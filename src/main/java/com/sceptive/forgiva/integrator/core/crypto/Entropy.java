package com.sceptive.forgiva.integrator.core.crypto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Entropy {



    private static final int NUMBERS  = 10;
    private static final int LOWERCASE  = 26;
    private static final int UPPERCASE  = 26;
    private static final int SYMBOLS  = 32;
    private static final int SPACE  = 1;
    private static final int TAB  = 1;
    private static final int OTHER  = 1;


    public static double num_bits(char[] _password) {
        return num_bits(num_combinations(_password));
    }

    static double num_combinations(char[] _password)
    {

        long  numCombinations = 0;

        byte[] charTypes = {0,0,0,0,0,0,0};

        for (int i = 0; i < _password.length; i++) {
            char chr = _password[i];
            if (Character.isDigit(chr))
                charTypes[0] = 1; // NUMBERS
            else if (Character.isLowerCase(chr))
                charTypes[1] = 1; // LOWERCASE
            else if (Character.isUpperCase(chr))
                charTypes[2] = 1; // UPPERCASE
            else if (!Character.isLetterOrDigit(chr) &&
                    !Character.isSpaceChar(chr) && chr != '\t')
                charTypes[3] = 1;          // SYMBOLS
            else if (Character.isSpaceChar(chr))
                charTypes[4] = 1; // SPACE
            else if (chr == '\t')
                charTypes[5] = 1; // TAB
            else
                charTypes[6] = 1; // OTHER
        }

        numCombinations = (charTypes[0] * NUMBERS) + (charTypes[1] * LOWERCASE) +
                (charTypes[2] * UPPERCASE) + (charTypes[3] * SYMBOLS) +
                (charTypes[4] * SPACE) + (charTypes[5] * TAB) +
                (charTypes[6] * OTHER);


        return Math.pow(Long.valueOf(numCombinations).doubleValue(),
                Long.valueOf(_password.length).doubleValue());


    }

    static double num_bits(double n_comb) {
        return Math.floor(Math.log(n_comb) / Math.log(2));
    }
}
