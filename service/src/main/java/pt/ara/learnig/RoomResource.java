package pt.ara.learnig;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("/rooms")
public class RoomResource {
    @Inject
    RoomService roomService;
    @Inject
    MeterRegistry meterRegistry;

    private final static Logger LOG = Logger.getLogger(RoomResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms(@QueryParam("bedInfo") String bedInfo) {
        Timer timer = Timer.builder("roomservice").tag("method", "getAllRooms").register(meterRegistry);
        long start = System.nanoTime();
        LOG.info("entering getAllRooms");
        if (bedInfo != null && !bedInfo.isBlank()) {
            return roomService.getRoomsByBedInfo(bedInfo);
        }
        LOG.info("exiting getAllRooms");
        timer.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
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
