package com.pt.ara.quarkus.learning;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/hello")
public class GreetingResource {

    private final GreetingConfig config;
    private final String message;

    private static final Logger LOGGER = Logger.getLogger(GreetingResource.class);

    @Inject
    public GreetingResource(
            @ConfigProperty(defaultValue = "DefaultValue", name = "application.greeting.recipient") String message,
            GreetingConfig config
    ) {
        this.message = message;
        this.config = config;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOGGER.debug("@@@ Start");
        String msg = "Hello " + config.getRecipient() + " " + message;
        LOGGER.info("@@@ message: " + msg);
        return msg;
    }
}
