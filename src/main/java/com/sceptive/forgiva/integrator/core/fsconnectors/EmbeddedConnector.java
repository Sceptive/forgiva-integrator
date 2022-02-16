package com.sceptive.forgiva.integrator.core.fsconnectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;

import oshi.SystemInfo;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class EmbeddedConnector implements IFSConnector {

    private static final String      BASE_PATH           = System.getenv("FORGIVA_INTEGRATOR_HOME");
    private static final Pattern
                                     FS_BIN_FILE_PATTERN = Pattern.compile("forgiva_server-r(\\d)-(\\d+)-(.*)-release");
    private static File              FS_BIN_FILE         = null;
    static               ObjectMapper omapper;

    static {
        omapper = new ObjectMapper();
        omapper.disable(MapperFeature.USE_ANNOTATIONS);
        omapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }


/* ***************************************************************************

    STATIC METHODS

    *************************************************************************** */
    private static EmbeddedConnector instance   = null;

    private static boolean conf_control_is_ok   = false;


    public static EmbeddedConnector get_instance() throws Exception {
        if (instance == null) {
            try {
                instance = new EmbeddedConnector();
            } catch (NotInitializedException ex) {
                Warning.get_instance().print(ex);
                return null;
            }
        }

        return instance;
    }



    private File binary_locator() throws NotInitializedException {

        File bin_dir =  new File(new StringBuilder().append(BASE_PATH)
                                                    .append(File.separator)
                                                    .append("bin")
                                                    .append(File.separator)
                                                    .toString());
        if (!bin_dir.exists()) {
            return null;
        }

        SystemInfo si = new SystemInfo();

        final String os_family = si.getOperatingSystem().getFamily().toLowerCase();

        Info.get_instance().print("OS Family: %s",os_family);

        if (si.getOperatingSystem().getBitness() != 64) {
            Warning.get_instance().print("Embedded Forgiva Server not supported for the architectures other than x86_64 processors.");
            throw new NotInitializedException();
        }

        final boolean is_linux = os_family.contains("linux") || os_family.contains("ubuntu");
        final boolean is_windows = os_family.contains("win");

        if (!is_linux && !is_windows) {
            Warning.get_instance().print("Embedded Forgiva Server not supported for the "+si.getOperatingSystem().getFamily()+" OS");
            throw new NotInitializedException();
        }
        final Hashtable<String,Matcher> matched_names = new Hashtable<>();

        File[] platform_binaries = bin_dir.listFiles((_file, _file_name) -> {


            Matcher matcher = FS_BIN_FILE_PATTERN.matcher(_file_name);
            if (matcher.find()) {


                String release   = matcher.group(1);
                String version   = matcher.group(2);

                try {
                    // Being sure they are parseable ints
                    Integer.parseInt(release);
                    Integer.parseInt(version);

                } catch (NumberFormatException _nfe) {
                    _nfe.printStackTrace();
                    return false;
                }

                String os_compat = matcher.group(3);

               // Info.get_instance().print("Release: %s Version %s OSCOMPAT: %s",release,version,os_compat);

                if ((is_linux && os_compat.equalsIgnoreCase("linux-x86_64")) ||
                    (is_windows && os_compat.equalsIgnoreCase("mingw64")))
                {
                    matched_names.put(_file_name,matcher);
                    return true;
                }

            }

            return false;
        });
        if (platform_binaries != null) {
            Arrays.sort(platform_binaries,
                        (_file_1, _file_2) -> {

                            Matcher matcher_1 = matched_names.get(_file_1.getName());
                            Matcher matcher_2 = matched_names.get(_file_2.getName());

                            Integer release_1 = Integer.parseInt(matcher_1.group(1));
                            Integer release_2 = Integer.parseInt(matcher_2.group(1));

                            Integer version_1 = Integer.parseInt(matcher_1.group(2));
                            Integer version_2 = Integer.parseInt(matcher_2.group(2));

                            int comp_01 = release_1.compareTo(release_2);
                            int comp_02 = version_1.compareTo(version_2);


                            return (comp_01 == 0 ? comp_02 : comp_01);

                        });

            if (platform_binaries.length == 0) {

                Warning.get_instance().print("Could not find a proper Forgiva Server binary.");
                throw new NotInitializedException();
            }

            return platform_binaries[platform_binaries.length-1];
        }


        return null;
    }

    private ExecutorService launcher_service = null;
    public EmbeddedConnector() throws NotInitializedException {

        if (!conf_control_is_ok) {

            if (Configuration.runtime.fs_embedded) {

                FS_BIN_FILE = binary_locator();

                if (FS_BIN_FILE == null) {
                    Warning.get_instance()
                           .print("Embedded Forgiva Server could not get launched");
                    throw new NotInitializedException();
                }

                launcher_service = Executors.newFixedThreadPool(Configuration.runtime.fs_runner_paralelization);


            }

        }

        conf_control_is_ok = true;


    }

//{
//    "host": "test.com",
//        "account": "administrator",
//        "animal": "Spider",
//        "character_length": 16,
//        "complexity": 1,
//        "is_lowercase": true,
//        "is_uppercase": true,
//        "is_symbols": true,
//        "is_number": true,
//        "password_hash":
//    "EE26B0DD4AF7E749AA1A8EE3C10AE9923F618980772E473F8819A5D4940E0DB27AC185F8A0E1D5F84F88BC887FD67B143732C304CC5FA9AD8E6F57F50028A8FF",
//            "renewal_date": "",
//        "signature": "",
//        "legacy_mode": false


    @Override
    public void execute(final EMetadata _metadata, final String _animal, final String _password_hash,
                        final String _signature, final IFSResponse _response_listener) {

        launcher_service.submit(() -> {
            Process      process = null;
            OutputStream stdin   = null;
            InputStream  stdout  = null;
            try {
                ProcessBuilder builder = new ProcessBuilder(FS_BIN_FILE.getAbsolutePath(),
                                                            "-s");
                process = builder.start();
                stdin  = process.getOutputStream();
                stdout = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
                String json = omapper.writeValueAsString(new FSRequest().host(_metadata.host)
                                                                        .account(_metadata.account)
                                                                        .animal(_animal)
                                                                        .character_length(_metadata.passwordLength)
                                                                        .complexity(_metadata.complexity)
                                                                        .is_lowercase(_metadata.optLowercase)
                                                                        .is_uppercase(_metadata.optUppercase)
                                                                        .is_symbols(_metadata.optSymbols)
                                                                        .is_number(_metadata.optNumbers)
                                                                        .password_hash(_password_hash)
                                                                        .renewal_date(_metadata.lastRenewal)
                                                                        .signature(_signature)
                                                                        .legacy_mode(false));

                //Info.get_instance().print("Input: %s",json);

                stdin.write(json.getBytes("UTF-8"));
                stdin.flush();
                stdin.close();


                FSResponse response = omapper.readValue(stdout,
                                                        FSResponse.class);
                _response_listener.ok(response.getAnimal(),
                                      response.getPassword());
            } catch (Exception _e) {
                Warning.get_instance().print("Failed to run %s", FS_BIN_FILE.getAbsolutePath());
                Warning.get_instance().print(_e);
                _response_listener.failed(_e.getMessage());
            } finally {
                try {
                    if (process != null) {
                        process.destroy();
                    }
                    if (stdin != null) {
                        stdin.close();
                    }
                    if (stdout != null) {
                        stdout.close();
                    }
                } catch (Exception _e)
                {
                    Warning.get_instance().print("Unexpected exception on closed process");
                }
            }




        });
    }



}
