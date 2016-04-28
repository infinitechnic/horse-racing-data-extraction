package com.infinitechnic.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.scanning.PackageNamesScanner;

public class AppConfig extends ResourceConfig {

    public AppConfig() {
        super();

        // Create a recursive package scanner
        PackageNamesScanner resourceFinder = new PackageNamesScanner(new String[]{"com.infinitechnic.rest"}, true);
        // Register the scanner with this Application
        registerFinder(resourceFinder);
        register(JacksonFeature.class);
    }
}
