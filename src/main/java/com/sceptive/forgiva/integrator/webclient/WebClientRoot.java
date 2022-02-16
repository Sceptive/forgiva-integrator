package com.sceptive.forgiva.integrator.webclient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Paths;

@Path("/wc")
public class WebClientRoot {

    private static final String BASE_PATH = System.getenv("FORGIVA_WC_ROOT_DIR");

    @GET
    @Path("/{fileName}")
    public Response getFile(@PathParam("fileName") String fileName) {
        if (BASE_PATH != null) {
            File file = Paths.get(BASE_PATH.toString(),
                                  fileName)
                             .toFile();
            if (file.exists()) {
                return Response.ok(file)
                               .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
