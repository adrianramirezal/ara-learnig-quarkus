package com.pt.ara.quarkus.learning;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

@QuarkusMain
public class Runner implements QuarkusApplication {
    @Inject
    QuarkusCqlSession cqlSession;

    private static final Logger LOG = Logger.getLogger(Runner.class);

    @Override
    public int run(String... args) {
        LOG.debug("@@@ Starting cmd-app");
        String cql = "select room_number, bed_info, name from lil.rooms";
        List<Room> rooms = new ArrayList<>();
        ResultSet rs = cqlSession.execute(cql);
        for (Row row : rs) {
            rooms.add(new Room(row.getString("room_number"), row.getString("bed_info"),
                    row.getString("name")));
        }
        rooms.forEach(System.out::println);
        LOG.debug("@@@ Closing cmd-app");
        return 0;
    }
}
