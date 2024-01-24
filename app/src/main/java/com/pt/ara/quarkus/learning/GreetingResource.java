package com.pt.ara.quarkus.learning;

import io.quarkus.arc.Arc;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/lq")
public class GreetingResource {

    private final GreetingConfig config;
    private final String message;

    private static final Logger LOGGER = Logger.getLogger(GreetingResource.class);

    @Inject
    DataSource dataSource;
    @Inject
    EntityManager entityManager;

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
    @Path("/hello")
    public String hello() {
        LOGGER.debug("@@@ Start");
        String msg = "Hello " + config.getRecipient() + " " + message;
        LOGGER.info("@@@ message: " + msg);
        return msg;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rooms")
    public List<Room> getRooms() {
        LOGGER.debug("@@@ Start Select");
        String sql = "SELECT ROOM_ID, NAME, ROOM_NUMBER, BED_INFO FROM ROOM";
        List<Room> rooms = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    rooms.add(new Room(rs.getLong("ROOM_ID"), rs.getString("NAME"), rs.getString("ROOM_NUMBER"),
                            rs.getString("BED_INFO")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rooms.forEach(System.out::println);
        LOGGER.debug("@@@ End Select");
        return rooms;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rooms/v2")
    public List<Room> getRoomsV2() {
        LOGGER.debug("@@@ Start Select");
        Arc.container().requestContext().activate();
        List<Room> rooms = entityManager.createQuery("select r from Room r", Room.class).getResultList();
        rooms.forEach(System.out::println);
        Arc.container().requestContext().deactivate();
        LOGGER.debug("@@@ End Select");
        return rooms;
    }
}
