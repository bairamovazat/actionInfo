package ru.itis.azat.ojs.config;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.itis.azat.ojs.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;

import javax.ws.rs.ApplicationPath;


@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = "ru.itis.azat.ojs")
@EnableJpaRepositories(basePackages = "ru.itis.azat.ojs.repository")
@EntityScan(basePackages = "ru.itis.azat.ojs.model")
@Import({AppConfiguration.class, SecurityConfig.class})
public class AppInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            log.error(e.getMessage(), e);
        });
        configureApplication(new SpringApplicationBuilder(JerseyConfig.class)).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        builder.bannerMode(Banner.Mode.OFF);
        return builder.sources(AppInitializer.class);
    }

    //Чтобым можно было запускать через war
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return configureApplication(builder);
    }

}