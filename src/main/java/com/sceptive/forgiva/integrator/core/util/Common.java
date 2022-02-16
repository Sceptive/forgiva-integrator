package com.sceptive.forgiva.integrator.core.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Common {


    public static ObjectMapper jckOMapper;

    static {
        jckOMapper = new ObjectMapper();
        jckOMapper.disable(MapperFeature.USE_ANNOTATIONS);
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
