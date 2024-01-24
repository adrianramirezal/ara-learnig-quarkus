package pt.ara.learnig;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;

@Path("/rooms")
@ApplicationScoped
@RegisterRestClient(configKey="rooms-api")
public interface RoomServiceRest {

    @GET
    List<Room> getAllRooms();
}
