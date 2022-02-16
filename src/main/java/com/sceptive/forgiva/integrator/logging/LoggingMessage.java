package com.sceptive.forgiva.integrator.logging;

import com.sceptive.forgiva.integrator.core.BootStrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.fusesource.jansi.Ansi;

public abstract class LoggingMessage {



    private static String Colorize(Ansi.Color _color, String _text) {
        return Ansi.ansi().fg(_color).a(_text).reset().toString();
    }

    private static void  pc(Level level,String message) {

        System.out.println(String.format("[%s] %s",
                Colorize((level.intLevel() == Level.FATAL.intLevel() ? Ansi.Color.RED :
                        level.intLevel() == Level.ERROR.intLevel() ? Ansi.Color.YELLOW :
                                level.intLevel() == Level.INFO.intLevel() ? Ansi.Color.BLUE :
                                        level.intLevel() == Level.WARN.intLevel() ? Ansi.Color.MAGENTA
                                                : Ansi.Color.WHITE),level.name())
                ,message));
        System.out.flush();
    }

    static void pl(Level level,String format, Object... params) {
        String formatted_message = null;
        try {
            formatted_message = String.format(format,params);
        } catch (java.util.MissingFormatArgumentException ex) {
            formatted_message = "Invalid format: "+format;
            ex.printStackTrace();
        }

        if (BootStrapper.is_logging_initialized()) {
            LogManager.getLogger().log(level, formatted_message);
        }

        if (level.intLevel() <= Level.INFO.intLevel()) {
            pc(level, formatted_message);
        }
    }

    public abstract void print(String format, Object... params);

    public void print(Throwable thr) {
        print("Exception: %s %s",thr.getMessage(), JettyLog4j2Bridge.stacktrace_to_string(thr));
    }




}
