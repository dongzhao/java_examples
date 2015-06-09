package com.dzhao.example.webservices.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(MyRestService.BASE_PATH)
public interface MyRestService {

    public static final String BASE_PATH = "/rest";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getInfo();

}
