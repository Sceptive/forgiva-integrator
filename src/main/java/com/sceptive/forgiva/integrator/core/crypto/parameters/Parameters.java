package com.sceptive.forgiva.integrator.core.crypto.parameters;

import org.bouncycastle.crypto.params.Argon2Parameters;

import java.util.Hashtable;
import java.util.Properties;

public class Parameters  extends Hashtable<String,Object> {




    public int get_int_parameter(String _key) {
        Object val = get(_key);

        return val == null ? 0 : ((Integer)val).intValue();
    }

    public Parameters set_int_parameter(String _key, int _value) {

        put(_key,Integer.valueOf(_value));
        return this;
    }


    public boolean get_bool_parameter(String _key) {
        Object val = get(_key);

        return val == null ? false : ((Boolean)val).booleanValue();
    }

    public Parameters set_bool_parameter(String _key, boolean _value) {
        put(_key,Boolean.valueOf(_value));
        return this;
    }
}
