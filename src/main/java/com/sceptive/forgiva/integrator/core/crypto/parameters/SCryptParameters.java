package com.sceptive.forgiva.integrator.core.crypto.parameters;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;

/*
    Configuration file SCRYPT_PARAMETERS holder by parsing value as constructor parameter
 */
public class SCryptParameters extends Parameters {


    public final static String p_memory = "memory";
    public final static String p_block_size = "block_size";
    public final static String p_parallelization = "parallelization";
    public final static String p_output_size = "output_size";

     /*
        Parses memory:block size:parallelization values as constructing type
     */
    public SCryptParameters(String _configuration_value) throws InvalidValueException

    {

        String[] vals = _configuration_value.split(":");
        if (vals.length != 3) {
            throw new InvalidValueException(
                    String.format("Invalid value %s for SCrypt parameters",_configuration_value));
        }


        try {
            set_int_parameter(p_memory,Integer.parseInt(vals[0]));
            set_int_parameter(p_block_size,Integer.parseInt(vals[1]));
            set_int_parameter(p_parallelization,Integer.parseInt(vals[2]));
        } catch (NumberFormatException ex) {
            throw new InvalidValueException(
                    String.format("Invalid value %s for SCrypt parameters",_configuration_value));
        }

        set_int_parameter(p_output_size, Constants.CFG_SCRYPT_OUTPUT_SIZE);

    }
}
