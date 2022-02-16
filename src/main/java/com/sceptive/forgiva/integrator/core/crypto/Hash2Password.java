package com.sceptive.forgiva.integrator.core.crypto;

import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Warning;

import java.util.Vector;

public class Hash2Password {



    static Vector<String> lowercase_chars   = new Vector<>(0);
    static Vector<String> uppercase_chars   = new Vector<>(0);
    static Vector<String> numbers           = new Vector<>(0);
    static Vector<String> symbols           = new Vector<>(0);

    static {
        for (char i = 'a'; i <= 'z'; i++) {
            lowercase_chars.add(String.valueOf(i));
            uppercase_chars.add(String.valueOf(i)
                                      .toUpperCase());
        }
        for (int i = 0; i <= 9; i++) {
            numbers.add(String.valueOf(i));
        }
        for (char c : "./\\+-`=#!@$%^&*()_.;}{[]".toCharArray()) {
            symbols.add(String.valueOf(c));
        }
    }

    public static String gen_password(EMetadata _metadata, byte[] vals) {

        String final_password = null;

        String[]       password_chars;

        int             required_score = 0;
        password_chars = new String[(_metadata.optLowercase ? lowercase_chars.size() : 0)+
                                    (_metadata.optUppercase ? uppercase_chars.size() : 0)+
                                    (_metadata.optNumbers   ? numbers.size() : 0)+
                                    (_metadata.optSymbols   ? symbols.size() : 0)];


        Vector<Vector<String>> available_character_families = new Vector<Vector<String>>(0);
        if (_metadata.optLowercase) {
            available_character_families.add(lowercase_chars);
            required_score++;
        }
        if (_metadata.optUppercase) {
            available_character_families.add(uppercase_chars);
            required_score++;
        }
        if (_metadata.optNumbers) {
            available_character_families.add(numbers);
            required_score++;
        }
        if (_metadata.optSymbols) {
            available_character_families.add(symbols);
            required_score++;
        }

        if (required_score == 0) {
            return "";
        }


        int idx = 0;
        for (Vector<String> family : available_character_families){
            for (String character : family) {
                password_chars[idx] = character;
                idx++;
            }
        }

        byte[] _vals = vals;
        while (final_password == null) {
            StringBuilder g_password = new StringBuilder();

            for (byte val : _vals) {


                int ui_val = 0x00 << 24 | val & 0xff;

                int abs_val = ui_val % password_chars.length;
                g_password.append(password_chars[abs_val]);

                //System.out.println("Val: "+ui_val+" Len: "+password_chars.length+" Abs: "+abs_val+" Chr: "+password_chars[abs_val]);
            }

            for (int i = 0; i < g_password.length() - _metadata.passwordLength; i++) {
                String nominee_pass = g_password.substring(i, i + _metadata.passwordLength );

                boolean has_lowercase   = false;
                boolean has_uppercase   = false;
                boolean has_numbers     = false;
                boolean has_symbols     = false;
                int     score           = 0;

                for (String lc : lowercase_chars) {
                    if (nominee_pass.contains(lc)) {
                        has_lowercase = true;
                        break;
                    }
                }

                for (String lc : uppercase_chars) {
                    if (nominee_pass.contains(lc)) {
                        has_uppercase = true;
                        break;
                    }
                }

                for (String lc : numbers) {
                    if (nominee_pass.contains(lc)) {
                        has_numbers = true;
                        break;
                    }
                }

                for (String lc : symbols) {
                    if (nominee_pass.contains(lc)) {
                        has_symbols = true;
                        break;
                    }
                }
                if (has_lowercase && _metadata.optLowercase) {
                    score++;
                }
                if (has_uppercase && _metadata.optUppercase) {
                    score++;
                }
                if (has_numbers && _metadata.optNumbers) {
                    score++;
                }
                if (has_symbols && _metadata.optSymbols) {
                    score++;
                }

                if (score == required_score) {
                    final_password = nominee_pass;
                    break;
                }


            }
            try {
                _vals = FHashGenerator.hash_for_model("SHA-512",_vals,_vals);
            }
            catch (Exception _e) {
                Warning.get_instance().print(_e);
            }
        }

        return final_password;

    }



}
