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



//long double fobj_strength_calc::num_combinations(String password)
//{
//
//    long double numCombinations = 0;
//
//    bool charTypes[7] = {0}; // bool charTypes[] = {0, 0, 0, 0, 0, 0, 0};
//
//    for (int i = 0; i < password.length(); i++) {
//        if (isdigit(password[i]))
//            charTypes[0] = 1; // NUMBERS
//        else if (islower(password[i]))
//            charTypes[1] = 1; // LOWERCASE
//        else if (isupper(password[i]))
//            charTypes[2] = 1; // UPPERCASE
//        else if (ispunct(password[i]))
//            charTypes[3] = 1;          // SYMBOLS
//        else if (password[i] == ' ') // was gonna use isspace(), but it doesn't
//            charTypes[4] = 1; // SPACE      // differentiate between tab vs space
//        else if (password[i] == '\t') // not many passwords contain tabs, but
//            charTypes[5] = 1;           // TAB        // why not check for it anyways?
//        else
//            charTypes[6] = 1; // OTHER
//    }
//
//    numCombinations = (charTypes[0] * NUMBERS) + (charTypes[1] * LOWERCASE) +
//                      (charTypes[2] * UPPERCASE) + (charTypes[3] * SYMBOLS) +
//                      (charTypes[4] * SPACE) + (charTypes[5] * TAB) +
//                      (charTypes[6] * OTHER);
//
//    numCombinations = pow(numCombinations, password.length());
//
//    return numCombinations;
//}
//double fobj_strength_calc::num_bits(long double n_comb)
//{
//
//    return floor(log(n_comb) / log(2));
//}


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
