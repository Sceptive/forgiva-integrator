package com.sceptive.forgiva.integrator;

import com.sceptive.forgiva.integrator.core.BootStrapper;
import com.sceptive.forgiva.integrator.core.Mailer;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.webclient.WebClientRoot;
import org.eclipse.jetty.util.log.Log;
import org.wso2.msf4j.MicroservicesRunner;
import org.wso2.msf4j.Request;
import org.wso2.msf4j.Response;
import org.wso2.msf4j.interceptor.RequestInterceptor;
import org.wso2.msf4j.interceptor.ResponseInterceptor;

import javax.ws.rs.Path;

import static com.sceptive.forgiva.integrator.core.Constants.WEB_API_BASEPATH;

public class Main {
static final String APPLICATION_PATH = "/integrator";
static final String CONTEXT_ROOT     = "/";

public static void main(String[] args) {

    // Launch hash generator utility if runned with fhash parameter.
    if (args.length > 0 && args[0].equalsIgnoreCase("fhash")) {
        BootStrapper.initialize_for_toollkit();
        try {
            FHashGenerator.generate(null);
        }
        catch (Exception e) {
            Fatal.get_instance()
                 .print(e);
        }
        System.exit(0);
    }
    Log.setLog(new com.sceptive.forgiva.integrator.logging.JettyLog4j2Bridge("JettyLog"));
    try {
        if (BootStrapper.initialize_application()) {
            System.setProperty("java.net.preferIPv4Stack",
                               "true");


            Main.run();
        }
    }
    catch (Throwable t) {
        t.printStackTrace();
    }
};

private static String get_updated_base_path(String _prefix, Object _object) {

    String path = _object.getClass().getAnnotation(Path.class).value();

    if (path == null) {
        return null;
    }

    return _prefix + path;
};

public static void run() throws Exception {

    //TODO LDAP integration

    try {

        Object[]            api_services = new Object[] {
                new LoginApi(),
                new LogoutApi(),
                new Login2faApi(),
                new NewSessionApi(),
                new UserApi(),
                new AdminApi()            
        };

        MicroservicesRunner msr = IntegratorMicroservicesRunner.get_runner();

        for (Object api_service : api_services) {
            msr.deploy(get_updated_base_path(WEB_API_BASEPATH ,api_service),api_service);
        }

        // Deploying web client app
        msr.deploy(new WebClientRoot());
        msr.deploy(new Root());
        msr.addGlobalRequestInterceptor(new RequestInterceptor() {
            @Override
            public boolean interceptRequest(final Request _request, final Response _response)
                    throws Exception {
                if (_request != null
                        && _request.getUri().equals("/")
                        && _request.getHttpMethod().equalsIgnoreCase("GET")) {
                    //System.out.println(" URI:" + _request.getUri() + " Method:" + _request.getHttpMethod());
                    _response.setStatus(301);
                    _response.setHeader("Location","/wc/index.html");
                    _response.send();
                    return false;
                }

                return true;
            }
});

        msr.start();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}
}
