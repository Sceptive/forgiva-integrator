package com.sceptive.forgiva.integrator.logging;

import org.apache.logging.log4j.Level;

public class Info extends LoggingMessage implements LogMessageProcessor {

    public void print(String format, Object... params) {
        pl(Level.INFO,format,params);
    }

    private static Info instance;

    public static Info get_instance() {
        if (instance == null) {
            instance = new Info();
        }

        return instance;
    }
}
