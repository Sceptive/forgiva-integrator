package com.sceptive.forgiva.integrator.core.crypto.parameters;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;

/*
    Configuration file ARGON2_PARAMETERS holder by parsing value as constructor parameter
 */
public class Argon2Parameters extends Parameters {

    public final static String p_memory = "memory";
    public final static String p_iterations = "iterations";
    public final static String p_parallelization = "parallelization";
    public final static String p_output_size = "output_size";

    /*
        Parses iterations:memory:parallelization values as constructing type
     */
    public Argon2Parameters(String _configuration_value) throws InvalidValueException {

        String[] vals = _configuration_value.split(":");
        if (vals.length != 3) {
            throw new InvalidValueException(
                    String.format("Invalid value %s for Argon2 parameters",_configuration_value));
        }

        try {
            set_int_parameter(p_iterations,Integer.parseInt(vals[0]));
            set_int_parameter(p_memory,Integer.parseInt(vals[1]));
            set_int_parameter(p_parallelization,Integer.parseInt(vals[2]));
        } catch (NumberFormatException ex) {
            throw new InvalidValueException(
                    String.format("Invalid value %s for Argon2 parameters",_configuration_value));
        }

        set_int_parameter(p_output_size, Constants.CFG_ARGON2_OUTPUT_SIZE);


    }
}
