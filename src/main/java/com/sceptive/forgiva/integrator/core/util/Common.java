package com.sceptive.forgiva.integrator.core.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sceptive.forgiva.integrator.logging.Info;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Common {


    public static ObjectMapper jckOMapper;

    static {
        jckOMapper = new ObjectMapper();
        jckOMapper.disable(MapperFeature.USE_ANNOTATIONS);
    }


    /*

        Returns string as compressed into base64 encode

     */
    public static String compress_string(final String str) throws IOException {
        if ((str == null) || (str.length() == 0)) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes("UTF-8"));
        gzip.flush();
        gzip.close();
        return base64_encode_ex(obj.toByteArray());
    }

    /*
        Decompresses base64 encoded ZIP content back to String

     */
    public static String decompress_string(final String _compressed) throws IOException {

        final byte[] compressed = base64_decode_ex(_compressed);

        final StringBuilder outStr = new StringBuilder();
        if ((compressed == null) || (compressed.length == 0)) {
            return "";
        }
        if (is_compressed(compressed)) {
            final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                outStr.append(line);
            }
        } else {
            outStr.append(compressed);
        }
        return outStr.toString();
    }

    public static boolean is_compressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                        && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }



    public static String base64_encode_ex(byte []_value) {
        try {
            byte[] _encoded = Base64.getEncoder()
                    .encode(_value);
            return new String(_encoded,
                    "UTF-8");
        }
        catch (UnsupportedEncodingException _uee) {
            Info.get_instance()
                    .print("Could not encode for UTF-8.");
        }
        return null;
    }

    public static String base64_encode(String _value) {
        try {
            byte[] _encoded = Base64.getEncoder()
                    .encode(_value.getBytes("UTF-8"));

            return base64_encode_ex(_encoded);
        }
        catch (UnsupportedEncodingException _uee) {
            Info.get_instance()
                    .print("Could not encode for UTF-8.");
        }
        return null;
    }

    public static byte[] base64_decode_ex(String _value) {
        byte[] _decoded = Base64.getDecoder()
                .decode(_value);

        return _decoded;

    }

    public static String base64_decode(String _value) {
        try {
            byte[] _decoded = base64_decode_ex(_value);

            return new String(_decoded,
                    "UTF-8");
        }
        catch (UnsupportedEncodingException _uee) {
            Info.get_instance()
                    .print("Could not encode for UTF-8.");
        }

        return null;
    }

    public static String get_non_null(String... vals) {
        for (String val : vals)
            if (val != null)
                return val;
        return null;
    }

    public interface ObjectFetchListener {
        void fetched(Object _o) throws Exception;
    }

    public static String format_local_date_time(LocalDateTime _ldt) {

        String ret = "";
        if (_ldt != null) {
            return _ldt.format(DateTimeFormatter.ISO_DATE)+" "+_ldt.format(DateTimeFormatter.ISO_TIME);
        }

        return ret;

    }

    public static LocalDateTime ld_minus_minutes(int interval) {

    	return LocalDateTime.now().minus(interval,
                                         ChronoUnit.MINUTES);
    }
}
