package com.sceptive.forgiva.integrator.core.crypto;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Argon2Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.SCryptParameters;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Info;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class FHashGenerator {

    private static byte[] to_bytearr(char[] _val) {

        ByteBuffer bb = Charset.forName("UTF-8").encode(CharBuffer.wrap(_val));
        byte[] ret = new byte[bb.remaining()];
        bb.get(ret);
        return ret;
    }

    private static boolean parameters_validated = false;

    //////// BEGIN >>>> STATIC PARAMETERS TO RUN ALL THE TIME GENERATING HASH
    private static Argon2Parameters     a2_params;
    private static SCryptParameters     sc_params;
    private static Parameters           bc_params;
    private static int                  bcrypt_cost;
    //////// EOF <<<< STATIC PARAMETERS TO RUN ALL THE TIME GENERATING HASH


    private static void validate_parameters()  throws InvalidValueException, NotInitializedException  {

        if (!parameters_validated) {
            List<String> available_algos = Arrays.asList(Constants.SECURITY_PW_HASHING_MODEL_PARAMS);
            a2_params       = new Argon2Parameters(Configuration.runtime.argon2_parameters);
            sc_params       = new SCryptParameters(Configuration.runtime.scrypt_parameters);

            if (Configuration.runtime.security_pw_hashing_salt.length() < 16) {
                throw new InvalidValueException(
                        String.format("Salt should be at least 128 bit (16 characters) length. Defined: %s",
                                Configuration.runtime.security_pw_hashing_salt.length()));
            }

            for (String algorithm : Configuration.runtime.security_pw_hashing_model.split("\\+")) {
                if (!available_algos.contains(algorithm.toUpperCase())) {
                    throw new InvalidValueException(
                            String.format("Invalid algorithm specified: %s in hashing model %s", algorithm,
                                    Configuration.runtime.security_pw_hashing_model));
                }
            }

            try {
                bcrypt_cost = Integer.parseInt(Configuration.runtime.bcrypt_cost);
            } catch (NumberFormatException ex) {
                throw new InvalidValueException(
                        String.format("Invalid value %s for BCrypt Cost parameter", Configuration.runtime.bcrypt_cost));
            }

            bc_params       = (new Parameters()).set_int_parameter("cost", bcrypt_cost);




            parameters_validated = true;

        }
    }

    /*
        Launches generate_from_bytes from string literal
     */
    public static String generate(String _password) throws InvalidValueException, NotInitializedException {

        return generate_from_bytes(_password == null ? null : to_bytearr(_password.toCharArray()));
    }

    /*
        Generates hash of a password identified with a hash model described in configuration.

        Simply it generates hash with

         SECURITY_PW_HASHING_MODEL(SECURITY_DEFAULT_HASHING(password,SECURITY_PW_HASHING_SALT),SECURITY_PW_HASHING_SALT)

        formula.

        If _password is not null then it runs interactively and tries to fetch password from console.

     */
    public static String generate_from_bytes(byte[] _password) throws InvalidValueException, NotInitializedException {

        boolean interactive = _password == null;
        java.io.Console console = null;
        byte[] pass = _password;
        double entropy = 0;

        validate_parameters();



        if (interactive) {
            Info.get_instance().print("Forgiva Integrator Password Hash Generator");
            Info.get_instance().print("Hashing model: %s",      Configuration.runtime.security_pw_hashing_model);
            Info.get_instance().print("HM Salt: %s",            Configuration.runtime.security_pw_hashing_salt);
            Info.get_instance().print("Default Salt: %s",       Configuration.runtime.security_default_hashing_salt);
            Info.get_instance().print("Default Hashing: %s",    Configuration.runtime.security_default_hashing);
            Info.get_instance().print("Argon2 parameters: %s",  Configuration.runtime.argon2_parameters);
            Info.get_instance().print("SCrypt parameters: %s",  Configuration.runtime.scrypt_parameters);
            Info.get_instance().print("BCrypt cost: %s",        bcrypt_cost);


            console = System.console();
            char[] pass_first = null;
            char[] pass_second = null;
            while (true) {
                pass_first      = console.readPassword("\nPlease enter password:");
                pass_second     = console.readPassword("Please enter password (again):");

                if (!Arrays.equals(pass_first, pass_second)) {
                    console.printf("\nPasswords are not the same. Please try again.");
                } else {
                    entropy = Entropy.num_bits(pass_first);
                    if (entropy < Constants.MIN_ENTROPY_FOR_PW) {
                        console.printf("\nPassword should have at least %d bits of entropy for an ideal strength.\n" +
                                "Yours have %f bits.", Constants.MIN_ENTROPY_FOR_PW, entropy);
                    } else {
                        break;
                    }
                }
            }
            pass = to_bytearr(pass_first);;
        } else {
            entropy = Entropy.num_bits(new String(pass).toCharArray());
            if (entropy < Constants.MIN_ENTROPY_FOR_PW) {
                throw new InvalidValueException(
                        String.format("Password should have at least %d bits of entropy for an ideal strength.\n" +
                                "Yours have %f bits.", Constants.MIN_ENTROPY_FOR_PW, entropy));
            }
        }

        byte[] pw_hashing_salt  = Common.decodeHex(Configuration.runtime.security_pw_hashing_salt);
        byte[] default_salt     = Common.decodeHex(Configuration.runtime.security_default_hashing_salt);

        if (interactive) {
            console.printf("\nRunning hash model with %f entropy password and %d bits of salt",
                    entropy, default_salt.length * 8);
        }

        // First through default hashing algorithm
        pass = Crypto.digest_or_kdf(Configuration.runtime.security_default_hashing,pass,
                default_salt,
                null);


        // Hashing over model
        pass = hash_for_model(Configuration.runtime.security_pw_hashing_model, pass,
                pw_hashing_salt);


        String generated = Common.encodeHex(pass);

        if (interactive) {
            console.printf("\nGenerated password: %s", generated);
            return null;
        } else {
            return generated;
        }

    }

    public static byte[] hash_for_model(String _hashing_model, byte[] pass, byte[] salt)  throws InvalidValueException, NotInitializedException {

        validate_parameters();

        String[] algorithms =  _hashing_model.split("\\+");

        for (String algorithm : algorithms) {
            switch (algorithm) {
                case Constants.SECURITY_ALG_LBL_ARGON2: {
                    pass = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,pass,salt,a2_params);
                    break;
                }
                case Constants.SECURITY_ALG_LBL_SCRYPT: {
                    pass = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_SCRYPT,pass,salt,sc_params);
                    break;
                }
                case Constants.SECURITY_ALG_LBL_BCRYPT: {
                    pass = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_BCRYPT,pass,salt,bc_params);
                    break;
                }
                default: {
                    pass = Crypto.digest_or_kdf(algorithm,pass,salt,null);
                    break;
                }

            }
        }

        return pass;
    }
}
