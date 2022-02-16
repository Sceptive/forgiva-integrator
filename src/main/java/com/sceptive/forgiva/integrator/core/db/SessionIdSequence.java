package com.sceptive.forgiva.integrator.core.db;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.internal.databaseaccess.Accessor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sequencing.Sequence;
import org.eclipse.persistence.sessions.Session;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.UUID;
import java.util.Vector;

/*
    Creates custom 512bit Session ID with random seed and random UUID combined
    over SHA-512 algorithm.
 */
public class SessionIdSequence  {

    private byte[] random_seed;
    private MessageDigest md = null;

    /*
        Initializes random seed and MessageDigest object from BouncyCastle implementation
     */
    private void initialize()  {

        try {
            // Generate 128 byte random seed from SecureRandom
            random_seed = SecureRandom.getInstanceStrong().generateSeed(128);
            // Initialize MessageDigest from BouncyCastle provider
            md = MessageDigest.getInstance("SHA-512","BC");
        } catch (NoSuchAlgorithmException e) {
            Fatal.get_instance().print("No SHA-512 algorithm could be found");
        } catch (NoSuchProviderException nspe) {
            Fatal.get_instance().print("Security provider could not be found");
        } catch (Exception e) {
            Fatal.get_instance().print("Could not generate strong secure random byte generator. " +
                    "Please seek to a mature JRE/JDK implementation.");
        }
    }
    public SessionIdSequence() {
        super();
        initialize();
    }


    public String generate() {

        synchronized (this) {
            String rand_uuid = "";
            try {
                // Reset prior generation
                md.reset();
                // Update digest with random seed generated on class initialization
                md.update(random_seed);
                // Random uuid
                rand_uuid = UUID.randomUUID()
                                .toString();
                // Update digest with random uuid bytes
                byte[] val = md.digest(rand_uuid.getBytes(StandardCharsets.UTF_8));
                // Return as lowercase hex
                return String.valueOf(Hex.encodeHex(val,
                                                    true));
            }
            catch (Exception ex) {
                Warning.get_instance()
                       .print("Rand seed: %s UUID: %s",
                              Common.encodeHex(random_seed),
                              rand_uuid);
                Fatal.get_instance()
                       .print(ex);
            }
        }

        return null;

    }

}
