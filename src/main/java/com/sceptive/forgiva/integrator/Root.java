package com.sceptive.forgiva.integrator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

@Path("/")
public class Root {



    @GET
    @Path("/")
    public Response getRoot() {


        //System.out.println("Path: "+uriInfo.getPath());
        return Response.seeOther(URI.create(
                                            "/wc/index.html")).build();
    }
}
