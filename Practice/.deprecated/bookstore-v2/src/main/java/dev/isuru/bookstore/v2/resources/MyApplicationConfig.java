package dev.isuru.bookstore.v2.resources;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig() {
        register(HelloWorldResource.class);
    }
}
