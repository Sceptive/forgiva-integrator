package com.sceptive.forgiva.integrator.test;

import com.sceptive.forgiva.integrator.core.BootStrapper;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.crypto.*;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Argon2Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.crypto.parameters.SCryptParameters;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.core.db.objects.ESession;
import com.sceptive.forgiva.integrator.logging.Debug;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.LogMessageProcessor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.SecureRandom;

@Test
public class CryptoTests extends IFTests {

private static final boolean ignore_tests = false;
private static final boolean debug        = true;

@BeforeClass(alwaysRun = true)
public void setup() {
    BootStrapper.initialize_for_testing(true,
                                        false,
                                        false);
}


@Test(groups = "crypto",
      testName = "hash2Password",
      description = "Checking hash to password converter",
      priority = 0
      )
public void test_hash2password() {
    if (ignore_tests)
        return;
    try {


        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            byte[] bytes = new byte[32];
            random.nextBytes(bytes);
            EMetadata _testMd = new EMetadata();

            _testMd.optSymbols = random.nextBoolean();
            _testMd.optNumbers = random.nextBoolean();
            _testMd.optUppercase = random.nextBoolean();
            _testMd.optLowercase = random.nextBoolean();
            _testMd.passwordLength = 16;

            Info.get_instance().print("Rand: %s.. Symb: %s Num: %s Uc: %s Lc: %s Result: %s",
                                      Common.encodeHex(bytes).substring(0,8),
                                      _testMd.optSymbols,
                                      _testMd.optNumbers,
                                      _testMd.optUppercase,
                                      _testMd.optLowercase,
                                      Hash2Password.gen_password(_testMd,bytes)
                                      );

        }
    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }


}

@Test(groups = "crypto",
      testName = "Argon2i",
      description = "Checking test vectors.",
      priority = 0)
public void test_argon2i() {
    if (ignore_tests)
        return;
    try {
        byte[] _password = "password".getBytes("UTF-8");
        byte[] _salt     = "somesalt".getBytes("UTF-8");
        byte[] _different_password = "differentpassword".getBytes("UTF-8");
        byte[] _different_salt     = "diffsalt".getBytes("UTF-8");
        Argon2Parameters argon2Parameters = new Argon2Parameters("2:20:1");
        argon2Parameters.set_int_parameter("output_size",
                                           32);
        byte[] out = null;
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "d1587aca0922c3b5d6a83edab31bee3c4ebaef342ed6127a55d19b2351ad1f41");
        argon2Parameters.set_int_parameter("memory",
                                           18);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "296dbae80b807cdceaad44ae741b506f14db0959267b183b118f9b24229bc7cb");
        argon2Parameters.set_int_parameter("memory",
                                           16);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "c1628832147d9720c5bd1cfd61367078729f6dfb6f8fea9ff98158e0d7816ed0");
        argon2Parameters.set_int_parameter("memory",
                                           8);
        argon2Parameters.set_int_parameter("iterations",
                                           2);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "89e9029f4637b295beb027056a7336c414fadd43f6b208645281cb214a56452f");
        argon2Parameters.set_int_parameter("parallelization",
                                           2);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "4ff5ce2769a1d7f4c8a491df09d41a9fbe90e5eb02155a13e4c01e20cd4eab61");
        argon2Parameters.set_int_parameter("memory",
                                           16);
        argon2Parameters.set_int_parameter("iterations",
                                           1);
        argon2Parameters.set_int_parameter("parallelization",
                                           1);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "d168075c4d985e13ebeae560cf8b94c3b5d8a16c51916b6f4ac2da3ac11bbecf");
        argon2Parameters.set_int_parameter("iterations",
                                           4);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "aaa953d58af3706ce3df1aefd4a64a84e31d7f54175231f1285259f88174ce5b");
        argon2Parameters.set_int_parameter("memory",
                                           16);
        argon2Parameters.set_int_parameter("iterations",
                                           2);
        argon2Parameters.set_int_parameter("parallelization",
                                           1);
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _different_password,
                                   _salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "14ae8da01afea8700c2358dcef7c5358d9021282bd88663a4562f59fb74d22ee");
        out = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_ARGON2,
                                   _password,
                                   _different_salt,
                                   argon2Parameters);
        Assert.assertEquals(Common.encodeHex(out),
                            "b0357cccfbef91f3860b0dba447b2348cbefecadaf990abfe9cc40726c521271");
    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}

