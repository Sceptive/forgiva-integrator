package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;

import java.util.Properties;

public abstract class IBootStrap {


    protected boolean bootstrapped = false;

    public abstract boolean bootstrap(Parameters _launch_options);

    public boolean is_bootstrapped() {
        return bootstrapped;
    }
}
