package com.sceptive.forgiva.integrator.core;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;

import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class GOtp {
    public GOtp() {
    }

    public static String generate_random_secret(SecureRandom _secureRandom) {
        return (new Base32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=", false, true)).toString(_secureRandom.generateSeed(10));
    }

    private static String truncateHash(byte[] hash) {
        String hashString = new String(hash);
        int offset = Integer.parseInt(hashString.substring(hashString.length() - 1, hashString.length()), 16);
        String truncatedHash = hashString.substring(offset * 2, offset * 2 + 8);
        int val = Integer.parseUnsignedInt(truncatedHash, 16) & 2147483647;
        String finalHash = String.valueOf(val);
        finalHash = finalHash.substring(finalHash.length() - 6, finalHash.length());
        return finalHash;
    }

    private static byte[] hmacSha1(byte[] value, byte[] keyBytes) {
        SecretKeySpec signKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);
            byte[] rawHmac = mac.doFinal(value);
            return Common.encodeHex(rawHmac).getBytes();
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static String gotp_code_ex(String _secret, long _value) throws Exception {
        if (_secret != null && _secret != "") {
            Base32 base = new Base32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=", false, true);
            byte[] key = base.fromString(_secret);
            byte[] data = new byte[8];

            for(int i = 8; i-- > 0; _value >>>= 8) {
                data[i] = (byte)((int)_value);
            }

            byte[] hash = hmacSha1(data, key);
            return truncateHash(hash);
        } else {
            throw new InvalidValueException("Secret key does not exist.");
        }
    }

    public static String gotp_code(String _secret) throws Exception {
        long value = (new Date()).getTime() / TimeUnit.SECONDS.toMillis(30L);
        return gotp_code_ex(_secret, value);
    }
}