@Test(groups = "crypto",
      testName = "BCrypt",
      description = "Checking test vectors.")
public void test_bcrypt() {
    if (ignore_tests)
        return;
    try {
        Object[][] testVectors = {{"", "144b3d691a7b4ecf39cf735c7fa7a79c", Integer.valueOf(6),
                                   "557e94f34bf286e8719a26be94ac1e16d95ef9f819dee092"},
                                  {"00", "144b3d691a7b4ecf39cf735c7fa7a79c", Integer.valueOf(6),
                                   "557e94f34bf286e8719a26be94ac1e16d95ef9f819dee092"},
                                  {"00", "26c63033c04f8bcba2fe24b574db6274", Integer.valueOf(8),
                                   "56701b26164d8f1bc15225f46234ac8ac79bf5bc16bf48ba"},
                                  {"00", "9b7c9d2ada0fd07091c915d1517701d6", Integer.valueOf(10),
                                   "7b2e03106a43c9753821db688b5cc7590b18fdf9ba544632"},
                                  {"6100", "a3612d8c9a37dac2f99d94da03bd4521", Integer.valueOf(6),
                                   "e6d53831f82060dc08a2e8489ce850ce48fbf976978738f3"},
                                  {"6100", "7a17b15dfe1c4be10ec6a3ab47818386", Integer.valueOf(8),
                                   "a9f3469a61cbff0a0f1a1445dfe023587f38b2c9c40570e1"},
                                  {"6100", "9bef4d04e1f8f92f3de57323f8179190", Integer.valueOf(10),
                                   "5169fd39606d630524285147734b4c981def0ee512c3ace1"},
                                  {"61626300", "2a1f1dc70a3d147956a46febe3016017",
                                   Integer.valueOf(6),
                                   "d9a275b493bcbe1024b0ff80d330253cfdca34687d8f69e5"},
                                  {"61626300", "4ead845a142c9bc79918c8797f470ef5",
                                   Integer.valueOf(8),
                                   "8d4131a723bfbbac8a67f2e035cae08cc33b69f37331ea91"},
                                  {"61626300", "631c554493327c32f9c26d9be7d18e4c",
                                   Integer.valueOf(10),
                                   "8cd0b863c3ff0860e31a2b42427974e0283b3af7142969a6"},
                                  {"6162636465666768696a6b6c6d6e6f707172737475767778797a00",
                                   "02d1176d74158ee29cffdac6150cf123", Integer.valueOf(6),
                                   "4d38b523ce9dc6f2f6ff9fb3c2cd71dfe7f96eb4a3baf19f"},
                                  {"6162636465666768696a6b6c6d6e6f707172737475767778797a00",
                                   "715b96caed2ac92c354ed16c1e19e38a", Integer.valueOf(8),
                                   "98bf9ffc1f5be485f959e8b1d526392fbd4ed2d5719f506b"},
                                  {"6162636465666768696a6b6c6d6e6f707172737475767778797a00",
                                   "85727e838f9049397fbec90566ede0df", Integer.valueOf(10),
                                   "cebba53f67bd28af5a44c6707383c231ac4ef244a6f5fb2b"},
                                  {"7e21402324255e262a28292020202020207e21402324255e262a2829504e4246524400",
                                   "8512ae0d0fac4ec9a5978f79b6171028", Integer.valueOf(6),
                                   "26f517fe5345ad575ba7dfb8144f01bfdb15f3d47c1e146a"},
                                  {"7e21402324255e262a28292020202020207e21402324255e262a2829504e4246524400",
                                   "1ace2de8807df18c79fced54678f388f", Integer.valueOf(8),
                                   "d51d7cdf839b91a25758b80141e42c9f896ae80fd6cd561f"},
                                  {"7e21402324255e262a28292020202020207e21402324255e262a2829504e4246524400",
                                   "36285a6267751b14ba2dc989f6d43126", Integer.valueOf(10),
                                   "db4fab24c1ff41c1e2c966f8b3d6381c76e86f52da9e15a9"},
                                  {"c2a300", "144b3d691a7b4ecf39cf735c7fa7a79c", Integer.valueOf(6),
                                   "5a6c4fedb23980a7da9217e0442565ac6145b687c7313339"},};
        for (Object[] vector : testVectors) {
            byte[] password = Common.decodeHex((String) vector[0]);
            byte[] salt     = Common.decodeHex((String) vector[1]);
            int    cost     = ((Integer) vector[2]).intValue();
            byte[] expected = Common.decodeHex((String) vector[3]);
            Parameters ip = (new Parameters()).set_int_parameter("cost",
                                                                 cost);
            byte[] ret = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_BCRYPT,
                                              password,
                                              salt,
                                              ip);
            Debug.get_instance()
                 .print("BCrypt %s %s => %s ",
                        (String) vector[0],
                        (String) vector[1],
                        Common.encodeHex(ret));
            Assert.assertEquals(ret,
                                expected);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}

@Test(groups = "crypto",
      testName = "SCrypt",
      description = "Checking test vectors.")
public void test_scrypt() {
    if (ignore_tests)
        return;
    try {
        Object[][] testVectors =
                {{"", "", Integer.valueOf(16), Integer.valueOf(1), Integer.valueOf(1),
                  "77d6576238657b203b19ca42c1" +
                  "8a0497f16b4844e3074ae8dfdffa3fede21442fcd0069ded0948f8326a753a0fc81f17e8d3e0fb2e0d3628cf" +
                  "35e20c38d18906"}, {"password", "NaCl", Integer.valueOf(1024), Integer.valueOf(8),
                                      Integer.valueOf(16), "fdbabe1c9d3472007856e7190d01e9fe" +
                                                           "7c6ad7cbc8237830e77376634b373162" +
                                                           "2eaf30d92e22a3886ff109279d9830da" +
                                                           "c727afb94a83ee6d8360cbdfa2cc0640"},
                 {"pleaseletmein", "SodiumChloride", Integer.valueOf(16384), Integer.valueOf(8),
                  Integer.valueOf(1),
                  "7023bdcb3afd7348461c06cd81fd38eb" + "fda8fbba904f8e3ea9b543f6545da1f2" +
                  "d5432955613f0fcf62d49705242a9af9" + "e61e85dc0d651e40dfcf017b45575887"},
                 {"pleaseletmein", "SodiumChloride", Integer.valueOf(1048576), Integer.valueOf(8),
                  Integer.valueOf(1),
                  "2101cb9b6a511aaeaddbbe09cf70f881" + "ec568d574a2ffd4dabe5ee9820adaa47" +
                  "8e56fd8f4ba5d09ffa1c6d927c40f4c3" + "37304049e8a952fbcbf45c6fa77a41a4"}};
        for (Object[] vector : testVectors) {
            byte[] password = ((String) vector[0]).getBytes();
            byte[] salt     = ((String) vector[1]).getBytes();
            int    N        = ((Integer) vector[2]).intValue();
            int    r        = ((Integer) vector[3]).intValue();
            int    p        = ((Integer) vector[4]).intValue();
            byte[] expected = Common.decodeHex((String) vector[5]);
            SCryptParameters params = new SCryptParameters(String.format("%d:%d:%d",
                                                                         N,
                                                                         r,
                                                                         p));
            params.set_int_parameter("output_size",
                                     64);
            byte[] ret = Crypto.digest_or_kdf(Constants.SECURITY_ALG_LBL_SCRYPT,
                                              password,
                                              salt,
                                              params);
            Debug.get_instance()
                 .print("SCrypt %s %s => %s ",
                        new String(password),
                        new String(salt),
                        Common.encodeHex(ret));
            Assert.assertEquals(ret,
                                expected);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}

@Test(groups = "crypto",
      testName = "Digest algorithms",
      description = "Checking test vectors.")
public void test_digests() {
    if (ignore_tests)
        return;
    try {
        Object[][] testVectors = {{Constants.SECURITY_ALG_LBL_SHA3_512, "password",
                                   "e9a75486736a550af4fea861e2378305c4a555a05094d" +
                                   "ee1dca2f68afea49cc3a50e8de6ea131ea521311f4d6fb054a146e8282f8e35ff2e6368c1a62e909716"},
                                  {Constants.SECURITY_ALG_LBL_SHA3_384, "password",
                                   "9c1565e99afa2ce7800e96a73c125363c06697c5674d5" +
                                   "9f227b3368fd00b85ead506eefa90702673d873cb2c9357eafc"},
                                  {Constants.SECURITY_ALG_LBL_SHA3_256, "password",
                                   "c0067d4af4e87f00dbac63b6156828237059172d1bbea" +
                                   "c67427345d6a9fda484"},
                                  {Constants.SECURITY_ALG_LBL_SHA512, "password",
                                   "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b" +
                                   "5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"},
                                  {Constants.SECURITY_ALG_LBL_SHA384, "password",
                                   "a8b64babd0aca91a59bdbb7761b421d4f2bb38280d3a75ba" +
                                   "0f21f2bebc45583d446c598660c94ce680c47d19c30783a7"},
                                  {Constants.SECURITY_ALG_LBL_SHA256, "password",
                                   "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd6" +
                                   "2a11ef721d1542d8"},
                                  {Constants.SECURITY_ALG_LBL_BLAKE2B_512, "password",
                                   "7c863950ac93c93692995e4732ce1e1466ad74a7753" +
                                   "52ffbaaf2a4a4ce9b549d0b414a1f3150452be6c7c72c694a7cb46f76452917298d33e67611f0a42addb8"},
                                  {Constants.SECURITY_ALG_LBL_BLAKE2B_384, "password",
                                   "78bb59097e9443312d702fe3f726ab54c1f22b4c788" +
                                   "7e7059a2b042425e872325dce9cc9610584d5eb3c79be3aa4da08"},
                                  {Constants.SECURITY_ALG_LBL_BLAKE2B_256, "password",
                                   "344b8a854221bd1eaf9382daaea1996fbcd496f158e" +
                                   "983f8835c7ef5084c55bb"},
                                  {Constants.SECURITY_ALG_LBL_SM3, "password",
                                   "08594e140bcc046e345325435218f67a85c38c63de6443" +
                                   "b197b544d70ee62f26"},};
        for (Object[] vector : testVectors) {
            String algorithm = (String) vector[0];
            byte[] password  = ((String) vector[1]).getBytes();
            byte[] salt      = new byte[0];
            byte[] expected  = Common.decodeHex((String) vector[2]);
            byte[] ret = Crypto.digest_or_kdf(algorithm,
                                              password,
                                              salt,
                                              null);
            Debug.get_instance()
                 .print("%s => %s ",
                        algorithm,
                        Common.encodeHex(ret));
            Assert.assertEquals(ret,
                                expected);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}


private LogMessageProcessor getLMProcessor() {
    if (debug) {
        return Info.get_instance();
    }

    return Debug.get_instance();
}

@Test(groups = "crypto",
      testName = "Asymmetric Encryption",
      description = "Checking for calculation stability.")
public void test_asymmetric_encryption() {
    if (ignore_tests)
        return;
    try {
        AsymmetricKeyPair ackp      = Asymmetric.generate_asymmetric_key_pairs();
        byte[]            plainText = "Forgiva Enterprise".getBytes();
        byte[] encrypted = Asymmetric.encrypt(ackp,
                                              plainText);
        getLMProcessor()
             .print("Encrypted: %s",
                    Common.encodeHex(encrypted));
        byte[] decrypted = Asymmetric.decrypt(ackp,
                                              encrypted);
        getLMProcessor()
             .print("Decrypted: %s",
                    Common.encodeHex(decrypted));
        Assert.assertEquals(plainText,
                            decrypted);
        byte[] long_text = new byte[1024 * 1024];
        System.arraycopy("Forgiva Enterprise".getBytes(),
                         0,
                         plainText,
                         0,
                         18);
        byte[] encrypted2 = Asymmetric.encrypt(ackp,
                                               long_text);
        byte[] decrypted2 = Asymmetric.decrypt(ackp,
                                               encrypted2);
        Assert.assertEquals(long_text,
                            decrypted2);
        AsymmetricKeyPair second_ackp = Asymmetric.generate_asymmetric_key_pairs();
        String public_key  = Asymmetric.get_public_key_as_hex(second_ackp);
        String private_key = Asymmetric.get_private_key_as_hex(second_ackp);
        AsymmetricKeyPair rackp = Asymmetric.init_cipherkeypair(public_key,
                                                                private_key);
        getLMProcessor()
             .print("Asymmetric public key: %s",
                    public_key);
        getLMProcessor()
             .print("Asymmetric private key: %s",
                    private_key);

        byte[] encrypted3 = Asymmetric.encrypt(rackp,
                                               plainText);

        byte[] decrypted3 = Asymmetric.decrypt(rackp,
                                               encrypted3);
        Assert.assertEquals(Asymmetric.decrypt(rackp,
                                               Asymmetric.encrypt(rackp,
                                                                  "marcus".getBytes("UTF-8"))),
                            "marcus".getBytes("UTF-8"));
        Assert.assertEquals(plainText,
                            decrypted3);

        AsymmetricKeyPair client_ackp      = Asymmetric.generate_asymmetric_key_pairs();
        AsymmetricKeyPair server_ackp      = Asymmetric.generate_asymmetric_key_pairs();

        byte[] client_pubkey = client_ackp.getPublicKey();
        byte[] client_prvkey = client_ackp.getPrivateKey();

        byte[] server_pubkey = server_ackp.getPublicKey();
        byte[] server_prvkey = server_ackp.getPrivateKey();

        getLMProcessor().print("Client pk / prvK : %s / %s",Common.encodeHex(client_pubkey), Common.encodeHex(client_prvkey));
        getLMProcessor().print("Server pk / prvK : %s / %s",Common.encodeHex(server_pubkey), Common.encodeHex(server_prvkey));

        AsymmetricKeyPair client_enc_ackp = Asymmetric.init_cipherkeypair_bytes(server_pubkey, client_prvkey);
        AsymmetricKeyPair server_dec_ackp = Asymmetric.init_cipherkeypair_bytes(client_pubkey, server_prvkey);

        byte[] client_to_server_encrypted = Asymmetric.encrypt_using_keypair(plainText, client_enc_ackp);
        byte[] server_decrypted           = Asymmetric.decrypt_using_keypair(client_to_server_encrypted,server_dec_ackp);

        getLMProcessor()
            .print("Encrypted: %s",
                   Common.encodeHex(client_to_server_encrypted));


        Assert.assertEquals(server_decrypted, plainText);


        // Test vectors
        server_pubkey   = Common.decodeHex("c0109230e383afe28e62c7e6fb364030af095f227d612b8e75a3a92be8e14a1d");
        server_prvkey   = Common.decodeHex("146ed33ede4b62e3cd72c28ce2dc3875c426889601af2d2aca1791cfb8eb4350");
        client_pubkey   = Common.decodeHex("14502444114a30cf1677b6205cf9f1bec4c64d6bae2066d5edb79bbdb4adc03f");
        client_prvkey   = Common.decodeHex("e6229b82690765951f892082f45f85d3881a34fcf4f37bc00119bcd5c4e6c45b");

        client_enc_ackp = Asymmetric.init_cipherkeypair_bytes(server_pubkey, client_prvkey);
        server_dec_ackp = Asymmetric.init_cipherkeypair_bytes(client_pubkey, server_prvkey);


        String enc01    = Common.encodeHex(Asymmetric.encrypt(client_enc_ackp,"abcdef".getBytes()));
        String enc02    = Common.encodeHex(Asymmetric.encrypt(server_dec_ackp,"abcdef".getBytes()));

        Assert.assertEquals(enc01, enc02);
        Assert.assertEquals(enc01, "00000000000000000000000000000000afa2913ed371e96f106b2504019d6776645b2a0f4f3c");

        Assert.assertEquals(Asymmetric.decrypt(server_dec_ackp,Common.decodeHex(enc01)),
                            Asymmetric.decrypt(client_enc_ackp,Common.decodeHex(enc01)));
    }
    catch (Exception e) {
        Assert.fail(e.getMessage(),
                    e);
    }
}
}
