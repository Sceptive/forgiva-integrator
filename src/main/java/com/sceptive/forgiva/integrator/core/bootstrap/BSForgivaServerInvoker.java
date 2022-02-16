package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.ForgivaServerInvoker;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;

public class BSForgivaServerInvoker extends IBootStrap {


    @Override
    public boolean bootstrap(Parameters _launch_options) {

        // If not initialize then bootstrap failed
        if (ForgivaServerInvoker.get_instance() == null) {
            Fatal.get_instance().print("Could not initialize Forgiva Server invoker.");
        }

        Info.get_instance().print("Database connection bootstrapped successfully.");

        return true;
    }
}
