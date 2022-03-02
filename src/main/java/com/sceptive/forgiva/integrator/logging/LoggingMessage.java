package com.sceptive.forgiva.integrator.logging;

import com.sceptive.forgiva.integrator.core.BootStrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.fusesource.jansi.Ansi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class LoggingMessage {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static String Colorize(Ansi.Color _color, String _text) {
        return Ansi.ansi().fg(_color).a(_text).reset().toString();
    }

    public static void  pc(Level level,String message) {

        Ansi.Color p_color = (level.intLevel() == Level.FATAL.intLevel() ? Ansi.Color.RED :
                level.intLevel() == Level.ERROR.intLevel() ? Ansi.Color.YELLOW :
                        level.intLevel() == Level.INFO.intLevel() ? Ansi.Color.BLUE :
                                level.intLevel() == Level.WARN.intLevel() ? Ansi.Color.MAGENTA :
                                        level.intLevel() == Level.DEBUG.intLevel() ? Ansi.Color.CYAN :
                                                level.intLevel() == Level.TRACE.intLevel() ? Ansi.Color.GREEN
                                                        : Ansi.Color.WHITE);

        System.out.println(String.format("[%s] [%s] %s",
                Colorize(p_color,level.name()),
                Colorize(p_color,formatter.format(LocalDateTime.now()))
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

        pc(level, formatted_message);
    }

    public abstract void print(String format, Object... params);

    public void print(Throwable thr) {
        print("Exception: %s %s",thr.getMessage(), JettyLog4j2Bridge.stacktrace_to_string(thr));
    }




}
