package com.sceptive.forgiva.integrator.core;


import com.sceptive.forgiva.integrator.core.crypto.Entropy;
import com.sceptive.forgiva.integrator.core.db.H2InstanceProvider;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcConnectionPool;


public class PasswordGuard {


    /* ***************************************************************************

     STATIC METHODS

    *************************************************************************** */
    private static PasswordGuard instance;

    private JdbcConnectionPool wl_conn_pool = null;
    private boolean generating_hashset      = false;

    /*
        Returns instance if not initialized statically
     */
    public static PasswordGuard get_instance() {
        if (instance == null) {
            try {
                instance = new PasswordGuard();
            } catch (Exception ex) {
                return null;
            }
        }

        return instance;
    }

    private void generate_hashset(File _wordlist_file, Connection _connection) throws Exception {

        generating_hashset    = true;
        // Average size of the expected total size of contents depending on compression algorithm
        long    expected_size = _wordlist_file.length()*3;
        // Readed line from decompressed file
        String line     = null;
        // Amount of words processed
        long   amount   = 0;
        // Amount of words failed to save
        long   failed   = 0;
        // Current percentage
        long   pr_size  = 0;
        // Last informed percentage
        long   ls_perc  = 0;

        InputStream is = null;
        String wl_lcase = _wordlist_file.getAbsolutePath().toLowerCase();
        if (wl_lcase.endsWith(".7z")) {
            SevenZFile sevenZFile = new SevenZFile(_wordlist_file);

            SevenZArchiveEntry en = sevenZFile.getNextEntry();
            is = sevenZFile.getInputStream(en);

        } else {
            InputStream fis = Files.newInputStream(Paths.get(_wordlist_file.getAbsolutePath()));
            if (wl_lcase.endsWith(".gz")) {
                is =
                    new GzipCompressorInputStream(fis);

            } else if (wl_lcase.endsWith(".bz2")) {
                is = new BZip2CompressorInputStream(fis);
            } else if (wl_lcase.endsWith(".xz")) {
                is = new XZCompressorInputStream(fis);
            } else {
                throw new InvalidValueException(String.format("%s is unacceptable. Please check configuration.",
                                                _wordlist_file.getAbsolutePath())
                                                );
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));



        try (Statement _st = _connection.createStatement()) {
            _st.execute("CREATE TABLE WORDS(WORD VARCHAR);");
            _st.execute("CREATE UNIQUE INDEX IDX_U_NAME ON WORDS(WORD);");
        }


        Info.get_instance().print("Generating wordlist for %s : %d bytes",
                                  _wordlist_file.getAbsolutePath(),_wordlist_file.length());

        PreparedStatement ps =  _connection.prepareStatement("INSERT INTO WORDS VALUES (?)");
        while ((line = br.readLine()) != null) {

            ps.setString(1, line);
            try {
                ps.execute();

                amount++;

            } catch (JdbcSQLException _ex) {
                failed++;
            }



            pr_size += line.length();

            for (long percentage = (pr_size* 100)/expected_size;
                        percentage % 10 == 0
                                && ls_perc != percentage
                                && percentage < 100;) {
                Info.get_instance().print("PasswordGuard processed %d bytes ~ %%%d of %s.",
                        pr_size,
                        percentage,_wordlist_file.getAbsolutePath());
                ls_perc = percentage;
                break;
            }

        }

        Info.get_instance().print("PasswordGuard reached total of %d words (failed %d) " +
                "and finished initialization. ", amount, failed);


        generating_hashset = false;
    }

    private ExecutorService launcher_service = null;
    public PasswordGuard() throws Exception {

        if (Configuration.runtime.security_pw_prohibited_list != null) {

            String expected_path = Configuration.conf_path + File.separator + Configuration.runtime.security_pw_prohibited_list;
            File   wordlist_file = new File(expected_path);

            if (!wordlist_file.exists()) {
                Fatal.get_instance().print("%s does not exists. Please check configuration.", expected_path);
            }

            Path     fp                 = Paths.get(expected_path);
            FileTime fileTime           = Files.getLastModifiedTime(fp);
            String bf_data_file_name    = String.format("%d-%d.wl",fileTime.toMillis(),wordlist_file.length());
            launcher_service        = Executors.newFixedThreadPool(1);

            wl_conn_pool            = H2InstanceProvider.get(bf_data_file_name);
            Connection c            = wl_conn_pool.getConnection();
            ResultSet rs            = c.getMetaData().getTables(c.getCatalog(),null,"WORDS",null);


            if (!rs.next()) {


                launcher_service.submit(() -> {
                    try {
                        Connection conn = wl_conn_pool.getConnection();
                        generate_hashset(wordlist_file, conn);
                        conn.close();
                    }
                    catch (Exception _e) {
                        Fatal.get_instance().print(_e);
                    }
                });

            }

            rs.close();
            c.close();

        }


    }

    public void validate_password(String _password,PasswordValidationResponse _response)
          {

        while (generating_hashset) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException _e) {
                Warning.get_instance().print(_e);
            }
        }

        try (Connection c            = wl_conn_pool.getConnection();
             PreparedStatement ps    = c.prepareStatement("SELECT WORD FROM WORDS WHERE WORD = ?");
        ) {


            for (int i = 4; i <= _password.length(); i++) {
                for (int x = 0; x <= _password.length() - i; x++) {
                    String subs = _password.substring(x,
                                                      x + i);

                    ps.setString(1,subs);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        _response.failed(String.format("Passwords containing '%s' is invalid against wordlist",
                                                       subs));
                    }
                    rs.close();;
                }
            }
        } catch (SQLException _ex) {
            Warning.get_instance().print(_ex);
            _response.failed("Temporary problem occured on Password Guard.  ");
        }

        double entropy = Entropy.num_bits(_password.toCharArray());
        if (entropy < Configuration.runtime.security_pw_min_entropy.doubleValue()) {
            _response.failed(
                    String.format("%f of bits entropy cannot be accepted. Minimum %d bits of entropy required",
                                  entropy, Configuration.runtime.security_pw_min_entropy));
        }

        _response.ok();
    }

    public interface PasswordValidationResponse {

         void ok();
         void failed(String _message);
    }

    public boolean isGenerating_hashset() {
        return generating_hashset;
    }
}
