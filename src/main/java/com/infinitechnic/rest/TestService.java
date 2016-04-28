package com.infinitechnic.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestService {

    @GET
    @Path("message")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return "Test";
    }
}