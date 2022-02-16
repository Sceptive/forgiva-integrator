package com.sceptive.forgiva.integrator.core.crypto;

import com.sceptive.forgiva.integrator.core.BootStrapper;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Argon2Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.SCryptParameters;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.crypto.generators.SCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class Crypto {


    private static MessageDigest get_message_digest(String _digest_name) throws InvalidValueException {
        MessageDigest ret = null;
        try {

            ret = MessageDigest.getInstance(_digest_name,"BC");

        } catch (NoSuchAlgorithmException e) {
            Warning.get_instance().print("No %s algorithm could be found",_digest_name);
            throw new InvalidValueException(e.getMessage());
        } catch (NoSuchProviderException nspe) {
            Warning.get_instance().print("Security provider could not be found");
            throw new InvalidValueException(nspe.getMessage());
        } catch (Exception e) {
            Warning.get_instance().print("Could not generate strong secure random byte generator. " +
                    "Please seek to a mature JRE/JDK implementation.");
            throw new InvalidValueException(e.getMessage());
        }
        return ret;
    }

    private static void check_bootstrapped() throws NotInitializedException {
        if (!BootStrapper.is_crypto_initialized())
            throw new NotInitializedException();
    }

    public static byte[] argon2i(byte[] _in, byte[] _salt, int _memory,
            int _iterations, int _parallelization, int _out_length) throws NotInitializedException
    {
        check_bootstrapped();

        Argon2BytesGenerator a2bg = new Argon2BytesGenerator();
        org.bouncycastle.crypto.params.Argon2Parameters a2ps = new org.bouncycastle.crypto.params.Argon2Parameters
                .Builder(org.bouncycastle.crypto.params.Argon2Parameters.ARGON2_i)
                .withMemoryPowOfTwo(_memory)
                .withIterations(_iterations)
                .withParallelism(_parallelization)
                .withVersion(org.bouncycastle.crypto.params.Argon2Parameters.ARGON2_VERSION_13)
                .withSalt(_salt).build();
        a2bg.init(a2ps);
        byte[] out = new byte[_out_length];
        a2bg.generateBytes(_in,out);
        return out;
    }


    public static byte[] scrypt(byte[] _in, byte[] _salt, int _memory,
                                 int _block_size, int _parallelization, int _out_length) throws NotInitializedException
    {
        check_bootstrapped();

        return SCrypt.generate(_in,_salt,_memory,_block_size,_parallelization,_out_length);
    }

    public static byte[] bcrypt(byte[] _in, byte[] _salt, int _bcrypt_cost) throws NotInitializedException, InvalidValueException {

        check_bootstrapped();

        if (_salt.length != 16) {
            throw new InvalidValueException("Salt length should be 128 bit for bcrypt.");
        }

        return BCrypt.generate(_in,_salt,_bcrypt_cost);

    }

    private static byte[] hash(String _algorithm,byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {

        check_bootstrapped();

        MessageDigest md = get_message_digest(_algorithm);

        byte[] ret = null;

        if (md != null) {
            // Update digest with salt
            md.update(_salt);
            // Update digest with input
            ret = md.digest(_in);
        }

        return ret;
    }


    public static byte[] sha512(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA-512",_in,_salt);
    }

    public static byte[] sha384(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA-384",_in,_salt);
    }

    public static byte[] sha256(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA-256",_in,_salt);
    }

    public static byte[] sha3_512(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA3-512",_in,_salt);
    }

    public static byte[] sha3_384(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA3-384",_in,_salt);
    }

    public static byte[] sha3_256(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SHA3-256",_in,_salt);
    }
    public static byte[] blake2b_512(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("BLAKE2B-512",_in,_salt);
    }

    public static byte[] blake2b_384(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("BLAKE2B-384",_in,_salt);
    }

    public static byte[] blake2b_256(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("BLAKE2B-256",_in,_salt);
    }
    public static byte[] sm3(byte[] _in, byte[] _salt) throws NotInitializedException, InvalidValueException {
        return hash("SM3",_in,_salt);
    }


    /*
            Depending on algorithm name creates digest or KDF result as in return.

            For Argon2 and SCrypt algorithms it is necessary to provide Argon2Parameters and SCryptParameters object
            as _parameters parameter.

            For BCrypt it is necessary to provide cost value as "cost" parameter within Parameters object.

     */
    public static byte[] digest_or_kdf(String _algorithm, byte[] _in, byte[] _salt, Parameters _parameters)
            throws NotInitializedException, InvalidValueException {

        check_bootstrapped();

        byte[] ret = null;


        switch (_algorithm) {
            case Constants.SECURITY_ALG_LBL_ARGON2: {
                ret = Crypto.argon2i(_in,_salt,
                        _parameters.get_int_parameter (Argon2Parameters.p_memory),
                        _parameters.get_int_parameter (Argon2Parameters.p_iterations),
                        _parameters.get_int_parameter (Argon2Parameters.p_parallelization),
                        _parameters.get_int_parameter (Argon2Parameters.p_output_size));
                break;
            }
            case Constants.SECURITY_ALG_LBL_SCRYPT: {
                ret = Crypto.scrypt(_in,_salt,
                        _parameters.get_int_parameter (SCryptParameters.p_memory),
                        _parameters.get_int_parameter (SCryptParameters.p_block_size),
                        _parameters.get_int_parameter (SCryptParameters.p_parallelization),
                        _parameters.get_int_parameter (SCryptParameters.p_output_size));
                break;
            }
            case Constants.SECURITY_ALG_LBL_BCRYPT: {
                byte[] bc_salt = Arrays.copyOf(_salt,16);
                ret = Crypto.bcrypt(_in,_salt,_parameters.get_int_parameter ("cost"));
                break;
            }
            default: {
                ret = hash(_algorithm,_in,_salt);
                break;
            }


        }


        return ret;

    }



}
