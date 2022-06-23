package ru.itis.azat.ojs.controller;

import io.agrest.DataResponse;
import io.agrest.jaxrs2.AgJaxrs;
import ru.itis.azat.ojs.model.cayenne.ActionInfoContext;
import ru.itis.azat.ojs.model.cayenne.ActionInfoUser;
import ru.itis.azat.ojs.model.cayenne.ActionInfo;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/agrest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActionInfoApi {

    @Context
    private Configuration config;

    @GET
    @Path("/action-info")
    public DataResponse<ActionInfo> getActionInfo(@Context UriInfo uriInfo) {
        return AgJaxrs.select(ActionInfo.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();
    }

    @GET
    @Path("/action-info-user")
    public DataResponse<ActionInfoUser> getActionInfoUser(@Context UriInfo uriInfo) {
        return AgJaxrs.select(ActionInfoUser.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();
    }

    @GET
    @Path("/action-info-context")
    public DataResponse<ActionInfoContext> getActionInfoContext(@Context UriInfo uriInfo) {
        return AgJaxrs.select(ActionInfoContext.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();
    }


    @GET
    @Path("/action-info/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public DataResponse<ActionInfo> getFile(@Context UriInfo uriInfo, @Context HttpServletResponse servletResponse) {
        DataResponse<ActionInfo> dataResponse = AgJaxrs.select(ActionInfo.class, config)
                .clientParams(uriInfo.getQueryParameters())
                .get();

        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"api-response-" + System.currentTimeMillis() + ".json\"");

        return dataResponse;
    }

}