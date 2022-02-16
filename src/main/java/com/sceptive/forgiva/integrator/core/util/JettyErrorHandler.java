package com.sceptive.forgiva.integrator.core.util;

import com.sceptive.forgiva.integrator.logging.Error;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyErrorHandler extends ErrorHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doError(target, baseRequest, request, response);
    }

    @Override
    public void doError(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Error.get_instance().print("Web server error: %s (%s) %s",baseRequest.getRequestURL(), Network.get_ip_address(request));
        baseRequest.setHandled(true);
        baseRequest.getResponse().closeOutput();
    }


}
