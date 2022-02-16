package com.sceptive.forgiva.integrator.logging;

import com.sun.org.apache.xerces.internal.util.MessageFormatter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

public class JettyLog4j2Bridge extends AbstractLogger {

    private String name;

    public JettyLog4j2Bridge(String _name) {
        name = _name;
    }

    @Override
    protected Logger newLogger(String _name) {
        return new JettyLog4j2Bridge(_name);
    }
    public String getName()
    {
        return name;
    }

    static String stacktrace_to_string(Throwable thrown) {
        StringWriter    sw = new StringWriter();
        PrintWriter     pw = new PrintWriter(sw);

        thrown.printStackTrace(pw);
        return sw.toString();
    }

    public void warn(String msg, Object... args)
    {
        Warning.get_instance().print(format(msg,args));
    }

    public void warn(Throwable thrown)
    {
        Warning.get_instance().print(stacktrace_to_string(thrown));
    }

    public void warn(String msg, Throwable thrown)
    {
        Warning.get_instance().print(String.format(" [%s] %s",msg,stacktrace_to_string(thrown)));
    }

    public void info(String msg, Object... args)
    {

        Info.get_instance().print(format(msg,args));
    }

    public void info(Throwable thrown)
    {
        Info.get_instance().print(stacktrace_to_string(thrown));
    }

    public void info(String msg, Throwable thrown)
    {
        Info.get_instance().print(String.format(" [%s] %s",msg,stacktrace_to_string(thrown)));
    }

    public boolean isDebugEnabled()
    {
        return true;
    }

    public void setDebugEnabled(boolean enabled)
    {

    }

    public void debug(String msg, Object... args)
    {
        Debug.get_instance().print(format(msg,args));
    }

    public void debug(Throwable thrown)
    {
        Debug.get_instance().print(stacktrace_to_string(thrown));
    }

    public void debug(String msg, Throwable thrown)
    {
        Debug.get_instance().print(String.format(" [%s] %s",msg,stacktrace_to_string(thrown)));
    }

    public void ignore(Throwable ignored)
    {
        Debug.get_instance().print(stacktrace_to_string(ignored));
    }

    // Taken from Jetty Sources
    private String format(String msg, Object... args)
    {
        msg = String.valueOf(msg); // Avoids NPE
        String braces = "{}";
        StringBuilder builder = new StringBuilder();
        int start = 0;
        for (Object arg : args)
        {
            int bracesIndex = msg.indexOf(braces, start);
            if (bracesIndex < 0)
            {
                builder.append(msg.substring(start));
                builder.append(" ");
                builder.append(arg);
                start = msg.length();
            }
            else
            {
                builder.append(msg.substring(start, bracesIndex));
                builder.append(String.valueOf(arg));
                start = bracesIndex + braces.length();
            }
        }
        builder.append(msg.substring(start));
        return builder.toString();
    }
}
