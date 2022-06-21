package ru.itis.azat.ojs.controller;

import io.agrest.DataResponse;
import io.agrest.jaxrs2.AgJaxrs;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.model.ActionInfoData;

import javax.ws.rs.*;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("action-info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActionInfoApi {

    @Context
    private Configuration config;

    @POST
    public DataResponse<ActionInfoData> create(@Context UriInfo uri, String data) {
        return AgJaxrs.create(ActionInfoData.class, config)
                .clientParams(uri.getQueryParameters())
                .syncAndSelect(data);
    }

    @GET
    public DataResponse<ActionInfoData> get(@Context UriInfo uriInfo) {
        return AgJaxrs.select(ActionInfoData.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();
    }

}