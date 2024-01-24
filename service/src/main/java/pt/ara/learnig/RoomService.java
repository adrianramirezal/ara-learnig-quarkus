package pt.ara.learnig;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RoomService {
    @Inject
    CqlSession cqlSession;

    private static final String getAllCql = "select room_number, bed_info, name from lil.rooms";
    private static final String getRoomsByBedInfoCql = "select room_number, bed_info, name from lil.rooms where bed_info = ?";
    private static final String addRoomCql = "insert into lil.rooms (room_number, bed_info, name) values (?, ?, ?)";
    private static final String getRoomByNumberCql = "select room_number, bed_info, name from lil.rooms where room_number = ?";

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<Room>();
        ResultSet rs = cqlSession.execute(getAllCql);
        for (Row row : rs) {
            rooms.add(new Room(row.getString("room_number"), row.getString("bed_info"), row.getString("name")));
        }
        return rooms;
    }

    public List<Room> getRoomsByBedInfo(String bedInfo) {
        List<Room> rooms = new ArrayList<>();
        PreparedStatement ps = cqlSession.prepare(getRoomsByBedInfoCql);
        BoundStatement bs = ps.bind(bedInfo);
        ResultSet rs = cqlSession.execute(bs);
        for (Row row : rs) {
            rooms.add(new Room(row.getString("room_number"), row.getString("bed_info"), row.getString("name")));
        }
        return rooms;
    }

    public Room addRoom(Room room) {
        if (room == null) {
            return null;
        }
        PreparedStatement ps = cqlSession.prepare(addRoomCql);
        BoundStatement bs = ps.bind(room.getRoomNumber(), room.getBedInfo(), room.getName());
        ResultSet rs = cqlSession.execute(bs);
        for (Row row : rs) {
            System.out.println(row);
        }
        return room;
    }

    public Room getRoomByNumber(String roomNumber) {
        Room room = null;
        PreparedStatement ps = cqlSession.prepare(getRoomByNumberCql);
        BoundStatement bs = ps.bind(roomNumber);
        ResultSet rs = cqlSession.execute(bs);
        for (Row row : rs) {
            room = new Room(row.getString("room_number"), row.getString("bed_info"), row.getString("name"));
        }
        return room;
    }
}
