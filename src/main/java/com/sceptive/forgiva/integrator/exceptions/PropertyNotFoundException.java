package com.sceptive.forgiva.integrator.exceptions;

public class PropertyNotFoundException extends Exception {

    String property = null;
    String file     = null;

    public PropertyNotFoundException(String _property, String _file) {
        property    = _property;
        file        =  _file;
    }

    public String getProperty() {
        return property;
    }

    public String getFile() {
        return file;
    }

    public String toString() {
        return String.format("PropertyNotFoundException of %s in %s",property,file);
    }
}
