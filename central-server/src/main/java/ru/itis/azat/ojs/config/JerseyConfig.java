package ru.itis.azat.ojs.config;

import io.agrest.cayenne.AgCayenneModule;
import io.agrest.jaxrs2.AgJaxrsFeature;
import io.agrest.runtime.AgRuntime;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.azat.ojs.controller.ActionInfoApi;
import ru.itis.azat.ojs.controller.ActionInfoUserApi;

import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(@Autowired DataSource dataSource) {
        ServerRuntime cayenneRuntime = ServerRuntime
                .builder()
                .dataSource(dataSource)
                .addConfig("cayenne-project.xml")
                .build();


        AgRuntime agRuntime = AgRuntime
                .builder()
                .module(AgCayenneModule.build(cayenneRuntime))
                .build();


        register(AgJaxrsFeature.build(agRuntime));


        register(ActionInfoApi.class);
        register(ActionInfoUserApi.class);

    }
}
