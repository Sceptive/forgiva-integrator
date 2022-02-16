package com.sceptive.forgiva.integrator.logging;

public interface LogMessageProcessor {

    public static LogMessageProcessor get_instance() { return  null; };

    public void print(String format, Object... params);
}
