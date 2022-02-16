package com.sceptive.forgiva.integrator.core;


import com.sceptive.forgiva.integrator.core.bootstrap.BSRuntime;
import com.sceptive.forgiva.integrator.exceptions.PropertyNotFoundException;
import com.sceptive.forgiva.integrator.logging.Warning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;


/*
    Responsible for holding runtime configuration of Integrator.

 */
public class Configuration {

    /* Environment value for Integrator home path */
    private static String home_env_value = System.getenv(Constants.FORGIVA_INTEGRATOR_HOME_LABEL);

    /* Integrator home path */
    public static String home_path
            = home_env_value == null ? new File("").getAbsoluteFile().getAbsolutePath() :
            home_env_value;

    public static String logging_path   = home_path + File.separator + Constants.LOG_DIRECTORY;
    public static String data_path      = home_path + File.separator + Constants.DTA_DIRECTORY;
    public static String conf_path      = home_path + File.separator + Constants.CNF_DIRECTORY;
    public static String bin_path       = home_path + File.separator + Constants.BIN_DIRECTORY;

    // File full paths
    public static String forgiva_server_path
            = bin_path + File.separator + Constants.FORGIVA_SERVER_FILENAME;
    public static String integrator_config_file_path
            = conf_path + File.separator + Constants.INTEGRATOR_CONFIG_FILENAME;
    public static String debug_logging_file_path
            = logging_path + File.separator + Constants.DEBUG_LOG_FILENAME;
    public static String debug_logging_rolling_file_path
            = logging_path + File.separator + Constants.DEBUG_ROLLING_LOG_FILENAME;

    public static String console_logging_file_path
            = logging_path + File.separator + Constants.CONSOLE_LOG_FILENAME;
    public static String console_logging_rolling_file_path
            = logging_path + File.separator + Constants.CONSOLE_ROLLING_LOG_FILENAME;


    public static BSRuntime runtime = null;

    private static Properties loaded_properties = null;

    private static Properties get_properties() throws FileNotFoundException, Exception {
        if (loaded_properties != null) {
            return loaded_properties;
        }

        loaded_properties = new Properties();
        loaded_properties.load(new FileInputStream(new File(integrator_config_file_path)));


        // Trimming values
        for (String key : loaded_properties.stringPropertyNames()) {
            String value = loaded_properties.getProperty(key);
            if (value != null) {
                loaded_properties.setProperty(key,value.trim());
            }
        }

        return loaded_properties;
    }


    public static String get_property(String _key) throws PropertyNotFoundException {
        try {
            return get_properties().getProperty(_key);
        } catch (Exception e) {
            throw new PropertyNotFoundException(_key, integrator_config_file_path);
        }
    }

    public static boolean get_property_bool(String _key) throws PropertyNotFoundException {
        String value = get_property(_key);
        return (value.equalsIgnoreCase("true"));
    }

    public static Integer get_property_int(String _key) throws PropertyNotFoundException, NumberFormatException {
        String value = get_property(_key);
        Integer int_val = 0;

        try {
         int_val =  Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            Warning.get_instance().print("For %s key %s is not a valid integer value",_key,value);
        }

        return int_val;
    }

    public static Long get_property_long(String _key) throws PropertyNotFoundException, NumberFormatException {
        String value = get_property(_key);
        Long long_val = 0L;

        try {
            long_val =  Long.parseLong(value);
        } catch (NumberFormatException ex) {
            Warning.get_instance().print("For %s key %s is not a valid long value",_key,value);
        }

        return long_val;
    }

}
