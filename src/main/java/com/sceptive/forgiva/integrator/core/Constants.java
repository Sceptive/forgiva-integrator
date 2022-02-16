package com.sceptive.forgiva.integrator.core;

import org.bouncycastle.crypto.digests.SM3Digest;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public final static String       FORGIVA_VERSION               = "0.0.1";
    public final static String       FORGIVA_INTEGRATOR_HOME_LABEL = "FORGIVA_INTEGRATOR_HOME";
    public final static String LOG_DIRECTORY  = "log";
    public final static String CNF_DIRECTORY = "conf";
    public final static String DTA_DIRECTORY = "data";
    public final static String BIN_DIRECTORY = "bin";
    public final static String       FORGIVA_SERVER_FILENAME       = "forgiva_server";
    public final static String       INTEGRATOR_CONFIG_FILENAME    = "integrator.conf";
    public final static String DEBUG_LOG_FILENAME              = "debug.log";
    public final static String DEBUG_ROLLING_LOG_FILENAME      = "debug-%d{yyyy-MM-dd}.log.gz";
    public final static String CONSOLE_LOG_FILENAME            = "console.log";
    public final static String CONSOLE_ROLLING_LOG_FILENAME    = "console-%d{yyyy-MM-dd}.log.gz";

    public final static String WEB_API_BASEPATH                = "/api";

    // Configuration File Properties
    public final static String CFG_PRODUCTION_ENVIRONMENT      = "PRODUCTION_ENVIRONMENT";

    // ********************************************************************************************//
    // LOGGING PARAMETERS                                                                          //
    // ********************************************************************************************//
    public final static String CFG_LOGGING_GENERATE_DEBUG_LOG  = "LOGGING_GENERATE_DEBUG_LOG";
    public final static String CFG_LOGGING_MAX_DEBUG_LOG_SIZE  = "LOGGING_MAX_DEBUG_LOG_SIZE";
    public final static String CFG_LOGGING_OUTPUT_FORMAT       = "LOGGING_OUTPUT_FORMAT";

    // ********************************************************************************************//
    // DATABASE CONFIGURATION PARAMETERS                                                           //
    // ********************************************************************************************//
    public final static String CFG_DB_JDBC_DRIVER              = "DB_JDBC_DRIVER";
    public final static String CFG_DB_JDBC_URL                 = "DB_JDBC_URL";
    public final static String CFG_DB_JDBC_USER                = "DB_JDBC_USER";
    public final static String CFG_DB_JDBC_PASSWORD            = "DB_JDBC_PASSWORD";

    // ********************************************************************************************//
    // LDAP SERVER CONFIGURATION PARAMETERS                                                        //
    // ********************************************************************************************//
    public final static String CFG_LDAP_SERVER                 = "LDAP_SERVER";
    public final static String CFG_LDAP_DOMAIN                 = "LDAP_DOMAIN";


    // ********************************************************************************************//
    // LOGIN PARAMETERS                                                                         //
    // ********************************************************************************************//

    public final static String CFG_LOGIN_MAX_RETRY              = "LOGIN_MAX_RETRY";
    public final static String CFG_LOGIN_BAN_TIME               = "LOGIN_BAN_TIME";

    // ********************************************************************************************//
    // FORGIVA SERVER CONFIGURATION PARAMETERS                                                     //
    // ********************************************************************************************//
    public final static String CFG_FS_HOSTS                    = "FS_HOSTS";
    public final static String CFG_FS_RUNNER_PARALELIZATION    = "FS_RUNNER_PARALELIZATION";
    public final static String CFG_FS_EMBEDDED                 = "FS_EMBEDDED";

    // ********************************************************************************************//
    // SMTP SERVER CONFIGURATION PARAMETERS                                                        //
    // ********************************************************************************************//
    public final static String CFG_SMTP_SERVER                 = "SMTP_SERVER";
    public final static String CFG_SMTP_FROM                   = "SMTP_FROM";
    public final static String CFG_SMTP_USERNAME               = "SMTP_USERNAME";
    public final static String CFG_SMTP_PASSWORD               = "SMTP_PASSWORD";
    public final static String CFG_SMTP_PROXY_HOST             = "SMTP_PROXY_HOST";
    public final static String CFG_SMTP_PROXY_USERNAME         = "SMTP_PROXY_USERNAME";
    public final static String CFG_SMTP_PROXY_PASSWORD         = "SMTP_PROXY_PASSWORD";


    // ********************************************************************************************//
    // ADMINISTRATOR PARAMETERS                                                                    //
    // ********************************************************************************************//
    public final static String CFG_ADMINISTRATOR_USERNAME      = "ADMINISTRATOR_USERNAME";
    public final static String CFG_ADMINISTRATOR_PASSWORD      = "ADMINISTRATOR_PASSWORD";
    public final static String CFG_ADMINISTRATOR_IP_ADDRESSES  = "ADMINISTRATOR_IP_ADDRESSES";


    // ********************************************************************************************//
    // SECURITY PARAMETERS                                                                         //
    // ********************************************************************************************//
    public final static String CFG_SECURITY_PW_HASHING_MODEL       = "SECURITY_PW_HASHING_MODEL";
    public final static String CFG_SECURITY_PW_HASHING_SALT        = "SECURITY_PW_HASHING_SALT";
    public final static String CFG_SECURITY_DEFAULT_HASHING        = "SECURITY_DEFAULT_HASHING";
    public final static String CFG_SECURITY_DEFAULT_HASHING_SALT   = "SECURITY_DEFAULT_HASHING_SALT";
    public final static String CFG_SECURITY_PW_MIN_ENTROPY         = "SECURITY_PW_MIN_ENTROPY";
    public final static String CFG_SECURITY_PW_PROHIBITED_WORDLIST = "SECURITY_PW_PROHIBITED_WORDLIST";

    public final static String CFG_SECURITY_SSL_TRUSTSTORE = "SECURITY_SSL_TRUSTSTORE";
    public final static String CFG_SECURITY_SSL_TRUSTSTORE_PASSWORD = "SECURITY_SSL_TRUSTSTORE_PASSWORD";


    public final static String CFG_SESSION_LIFETIME                = "SESSION_LIFETIME";

    public final static String CFG_HTTPS_PORT                      = "HTTPS_PORT";
    public final static String CFG_HTTPS_HOST                      = "HTTPS_HOST";
    public final static String CFG_HTTPS_SSL_KEYSTORE_FILE         = "HTTPS_SSL_KEYSTORE_FILE";
    public final static String CFG_HTTPS_SSL_KEYSTORE_PASS         = "HTTPS_SSL_KEYSTORE_PASS";
    public final static String CFG_HTTPS_SSL_CERT_PASS             = "HTTPS_SSL_CERT_PASS";


    public final static String SMTP_SERVER_TYPE_PLAIN           = "SMTP";
    public final static String SMTP_SERVER_TYPE_SECURE          = "SMTPS";
    public final static String SMTP_SERVER_TYPE_SECURE_TLS      = "SMTPS_TLS";

    public final static List<String> USER_SETTINGS_KEYS = Arrays.asList(
            "animals_do_not_scramble", // Do not scramble animal positions randomly (Less secure)
            "masterkey_use_password",  // Use my login password as master key (Less secure)
            "masterkey_ask_once",       // Ask master key once when regenerating password
            "password_always_show_not_copy", // Always show passwords rather than copying to clipboard
            "password_default_length",  // Default password length
            "password_default_complexity" ); // Default password complexity

    public final static List<String> USER_SETTINGS_DEFAULT_VALUES = Arrays.asList(
            "false",
            "false",
            "false",
            "false",
            "16",
            "1"
    );

    public final static List<Class> USER_SETTINGS_TYPES = Arrays.asList(
        Boolean.class,
        Boolean.class,
        Boolean.class,
        Boolean.class,
        Integer.class,
        Integer.class);

    public final static String SECURITY_ALG_LBL_ARGON2        = "ARGON2";
    public final static String SECURITY_ALG_LBL_SCRYPT        = "SCRYPT";
    public final static String SECURITY_ALG_LBL_BCRYPT        = "BCRYPT";
    public final static String SECURITY_ALG_LBL_SHA512        = "SHA512";
    public final static String SECURITY_ALG_LBL_SHA384        = "SHA384";
    public final static String SECURITY_ALG_LBL_SHA256        = "SHA256";
    public final static String SECURITY_ALG_LBL_SHA3_512        = "SHA3-512";
    public final static String SECURITY_ALG_LBL_SHA3_384        = "SHA3-384";
    public final static String SECURITY_ALG_LBL_SHA3_256        = "SHA3-256";
    public final static String SECURITY_ALG_LBL_BLAKE2B_512         = "BLAKE2B-512";
    public final static String SECURITY_ALG_LBL_BLAKE2B_384         = "BLAKE2B-384";
    public final static String SECURITY_ALG_LBL_BLAKE2B_256         = "BLAKE2B-256";
    public final static String SECURITY_ALG_LBL_SM3                 = "SM3";


    public final static String[] SECURITY_DEFAULT_HASHING_MODEL_PARAMS = {
                SECURITY_ALG_LBL_SHA512,
                SECURITY_ALG_LBL_SHA384,
                SECURITY_ALG_LBL_SHA256,
                SECURITY_ALG_LBL_SHA3_512,
                SECURITY_ALG_LBL_SHA3_384,
                SECURITY_ALG_LBL_SHA3_256,
                SECURITY_ALG_LBL_BLAKE2B_512,
                SECURITY_ALG_LBL_BLAKE2B_384,
                SECURITY_ALG_LBL_BLAKE2B_256,
                SECURITY_ALG_LBL_SM3


    };

    public final static String[] SECURITY_PW_HASHING_MODEL_PARAMS
            = {
            SECURITY_ALG_LBL_ARGON2,
            SECURITY_ALG_LBL_SCRYPT,
            SECURITY_ALG_LBL_BCRYPT,
            SECURITY_ALG_LBL_SHA512,
            SECURITY_ALG_LBL_SHA384,
            SECURITY_ALG_LBL_SHA256,
            SECURITY_ALG_LBL_SHA3_512,
            SECURITY_ALG_LBL_SHA3_384,
            SECURITY_ALG_LBL_SHA3_256,
            SECURITY_ALG_LBL_BLAKE2B_512,
            SECURITY_ALG_LBL_BLAKE2B_384,
            SECURITY_ALG_LBL_BLAKE2B_256,
            SECURITY_ALG_LBL_SM3

    };


    public final static String CFG_ARGON2_PARAMETERS               = "ARGON2_PARAMETERS";
    public final static int    CFG_ARGON2_OUTPUT_SIZE       = 64;
    public final static String CFG_SCRYPT_PARAMETERS               = "SCRYPT_PARAMETERS";
    public final static int    CFG_SCRYPT_OUTPUT_SIZE       = 64;
    public final static String CFG_BCRYPT_COST                     = "BCRYPT_COST";



    public final static int    MIN_ENTROPY_FOR_PW                   = 40;
    public final static String DEFAULT_SALT                         = "464f52474956415f494e5445475241544f525f4445464155" +
            "4c545f4845585f56414c55455f464f525f53414c545f504c454153455f4348414e47455f54484953";

    // Application Constants
    public final static String HEADER_AUTHORIZATION             = "Authorization";
    public final static String HEADER_AUTHENTICATION_TYPE_BASIC = "Basic";
    public final static String HEADER_X_AUTH_TOKEN              = "X-AUTH-TOKEN";
    public final static String HEADER_WWW_Authenticate          = "WWW-Authenticate";
    public final static String HEADER_X_FORWARDED_FOR           = "X-Forwarded-For";
    public final static String HEADER_PROXY_CLIENT_IP           = "Proxy-Client-IP";
    public final static String HEADER_WL_PROXY_CLIENT_IP        = "WL-Proxy-Client-IP";
    public final static String HEADER_HTTP_CLIENT_IP            = "HTTP_CLIENT_IP";
    public final static String HEADER_HTTP_X_FORWARDED_FOR      = "HTTP_X_FORWARDED_FOR";

    public final static String[] IP_HEADERS = new String[] {
            HEADER_AUTHORIZATION, HEADER_AUTHENTICATION_TYPE_BASIC, HEADER_X_AUTH_TOKEN, HEADER_WWW_Authenticate,
            HEADER_X_FORWARDED_FOR, HEADER_PROXY_CLIENT_IP, HEADER_WL_PROXY_CLIENT_IP, HEADER_HTTP_CLIENT_IP,
            HEADER_HTTP_X_FORWARDED_FOR
    };

    // Internal configuration
    public final static String DEFAULT_USERGROUP_NAME           = "Forgiva Users";
    public final static String DEFAULT_USERGROUP_DESCRIPTION    = "Default Root Group";

    public final static String DEFAULT_METADATAGROUP_NAME           = "Accounts";
    public final static String DEFAULT_METADATAGROUP_DESCRIPTION    = "Default Metadata Group";

    public final static String DEFAULT_LDAP_USERGROUP_NAME      = "LDAP Users";
    public final static String DEFAULT_LDAP_USERGROUP_DESCRIPTION = "LDAP Users with external login procedures";

}
