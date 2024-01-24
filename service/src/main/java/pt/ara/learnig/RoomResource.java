package pt.ara.learnig;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@Path("/rooms")
public class RoomResource {
    @Inject
    RoomService roomService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms(@QueryParam("bedInfo") String bedInfo) {
        if (!bedInfo.isBlank()) {
            return roomService.getRoomsByBedInfo(bedInfo);
        }
        return roomService.getAllRooms();
    }

    @GET
    @Path("/{roomNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("roomNumber") String roomNumber) {
        return roomService.getRoomByNumber(roomNumber);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Room createRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }
}
