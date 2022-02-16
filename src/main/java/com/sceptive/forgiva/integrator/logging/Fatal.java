package com.sceptive.forgiva.integrator.logging;

import org.apache.logging.log4j.Level;

public class Fatal extends LoggingMessage implements LogMessageProcessor {


    public void print(String format, Object... params) {
        pl(Level.FATAL,format,params);
        System.exit(1);
    }

    @Override
    public void print(final Throwable thr) {
        Fatal.this.print("Exception: %s %s",thr.getMessage(), JettyLog4j2Bridge.stacktrace_to_string(thr));
        System.exit(1);
    }

    private static Fatal instance;

    public static Fatal get_instance() {
        if (instance == null) {
            instance = new Fatal();
        }

        return instance;
    }

}