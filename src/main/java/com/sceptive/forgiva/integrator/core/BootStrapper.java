package com.sceptive.forgiva.integrator.core;


import com.sceptive.forgiva.integrator.core.bootstrap.*;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;

import java.io.File;
import java.util.Vector;

public class BootStrapper {



    private static BSLog4j                  bs_log4j;
    private static BSCrypto                 bs_crypto;
    private static BSDatabase               bs_database;
    private static BSStatCollector          bs_statcollector;
    private static BSForgivaServerInvoker   bs_forgivainvoker;
    private static BSFinal                  bs_final;


    private static void   check_for_necessary_directories() {
        File file_data_path         = new File(Configuration.data_path);

        File file_cfg_path          = new File(Configuration.conf_path);
        File file_cfg_file_path     = new File(Configuration.integrator_config_file_path);



        // Validating Configuration path
        if (!(file_cfg_path.exists() && file_cfg_path.isDirectory())) {
            Fatal.get_instance().print("Configuration files directory %s does not exists ",Configuration.conf_path);
        }

        // Validating Configuration file
        if (!(file_cfg_file_path.exists() && file_cfg_file_path.isFile() && file_cfg_file_path.canRead())) {
            Fatal.get_instance().print("Configuration file %s does not exists or not readable as file",
                                       Configuration.integrator_config_file_path);
        }

        // Validating Data path
        if (!(file_data_path.exists() && file_data_path.isDirectory())) {

            if ((!file_data_path.mkdir())) {
                Fatal.get_instance().print("Data directory %s does not exists. And could not be created. ", Configuration.data_path);
            }
        }

    }

    public static boolean initialize_for_toollkit() {
        // Bootstrapping runtime properties
        Configuration.runtime = new BSRuntime();
        Configuration.runtime.bootstrap(null);

        // Bootstrapping cryptography extensions
        bs_crypto = new BSCrypto();
        bs_crypto.bootstrap(new Parameters().set_bool_parameter(BSCrypto.p_check_configuration, true));

        return true;
    }


    public static boolean initialize_for_testing(boolean _initialize_crypto,
                                                    boolean _initialize_log4j,
                                                    boolean _initialize_database) {



        Configuration.runtime   = new BSRuntime();
        bs_crypto               = new BSCrypto();
        bs_log4j                = new BSLog4j();
        bs_database             = new BSDatabase();

        Info.get_instance().print("Bootstrapping for unit testing purposes...");

        // Bootstrapping runtime properties

        Parameters bsruntime_parameters = new Parameters();
        bsruntime_parameters.set_bool_parameter(BSRuntime.p_for_testing, true);

        Configuration.runtime.bootstrap(bsruntime_parameters);

        check_for_necessary_directories();

        if (_initialize_crypto) {
            // Bootstrapping cryptography extensions
            bs_crypto.bootstrap(new Parameters().set_bool_parameter(BSCrypto.p_check_configuration, true));
        }

        if (_initialize_log4j) {
            // Bootstrapping Log4j configuration
            bs_log4j.bootstrap(null);
        }

        if (_initialize_database) {
            // Bootstrapping Database configurations


            Configuration.runtime.db_jdbc_driver = "org.h2.Driver";
            Configuration.runtime.db_jdbc_url    = "jdbc:h2:mem:test ";
            Configuration.runtime.db_jdbc_user      = "admin";
            Configuration.runtime.db_jdbc_password  = "admin";

            bs_database.bootstrap(null);
        }

        return true;
    }

    public static boolean initialize_application() {

        File file_home_path         = new File(Configuration.home_path);
        File file_bin_path          = new File(Configuration.bin_path);
        File file_log_path          = new File(Configuration.logging_path);


        Info.get_instance().print("Booting up Forgiva Integrator.");

        // Validating HOME path
        if (!(file_home_path.exists() && file_home_path.isDirectory())) {
            Fatal.get_instance().print("Please set %s environment value to a valid directory. ",Constants.FORGIVA_INTEGRATOR_HOME_LABEL);
        }

        // Validating Binaries path
        if (!(file_bin_path.exists() && file_bin_path.isDirectory())) {
            Fatal.get_instance().print("Binaries directory %s does not exists. ",Configuration.bin_path);
        }

        // Validating Logging path
        if (!(file_log_path.exists() && file_log_path.isDirectory())) {

            if ((!file_log_path.mkdir())) {
                Fatal.get_instance().print("Logging directory %s does not exists. And could not be created. ", Configuration.logging_path);
            }
        }


        check_for_necessary_directories();


        // Bootstrapping runtime properties
        Configuration.runtime = new BSRuntime();

        Parameters bsruntime_parameters = new Parameters();
        bsruntime_parameters.set_bool_parameter(BSRuntime.p_for_testing, false);

        Configuration.runtime.bootstrap(bsruntime_parameters);


        if (!new File(Configuration.conf_path + File.separator + Configuration.runtime.https_ssl_keystore_file).exists()) {
            Warning.get_instance().print("");
        }


        Info.get_instance().print("Bootstrapping cryptography extensions...");

        // Bootstrapping cryptography extensions
        bs_crypto = new BSCrypto();
        bs_crypto.bootstrap(new Parameters().set_bool_parameter(BSCrypto.p_check_configuration,
                true));

        // Bootstrapping Log4j configuration
        bs_log4j = new BSLog4j();
        bs_log4j.bootstrap(null);

        Info.get_instance().print("Bootstrapping database connection...");

        // Bootstrapping Database configurations
        bs_database = new BSDatabase();
        bs_database.bootstrap(null);

        Info.get_instance().print("Bootstrapping statistics collector...");

        bs_statcollector = new BSStatCollector();
        bs_statcollector.bootstrap(null);


        Info.get_instance().print("Bootstrapping Forgiva Server invoker..");

        // Bootstrapping Forgiva Server
        bs_forgivainvoker = new BSForgivaServerInvoker();
        bs_forgivainvoker.bootstrap(null);


        Info.get_instance().print("Running final checks");

        bs_final          = new BSFinal();
        bs_final.bootstrap(null);

        Info.get_instance().print("Bootstrapping completed successfully");

        return true;
    }

    public static boolean is_crypto_initialized() {
        if (bs_crypto != null)
            return bs_crypto.is_bootstrapped();

        return false;
    }

    public static boolean is_logging_initialized() {
        if (bs_log4j != null)
            return bs_log4j.is_bootstrapped();

        return false;
    }
}
