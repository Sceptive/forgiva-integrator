package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.StatCollector;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;

public class BSStatCollector extends IBootStrap {




    @Override
    public boolean bootstrap(final Parameters _launch_options) {

        // Invoking initialization and scheduling of statistics collector
        StatCollector collector = StatCollector.get_instance();

        return true;
    }
}
