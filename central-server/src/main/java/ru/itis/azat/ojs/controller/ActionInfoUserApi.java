package ru.itis.azat.ojs.controller;

import io.agrest.DataResponse;
import io.agrest.jaxrs2.AgJaxrs;
import ru.itis.azat.ojs.model.ActionInfoUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("action-info-user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActionInfoUserApi {

    @Context
    private Configuration config;

    @POST
    public DataResponse<ActionInfoUser> create(@Context UriInfo uri, String data) {

        return AgJaxrs.create(ActionInfoUser.class, config)
                .clientParams(uri.getQueryParameters())
                .syncAndSelect(data);
    }

    @GET
    public DataResponse<ActionInfoUser> get(@Context UriInfo uriInfo) {
        return AgJaxrs.select(ActionInfoUser.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();
    }

}