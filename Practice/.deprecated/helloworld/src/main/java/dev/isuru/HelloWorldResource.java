package dev.isuru;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorldResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello() {
        return "<h1>Hello World</h1>";
    }
}
