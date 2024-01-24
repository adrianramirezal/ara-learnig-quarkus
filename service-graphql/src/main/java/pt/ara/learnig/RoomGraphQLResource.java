package pt.ara.learnig;

import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
public class RoomGraphQLResource {

    @Inject
    RoomService roomService;

    @Query("allRooms")
    @Description("Get all rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}