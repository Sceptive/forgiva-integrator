package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.PasswordGuard;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.Crypto;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class BSCrypto extends IBootStrap {


    // Bootstrap parameter
    public static final String p_check_configuration = "check_configuration";

    // Bootstrap cryptography extension(s)

    @Override
    public boolean bootstrap(Parameters _launch_options) {
        if (bootstrapped)
            return true;

        boolean check_configuration     = _launch_options != null ?
                _launch_options.get_bool_parameter(p_check_configuration) : true;


        // Initializing BouncyCastle for security provider
        Security.addProvider(new BouncyCastleProvider());

        if (check_configuration ) {
            // Security settings check
            // Check salt value
            if (!Common.check_if_hex(Configuration.runtime.security_pw_hashing_salt)) {
                Fatal.get_instance().print("Hashing salt specified in configuration is not a hex value.");
            }

            // Check whether salt it is default
            if (Configuration.runtime.security_pw_hashing_salt.equalsIgnoreCase(
                    Constants.DEFAULT_SALT)) {
                if (!Configuration.runtime.production_environment) {
                    Warning.get_instance().print("It is strongly advised to change default salt to a truly random" +
                            "-generated value longer than 256 bits (64 characters).");
                } else {
                    Fatal.get_instance().print("You should not run Forgiva Integrator on production environment" +
                                    " with default development/test salt value. Please change %s parameter on configuration " +
                                    "file %s with the one truly-randomly generated and at least 256 bit value.",
                            Constants.CFG_SECURITY_PW_HASHING_SALT, Configuration.integrator_config_file_path);
                }
            }
        }

        // Validating password guard bootstrap
        try {
            PasswordGuard.get_instance();
        } catch (Exception _ex) {
            Fatal.get_instance().print(_ex);
        }

        bootstrapped = true;

        return bootstrapped;
    }
}
