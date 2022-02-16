package com.sceptive.forgiva.integrator.core.fsconnectors;

import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;

public interface IFSConnector {


    public void         execute(EMetadata _metadata,
                                String    _animal,
                                String    _password_hash,
                                String    _signature,
                                IFSResponse _response_listener);


}
