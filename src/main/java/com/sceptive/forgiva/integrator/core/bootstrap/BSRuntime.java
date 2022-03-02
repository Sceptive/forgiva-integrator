package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.PropertyNotFoundException;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.LogMessageProcessor;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.apache.commons.text.StringSubstitutor;

import java.io.File;

// Holds runtime configuration values specified withing "integrator_config_file_path" file
public class BSRuntime extends IBootStrap {


    // Runtime configuration values. Defaults are for unit testing.
    public boolean production_environment        = true;
    public boolean logging_generate_debug_log    = false;
    public String  logging_max_file_size         = null;
    public String  logging_output_format         = null;
    public String  db_jdbc_driver                = null;
    public String  db_jdbc_url                   = null;
    public String  db_jdbc_user                  = null;
    public String  db_jdbc_password              = null;

    public Integer security_pw_min_entropy       = null;
    public String  security_pw_prohibited_list   = null;
    public String  security_pw_hashing_model     = null;
    public String  security_pw_hashing_salt      = null;
    public String  security_default_hashing      = null;
    public String  security_default_hashing_salt = null;

    public String  security_ssl_truststore          = null;
    public String  security_ssl_truststore_password = null;

    public String  argon2_parameters             = null;
    public String  scrypt_parameters             = null;
    public String  bcrypt_cost                   = null;
    public String  administrator_username        = null;
    public String  administrator_password        = null;
    public String  administrator_ip_addresses    = null;
    public Integer session_lifetime              = 60*24*30;
    public Integer https_port                    = null;
    public String  https_host                    = null;
    public String  https_ssl_keystore_file       = null;
    public String  https_ssl_keystore_pass       = null;
    public String  https_ssl_cert_pass           = null;

    public int     login_max_retry               = 3;
    public int     login_ban_time                = 10;

    public String  ldap_server                   = null;
    public String  ldap_domain                   = null;

    public String  fs_hosts                      = null;
    public Integer fs_runner_paralelization      = 8;
    public boolean fs_embedded                   = false;

    public String  smtp_server_header            = null;
    public String  smtp_server_host              = null;
    public String  smtp_server_from              = "forgiva_integrator@localhost";
    public Integer smtp_server_port              = 0;
    public String  smtp_server_username              = null;
    public String  smtp_server_password              = null;
    public String  smtp_server_proxy_host              = null;
    public Integer smtp_server_proxy_port              = 0;
    public String  smtp_server_proxy_username              = null;
    public String  smtp_server_proxy_password              = null;

    public static final String p_for_testing = "p_for_testing";


    public static boolean check_file_exists_and_readable(String _location,
                                                           String _description,
                                                           boolean _quit_if_not) {

        File                test_file   = new File(_location);
        LogMessageProcessor processor   = (_quit_if_not ? Fatal.get_instance() : Warning.get_instance());
        if (!test_file.exists()) {
            processor.print("%s file does not exists (%s)", _description, _location);
            return false;
        } else if (!test_file.canRead()) {
            processor.print("%s file cannot be readed (%s)", _description,_location);
            return false;
        }

        return true;
    }

