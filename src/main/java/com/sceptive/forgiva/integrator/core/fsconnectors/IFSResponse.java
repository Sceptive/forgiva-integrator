package com.sceptive.forgiva.integrator.core.fsconnectors;

public interface IFSResponse {

    public void ok(String _favorite_animal, String _password);
    public void failed(String _error_code);
}
