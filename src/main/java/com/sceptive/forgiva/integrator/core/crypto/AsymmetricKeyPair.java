package com.sceptive.forgiva.integrator.core.crypto;

public class AsymmetricKeyPair {
    private byte[] publicKey;
    private byte[] privateKey;

    public AsymmetricKeyPair(final byte[] _publicKey, final byte[] _privateKey) {
        publicKey  = _publicKey;
        privateKey = _privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final byte[] _publicKey) {
        publicKey = _publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(final byte[] _privateKey) {
        privateKey = _privateKey;
    }
}
