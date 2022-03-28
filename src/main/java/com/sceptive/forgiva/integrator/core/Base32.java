package com.sceptive.forgiva.integrator.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Base32 {
    private String alphabet;
    private boolean padding;
    private boolean lowercase;

    public Base32(String alphabet, boolean padding, boolean lowercase) {
        this.alphabet = alphabet;
        this.padding = padding;
        this.lowercase = lowercase;
    }

    private static int blockLenToPadding(int blocklen) {
        switch(blocklen) {
            case 1:
                return 6;
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 1;
            case 5:
                return 0;
            default:
                return -1;
        }
    }

    private static int paddingToBlockLen(int padlen) {
        switch(padlen) {
            case 0:
                return 5;
            case 1:
                return 4;
            case 2:
            case 5:
            default:
                return -1;
            case 3:
                return 3;
            case 4:
                return 2;
            case 6:
                return 1;
        }
    }

    public String toString(byte[] b) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < (b.length + 4) / 5; ++i) {
            short[] s = new short[5];
            int[] t = new int[8];
            int blocklen = 5;

            int padlen;
            for(padlen = 0; padlen < 5; ++padlen) {
                if (i * 5 + padlen < b.length) {
                    s[padlen] = (short)(b[i * 5 + padlen] & 255);
                } else {
                    s[padlen] = 0;
                    --blocklen;
                }
            }

            padlen = blockLenToPadding(blocklen);
            t[0] = (byte)(s[0] >> 3 & 31);
            t[1] = (byte)((s[0] & 7) << 2 | s[1] >> 6 & 3);
            t[2] = (byte)(s[1] >> 1 & 31);
            t[3] = (byte)((s[1] & 1) << 4 | s[2] >> 4 & 15);
            t[4] = (byte)((s[2] & 15) << 1 | s[3] >> 7 & 1);
            t[5] = (byte)(s[3] >> 2 & 31);
            t[6] = (byte)((s[3] & 3) << 3 | s[4] >> 5 & 7);
            t[7] = (byte)(s[4] & 31);

            int j;
            for(j = 0; j < t.length - padlen; ++j) {
                char c = this.alphabet.charAt(t[j]);
                if (this.lowercase) {
                    c = Character.toLowerCase(c);
                }

                os.write(c);
            }

            if (this.padding) {
                for(j = t.length - padlen; j < t.length; ++j) {
                    os.write(61);
                }
            }
        }

        return new String(os.toByteArray());
    }

    public byte[] fromString(String str) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        byte[] raw = str.getBytes();

        for(int i = 0; i < raw.length; ++i) {
            char c = (char)raw[i];
            if (!Character.isWhitespace(c)) {
                c = Character.toUpperCase(c);
                bs.write((byte)c);
            }
        }

        if (this.padding) {
            if (bs.size() % 8 != 0) {
                return null;
            }
        } else {
            while(bs.size() % 8 != 0) {
                bs.write(61);
            }
        }

        byte[] in = bs.toByteArray();
        bs.reset();
        DataOutputStream ds = new DataOutputStream(bs);

        for(int i = 0; i < in.length / 8; ++i) {
            short[] s = new short[8];
            int[] t = new int[5];
            int padlen = 8;

            int blocklen;
            for(blocklen = 0; blocklen < 8; ++blocklen) {
                char c = (char)in[i * 8 + blocklen];
                if (c == '=') {
                    break;
                }

                s[blocklen] = (short)this.alphabet.indexOf(in[i * 8 + blocklen]);
                if (s[blocklen] < 0) {
                    return null;
                }

                --padlen;
            }

            blocklen = paddingToBlockLen(padlen);
            if (blocklen < 0) {
                return null;
            }

            t[0] = s[0] << 3 | s[1] >> 2;
            t[1] = (s[1] & 3) << 6 | s[2] << 1 | s[3] >> 4;
            t[2] = (s[3] & 15) << 4 | s[4] >> 1 & 15;
            t[3] = s[4] << 7 | s[5] << 2 | s[6] >> 3;
            t[4] = (s[6] & 7) << 5 | s[7];

            try {
                for(int j = 0; j < blocklen; ++j) {
                    ds.writeByte((byte)(t[j] & 255));
                }
            } catch (IOException var12) {
            }
        }

        return bs.toByteArray();
    }

    public static class Alphabet {
        public static final String BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=";
        public static final String BASE32HEX = "0123456789ABCDEFGHIJKLMNOPQRSTUV=";

        private Alphabet() {
        }
    }
}