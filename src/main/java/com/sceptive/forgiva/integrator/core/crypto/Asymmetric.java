package com.sceptive.forgiva.integrator.core.crypto;

import com.sceptive.forgiva.integrator.core.crypto.nacl.NaCl;
import com.sceptive.forgiva.integrator.core.crypto.nacl.ncrypto.curve25519xsalsa20poly1305;
import com.sceptive.forgiva.integrator.core.db.objects.ESession;
import com.sceptive.forgiva.integrator.logging.Info;

import java.util.Arrays;

import static com.sceptive.forgiva.integrator.core.crypto.nacl.ncrypto.curve25519xsalsa20poly1305.crypto_secretbox_PUBLICKEYBYTES;

/**
 * Asymmetric Cryptography Implementation Using NACL crypto_box
 */
public class Asymmetric {
private static byte[] tmp_nonce = new byte[curve25519xsalsa20poly1305.crypto_secretbox_NONCEBYTES];

/*
    Generates random Public and Private key pairs for NTRU algorithm and returns within
    AsymmetricCipherKeyPair
    object.
 */
public static AsymmetricKeyPair generate_asymmetric_key_pairs() {

    byte[] pubKey = new byte[crypto_secretbox_PUBLICKEYBYTES];
    byte[] prvKey = new byte[curve25519xsalsa20poly1305.crypto_secretbox_SECRETKEYBYTES];
    curve25519xsalsa20poly1305.crypto_box_keypair(pubKey,
                                                  prvKey);
    AsymmetricKeyPair ackp = new AsymmetricKeyPair(pubKey,
                                                   prvKey);
    return ackp;
}

public static AsymmetricKeyPair init_cipherkeypair_bytes(byte[] _public_key, byte[] _private_key)
        throws Exception {
    AsymmetricKeyPair kp = new AsymmetricKeyPair(_public_key,
                                                 _private_key);
    return kp;
}

/*
        Generates AsymmetricKeyPair object from hex encoded Public and Private key pairs for NTRU
         algorithm.

     */
public static AsymmetricKeyPair init_cipherkeypair(String _public_key, String _private_key)
        throws Exception {
    byte[] prvBytes = Common.decodeHex(_private_key);
    byte[] pubBytes = Common.decodeHex(_public_key);
    return init_cipherkeypair_bytes(pubBytes,
                                    prvBytes);
}

/*
    Returns public key as hexadecimal from AsymmetricCipherKeyPair
 */
public static String get_public_key_as_hex(AsymmetricKeyPair _ackp) {
    return Common.encodeHex(_ackp.getPublicKey());
}

/*
     Returns private key as hexadecimal from AsymmetricCipherKeyPair
*/
public static String get_private_key_as_hex(AsymmetricKeyPair _ackp) {
    return Common.encodeHex(_ackp.getPrivateKey());
}

/*
  Decrypts _to_decrypt using _key_pair and returns decrypted byte array.
*/
public static byte[] decrypt(AsymmetricKeyPair _key_pair, byte[] _to_decrypt) throws Exception {
    return decrypt_using_keypair(_to_decrypt,
                                 _key_pair);
}

/*
Decrypts data using ESession object
*/
public static byte[] decrypt_using_session(byte[] _to_decrypt, ESession _session) throws Exception {
    // Initializing Asymmetric Cryptography keypairs
    AsymmetricKeyPair asckp = Asymmetric.init_cipherkeypair(_session.client_public_key,
                                                            _session.private_key);

//    Info.get_instance().print("Server public key: %s", _session.public_key);
//    Info.get_instance().print("Client public key: %s", _session.client_public_key);

    // Decrypted username
    return Asymmetric.decrypt(asckp,
                              _to_decrypt);
}

/*
    Decrypts _to_decrypt using _key_pair and returns decrypted byte array.
 */
public static byte[] decrypt_using_keypair(byte[] _to_decrypt, AsymmetricKeyPair _key_pair)
        throws Exception {
    // Initialize engine
    NaCl nacl = new NaCl(_key_pair.getPrivateKey(),
                         _key_pair.getPublicKey());
    return nacl.decrypt(_to_decrypt,
                        tmp_nonce);
}

public static byte[] encrypt_using_session(byte[] _to_encrypt, ESession _session) throws Exception {

    // Initializing Asymmetric Cryptography keypairs
    AsymmetricKeyPair asckp = Asymmetric.init_cipherkeypair(_session.client_public_key,
                                                            _session.private_key);
//    Info.get_instance().print("Enc Server priva. key: %s", _session.private_key);
//    Info.get_instance().print("Enc Client public key: %s", _session.client_public_key);


    return Asymmetric.encrypt(asckp,_to_encrypt);
}

/*
    Encrypts _to_encrypt using _key_pair and returns encrypted byte array.
 */
public static byte[] encrypt(AsymmetricKeyPair _key_pair, byte[] _to_encrypt) throws Exception {
    // Strip public key from key pair and encrypt using it
    return encrypt_using_keypair(_to_encrypt,
                                 _key_pair);
}

/*
   Encrypts _to_encrypt using public key bytes and returns encrypted byte array.
*/
public static byte[] encrypt_using_keypair(byte[] _to_encrypt, AsymmetricKeyPair _key_pair)
        throws Exception {
    NaCl nacl = new NaCl(_key_pair.getPrivateKey(),
                         _key_pair.getPublicKey());

    Arrays.fill(tmp_nonce,(byte)0);

    return nacl.encrypt(_to_encrypt,
                        tmp_nonce);
}
}
