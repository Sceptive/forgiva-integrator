package com.sceptive.forgiva.integrator.logging;

import org.apache.logging.log4j.Level;

public class Error extends LoggingMessage implements LogMessageProcessor {

    public void print(String format, Object... params) {
        pl(Level.ERROR,format,params);
    }

    private static Error instance;

    public static Error get_instance() {
        if (instance == null) {
            instance = new Error();
        }

        return instance;
    }

}