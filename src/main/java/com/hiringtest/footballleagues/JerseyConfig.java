package com.hiringtest.footballleagues;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // scan the resources package for our resources
        packages(getClass().getPackage().getName() + ".api");
    }
}