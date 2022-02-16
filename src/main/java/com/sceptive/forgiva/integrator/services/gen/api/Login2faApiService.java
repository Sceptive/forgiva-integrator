package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import com.sceptive.forgiva.integrator.services.gen.model.PostLogin2faRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostLogin2faResponse;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public abstract class Login2faApiService {
    public abstract Response postLogin2fa(PostLogin2faRequest postLogin2faRequest
 ) throws NotFoundException;
}
