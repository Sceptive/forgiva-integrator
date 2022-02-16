package com.sceptive.forgiva.integrator.core.crypto;


import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Warning;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Common {

    private static final int max_safe_compare_iteration = 1000;
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static int random_next_int(int _max) {
        return ThreadLocalRandom.current().nextInt(0, _max);
    }

    public static boolean random_boolean() {
        return random_next_int(2) == 0;
    }


    public static boolean safe_compare(String a, String b) {

        // If any of the string's length longer than max iteration
        // then set iteration to it. This may be a flaw in the algorithm
        // but it will be feasible if any of the length will be bigger than
        // max_safe_compare_iteration value
        int iteration = (a.length() > max_safe_compare_iteration ? a.length() :
                (b.length() > max_safe_compare_iteration ? b.length() : max_safe_compare_iteration));

        for ( long i = 0; i < iteration; i++ ) {
            if ( a.charAt((int)(i % a.length())) != b.charAt((int)(i % b.length()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean check_if_hex(String value) {

        if (value.length() == 0)
            return false;
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                char uc = Character.toUpperCase(c);
                // Has letter but not letter used in hexadecimal representation
                if (uc != 'A' && uc != 'B' && uc != 'C'
                        && uc != 'D' && uc != 'E' && uc != 'F') {
                    return false;
                }
            } else if (!Character.isDigit(c)) {
                // Nor letter neither digit
                return false;
            }
        }

        return true;
    }



    public static byte[] decodeHex(String str) {
        char[] data = str.toCharArray();
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Invalid hex to decode");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Ilegal hexadecimal character");
        } else {
            return digit;
        }
    }

    public static String encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var4 = 0; i < l; ++i) {
            out[var4++] = DIGITS[(240 & data[i]) >>> 4];
            out[var4++] = DIGITS[15 & data[i]];
        }

        return new String(out);
    }

    public static String generate_password(int _length) {
        EMetadata tmp_metadata = new EMetadata();
        tmp_metadata.optNumbers = true;
        tmp_metadata.optSymbols = true;
        tmp_metadata.optUppercase = true;
        tmp_metadata.optLowercase = true;
        tmp_metadata.passwordLength = _length;

        SecureRandom sr =  null;

        try {
            sr = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            Warning.get_instance().print(e);
        }
        byte[] seed = new byte[32];

        if (sr != null) {
            seed = sr.generateSeed(seed.length);
        } else {
            new Random().nextBytes(seed);
        }

        return Hash2Password.gen_password(tmp_metadata,
                                   seed
                                   );


    }

}
