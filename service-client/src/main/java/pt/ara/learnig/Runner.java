package pt.ara.learnig;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@QuarkusMain
public class Runner implements QuarkusApplication {

    @Inject
    @RestClient
    RoomServiceRest roomService;

    @Override
    public int run(String... args) {
        List<Room> rooms = roomService.getAllRooms();
        rooms.forEach(System.out::println);
        return 0;
    }
}
