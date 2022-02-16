package com.sceptive.forgiva.integrator.core.db;

import org.eclipse.persistence.exceptions.ConversionException;
import org.eclipse.persistence.internal.helper.ClassConstants;
import org.eclipse.persistence.internal.helper.ConversionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DBConversionManager extends ConversionManager {




    // Tries to parse from stirng to LocalDateTime for various configurations.
    private LocalDateTime forceToConvert(String _value) {

        LocalDateTime ret = null;

        String[] possibleFormats = new String[] {
                "yyyy-MM-dd'T'HH:mm:ss[.S][VV][x][xx][xxx]",
                "yyyy-MM-dd'T'HH:mm:ss[.SS][VV][x][xx][xxx]",
                "yyyy-MM-dd'T'HH:mm:ss[.SSS][VV][x][xx][xxx]",
                "yyyy-MM-dd'T'HH:mm:ss[.SSSS][VV][x][xx][xxx]",
                "yyyy-MM-dd'T'HH:mm:ss[.SSSSS][VV][x][xx][xxx]",
                "yyyy-MM-dd HH:mm:ss[.SSSSS][VV][x][xx][xxx]",
                "yyyy-MM-dd HH:mm:ss[.SSSS][VV][x][xx][xxx]",
                "yyyy-MM-dd HH:mm:ss[.SSS][VV][x][xx][xxx]",
                "yyyy-MM-dd HH:mm:ss[.SS][VV][x][xx][xxx]",
                "yyyy-MM-dd HH:mm:ss[.S][VV][x][xx][xxx]"
        };

        for (String possibleFormat  : possibleFormats) {

            try {
                TemporalAccessor tac = DateTimeFormatter.ofPattern(possibleFormat).parse(_value);
                ret = LocalDateTime.from(tac);

            } catch (java.time.format.DateTimeParseException _ex) {
                // Cannot parse... move on.
            }

            if (ret != null) {
                return ret;
            }

        }

        return null;

    }


    @Override
    protected LocalDateTime convertObjectToLocalDateTime(final Object sourceObject)
        throws ConversionException {
        LocalDateTime ret = null;
        // FIXES FE-74 => For Strings like  '2020-07-02T11:01:30.492+00' EclipseLink fails to parse DateTime
        try {
            ret = super.convertObjectToLocalDateTime(sourceObject);
        } catch (Exception ex) {
            // If it fails to parse... force for appropriate format
            if (sourceObject instanceof String) {
               ret = forceToConvert(((String)sourceObject));
            }
        }
        // If still cannot get parsed then throw exception
        if (ret == null) {
            throw ConversionException.couldNotBeConverted(sourceObject, ClassConstants.TIME_LDATETIME);
        }
        // Parsed successfully good to go
        return ret;

    }
}
