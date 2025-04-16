package dev.isuru;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getHello() {
        return Response.ok("<p>Hello World!</p>").build();
    }
}
