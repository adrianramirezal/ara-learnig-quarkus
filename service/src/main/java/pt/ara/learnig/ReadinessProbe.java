package pt.ara.learnig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessProbe implements HealthCheck {

    @Inject
    RoomService roomService;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Custom Readiness Check")
                .up()
                .withData("roomCount", roomService.getAllRooms().size())
                .build();
    }
}

