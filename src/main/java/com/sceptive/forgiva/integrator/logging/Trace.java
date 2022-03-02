package com.sceptive.forgiva.integrator.logging;


import org.apache.logging.log4j.Level;

public class Trace extends LoggingMessage implements LogMessageProcessor {

    public void print(String format, Object... params) {
        pl(Level.ERROR,format,params);
    }

    private static Trace instance;

    public static Trace get_instance() {
        if (instance == null) {
            instance = new Trace();
        }

        return instance;
    }

}