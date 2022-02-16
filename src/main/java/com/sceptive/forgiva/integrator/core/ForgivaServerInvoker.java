package com.sceptive.forgiva.integrator.core;

import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.core.fsconnectors.EmbeddedConnector;
import com.sceptive.forgiva.integrator.core.fsconnectors.IFSConnector;
import com.sceptive.forgiva.integrator.core.fsconnectors.IFSResponse;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;

public class ForgivaServerInvoker implements IFSConnector {




    private static EmbeddedConnector    EMBEDDED_CONNECTOR = null;
    /* ***************************************************************************

        STATIC METHODS

    *************************************************************************** */
    private static ForgivaServerInvoker instance;

    /*
        Returns instance if not initialized statically
     */
    public static ForgivaServerInvoker get_instance() {
        if (instance == null) {
            try {
                instance = new ForgivaServerInvoker();
            } catch (Exception ex) {
                return null;
            }
        }

        return instance;
    }



    public ForgivaServerInvoker() {

        if (Configuration.runtime.fs_embedded) {
            try {
                EMetadata _test_data = new EMetadata();
                _test_data.host             = "test.com";
                _test_data.account          = "administrator";
                _test_data.passwordLength   = 16;
                _test_data.complexity       = 1;
                _test_data.optLowercase     = true;
                _test_data.optUppercase     = true;
                _test_data.optSymbols       = true;
                _test_data.optNumbers       = true;
                _test_data.lastRenewal      = "";

                EMBEDDED_CONNECTOR = EmbeddedConnector.get_instance();

                EMBEDDED_CONNECTOR.execute(_test_data,
                                           "Spider",
                                           "EE26B0DD4AF7E749AA1A8EE3C10AE9923F618980772E473F8819A5D4940E0DB27AC185F8A0E1D5F84F88BC887FD67B143732C304CC5FA9AD8E6F57F50028A8FF",
                                           "",
                                           new IFSResponse() {
                                              @Override
                                              public void ok(final String _favorite_animal,
                                                             final String _password) {

                                                  Info.get_instance().print("Forgiva Server sanity test passed %s - %s ",_favorite_animal,_password);
                                              }

                                              @Override
                                              public void failed(final String _error_code) {
                                                  Fatal.get_instance().print("Forgiva Server sanity test is failed: %s",_error_code);
                                              }
                                          });

            } catch (Exception _ex) {

                Warning.get_instance().print(_ex);
                Fatal.get_instance().print("Could not initialize embedded Forgiva Server");
            }
        }

        //TODO Host mode will be implemented
    }

    @Override
    public void execute(final EMetadata _metadata, final String _animal, final String _password_hash,
                        final String _signature, final IFSResponse _response_listener) {

        if (Configuration.runtime.fs_embedded) {

            EMBEDDED_CONNECTOR.execute(_metadata, _animal, _password_hash, _signature, _response_listener);
        }

    }
}
