package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.util.Properties;

public class BSLog4j extends IBootStrap {

    @Override
    public boolean bootstrap(Parameters _launch_options) {

        // Re-configuring Log4j
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.DEBUG);
        builder.setConfigurationName("Default");

        // Creating Root Logger
        RootLoggerComponentBuilder rlcb =  builder.newRootLogger( Level.ALL );

        // Creating output format based on configuration
        LayoutComponentBuilder layoutBuilder;

        if (Configuration.runtime.logging_output_format.equalsIgnoreCase("JSON")) {
            layoutBuilder = builder.newLayout("JsonLayout")
                    .addAttribute("complete", "true");
        }
        else {
            layoutBuilder = builder.newLayout("PatternLayout")
                    .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");
        }

        // Creating triggering policy for all log files
        ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                        .addAttribute("size", Configuration.runtime.logging_max_file_size));

        // Configuring general logs
        {

            AppenderComponentBuilder appenderBuilder = builder
                    .newAppender("RRAFA_INFO","RollingRandomAccessFile")
                    .addAttribute("fileName", Configuration.console_logging_file_path)
                    .addAttribute("filePattern", Configuration.console_logging_rolling_file_path)
                    .addAttribute("bufferedIO","true");
            appenderBuilder.add(builder.newLayout("ThresholdFilter")
                    .addAttribute("level", "INFO")
                    .addAttribute("onMatch", "ACCEPT")
                    .addAttribute("onMismatch","DENY"));
            appenderBuilder.add(layoutBuilder);
            appenderBuilder.addComponent(triggeringPolicy);
            builder.add( builder.newLogger( "InfoLogger", Level.INFO )
                    .add( builder.newAppenderRef( "RRAFA_INFO" ) )
                    .addAttribute("additivity", false));
            rlcb.add(builder.newAppenderRef("RRAFA_INFO"));
            builder.add(appenderBuilder);
        }


        // If debug logging is enabled, activating.
        if (Configuration.runtime.logging_generate_debug_log) {

            AppenderComponentBuilder appenderBuilder = builder
                    .newAppender("RRAFA_DEBUG","RollingRandomAccessFile")
                    .addAttribute("fileName", Configuration.debug_logging_file_path)
                    .addAttribute("filePattern", Configuration.debug_logging_rolling_file_path)
                    .addAttribute("bufferedIO","true");

            appenderBuilder.add(builder.newLayout("ThresholdFilter")
                    .addAttribute("level", "INFO")
                    .addAttribute("onMatch", "DENY")
                    .addAttribute("onMismatch","NEUTRAL"));
            appenderBuilder.add(builder.newLayout("ThresholdFilter")
                    .addAttribute("level", "DEBUG")
                    .addAttribute("onMatch", "ACCEPT")
                    .addAttribute("onMismatch","DENY"));
            appenderBuilder.add(layoutBuilder);
            appenderBuilder.addComponent(triggeringPolicy);
            builder.add( builder.newLogger( "DebugLogger", Level.DEBUG )
                    .add( builder.newAppenderRef( "RRAFA_DEBUG" ) )
                    .addAttribute("additivity", false));
            rlcb.add(builder.newAppenderRef("RRAFA_DEBUG"));
            builder.add(appenderBuilder);
        }

        builder.add(rlcb);


        Configurator.initialize(builder.build());


        bootstrapped = true;

        return bootstrapped;
    }



}
