package com.sceptive.forgiva.integrator.logging;

import org.apache.logging.log4j.Level;

public class Warning extends LoggingMessage implements LogMessageProcessor {

    public void print(String format, Object... params) {
        pl(Level.WARN,format,params);
    }

    private static Warning instance;

    public static Warning get_instance() {
        if (instance == null) {
            instance = new Warning();
        }

        return instance;
    }

}