    @Override
    public boolean bootstrap(Parameters _launch_options) {

        if (bootstrapped)
            return true;

        boolean for_testing     = (_launch_options == null) ? false :
                                  _launch_options.get_bool_parameter(p_for_testing);

        try {


            production_environment          = Configuration.get_property_bool(Constants.CFG_PRODUCTION_ENVIRONMENT);
            logging_generate_debug_log      = Configuration.get_property_bool(Constants.CFG_LOGGING_GENERATE_DEBUG_LOG);
            logging_max_file_size           = Configuration.get_property(Constants.CFG_LOGGING_MAX_DEBUG_LOG_SIZE);
            logging_output_format           = Configuration.get_property(Constants.CFG_LOGGING_OUTPUT_FORMAT);
            db_jdbc_driver                  = Configuration.get_property(Constants.CFG_DB_JDBC_DRIVER);

            db_jdbc_url                     = Configuration.get_property(Constants.CFG_DB_JDBC_URL);
            db_jdbc_url                     = StringSubstitutor.replace(db_jdbc_url,Configuration.dynamic_variables);

            db_jdbc_user                    = Configuration.get_property(Constants.CFG_DB_JDBC_USER);
            db_jdbc_password                = Configuration.get_property(Constants.CFG_DB_JDBC_PASSWORD);

            security_pw_hashing_model       = Configuration.get_property(Constants.CFG_SECURITY_PW_HASHING_MODEL);
            security_pw_hashing_salt        = Configuration.get_property(Constants.CFG_SECURITY_PW_HASHING_SALT);
            security_default_hashing        = Configuration.get_property(Constants.CFG_SECURITY_DEFAULT_HASHING);
            security_default_hashing_salt   = Configuration.get_property(Constants.CFG_SECURITY_DEFAULT_HASHING_SALT);

            security_pw_min_entropy         = Configuration.get_property_int(Constants.CFG_SECURITY_PW_MIN_ENTROPY);

            if (security_pw_min_entropy < 20) {
                throw new InvalidValueException(String.format("Less than 20 bits of password security cannot be accepted: %d",
                                                              security_pw_min_entropy));
            }


            login_max_retry = Configuration.get_property_int(Constants.CFG_LOGIN_MAX_RETRY);
            login_ban_time  = Configuration.get_property_int(Constants.CFG_LOGIN_BAN_TIME);

            security_ssl_truststore              = Configuration.conf_path + File.separator
                                                +  Configuration.get_property(Constants.CFG_SECURITY_SSL_TRUSTSTORE);
            security_ssl_truststore_password     = Configuration.get_property(Constants.CFG_SECURITY_SSL_TRUSTSTORE_PASSWORD);

            if (check_file_exists_and_readable(security_ssl_truststore, "SSL TrustStore", false)) {
                System.setProperty("javax.net.ssl.trustStore",          security_ssl_truststore);
                System.setProperty("javax.net.ssl.trustStorePassword",  security_ssl_truststore_password);
                Info.get_instance().print("%s set as default TrustStore for SSL Connections", security_ssl_truststore);
            }

            security_pw_prohibited_list     = Configuration.get_property(Constants.CFG_SECURITY_PW_PROHIBITED_WORDLIST);
            //gz / .bz2 / .7z or .xz
            if (security_pw_prohibited_list != null &&
                    !(security_pw_prohibited_list.toLowerCase().endsWith(".gz") ||
                security_pw_prohibited_list.toLowerCase().endsWith(".bz2") ||
                security_pw_prohibited_list.toLowerCase().endsWith(".7z") ||
                security_pw_prohibited_list.toLowerCase().endsWith(".xz"))) {
                throw new InvalidValueException(String.format("Unsupported wordlist file compression extension: %s"
                        ,security_pw_prohibited_list));
            }

            argon2_parameters           = Configuration.get_property(Constants.CFG_ARGON2_PARAMETERS);
            scrypt_parameters           = Configuration.get_property(Constants.CFG_SCRYPT_PARAMETERS);
            bcrypt_cost                 = Configuration.get_property(Constants.CFG_BCRYPT_COST);

            administrator_username      = Configuration.get_property(Constants.CFG_ADMINISTRATOR_USERNAME);
            administrator_password      = Configuration.get_property(Constants.CFG_ADMINISTRATOR_PASSWORD);
            administrator_ip_addresses  = Configuration.get_property(Constants.CFG_ADMINISTRATOR_IP_ADDRESSES);

            session_lifetime            = Configuration.get_property_int(Constants.CFG_SESSION_LIFETIME);

            ldap_server                 = Configuration.get_property(Constants.CFG_LDAP_SERVER);
            ldap_domain                 = Configuration.get_property(Constants.CFG_LDAP_DOMAIN);

            https_port                  = Configuration.get_property_int(Constants.CFG_HTTPS_PORT);;
            https_host                  = Configuration.get_property(Constants.CFG_HTTPS_HOST);;
            https_ssl_keystore_file     = Configuration.conf_path + File.separator
                                        + Configuration.get_property(Constants.CFG_HTTPS_SSL_KEYSTORE_FILE);
            https_ssl_keystore_pass     = Configuration.get_property(Constants.CFG_HTTPS_SSL_KEYSTORE_PASS);
            https_ssl_cert_pass         = Configuration.get_property(Constants.CFG_HTTPS_SSL_CERT_PASS);

            check_file_exists_and_readable(https_ssl_keystore_file, "HTTPS SSL KeyStore", true);

            fs_hosts                    = Configuration.get_property(Constants.CFG_FS_HOSTS);
            fs_runner_paralelization    = Configuration.get_property_int(Constants.CFG_FS_RUNNER_PARALELIZATION);
            fs_embedded                 = Configuration.get_property_bool(Constants.CFG_FS_EMBEDDED);


            String smtp_server          = Configuration.get_property(Constants.CFG_SMTP_SERVER);

            if (smtp_server != null) {

                smtp_server_from        = Configuration.get_property(Constants.CFG_SMTP_FROM);
                String lc_smtp_server = smtp_server.toLowerCase();
                if (lc_smtp_server.startsWith("smtp://")) {
                    smtp_server_header = Constants.SMTP_SERVER_TYPE_PLAIN;
                } else if (lc_smtp_server.startsWith("smtps://")) {
                    smtp_server_header = smtp_server_header = Constants.SMTP_SERVER_TYPE_SECURE;
                } else if (lc_smtp_server.startsWith("smtps_tls://")) {
                    smtp_server_header = smtp_server_header = Constants.SMTP_SERVER_TYPE_SECURE_TLS;
                } else {
                    throw new InvalidValueException(String.format("%s : %s is invalid",
                                                                  Constants.CFG_SMTP_SERVER,
                                                                  lc_smtp_server));
                }

                String[] parts = smtp_server.split("://");
                if (parts.length != 2) {
                    throw new InvalidValueException(String.format("%s : %s is invalid",
                                                                 Constants.CFG_SMTP_SERVER,
                                                                  smtp_server));
                }

                String host_part    = parts[1];
                String[] host_parts = host_part.split(":");

                if (host_parts.length == 2) {
                    smtp_server_host = host_parts[0];
                    smtp_server_port = Integer.parseInt(host_parts[1]);
                } else {
                    smtp_server_host = host_part;
                    smtp_server_port = 25;
                }

                smtp_server_username       = Configuration.get_property(Constants.CFG_SMTP_USERNAME);
                smtp_server_password       = Configuration.get_property(Constants.CFG_SMTP_PASSWORD);

                String smtp_proxy_server = Configuration.get_property(Constants.CFG_SMTP_PROXY_HOST);
                if (smtp_proxy_server != null) {
                    host_parts = smtp_proxy_server.split(":");
                    if (host_parts.length == 2) {
                        smtp_server_proxy_host = host_parts[0];
                        smtp_server_proxy_port = Integer.parseInt(host_parts[1]);
                    } else {
                        smtp_server_proxy_host = smtp_proxy_server;
                        smtp_server_proxy_port = 1080;
                    }
                    smtp_server_proxy_username = Configuration.get_property(Constants.CFG_SMTP_PROXY_USERNAME);
                    smtp_server_proxy_password = Configuration.get_property(Constants.CFG_SMTP_PROXY_PASSWORD);
                }

            }




        } catch (PropertyNotFoundException pe) {
            Fatal.get_instance().print("Could not find configuration value for %s in %s", pe.getProperty(), pe.getFile());
        } catch (NumberFormatException nfe) {
            Fatal.get_instance().print(nfe.getMessage());
        } catch (InvalidValueException iv) {
            Fatal.get_instance().print("Invalid value");
        }


        bootstrapped = true;

        return bootstrapped;
    }
}
