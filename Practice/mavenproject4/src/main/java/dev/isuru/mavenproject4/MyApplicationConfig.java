package dev.isuru.mavenproject4;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplicationConfig extends ResourceConfig {
public MyApplicationConfig() {
register(HelloWorldResource.class);
}
}