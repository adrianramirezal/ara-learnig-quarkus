package com.pt.ara.quarkus.learning;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApplicationScoped
public class GreetingConfig {
    private final String recipient = "Learning";
}
