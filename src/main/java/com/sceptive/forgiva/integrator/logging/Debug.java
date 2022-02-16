package com.sceptive.forgiva.integrator.logging;

import org.apache.logging.log4j.Level;

public class Debug extends LoggingMessage implements LogMessageProcessor {

    private static Debug instance;

    public static Debug get_instance() {
        if (instance == null) {
            instance = new Debug();
        }

        return instance;
    }

    public void print(String format, Object... params) {
        pl(Level.DEBUG,format,params);
    }




}