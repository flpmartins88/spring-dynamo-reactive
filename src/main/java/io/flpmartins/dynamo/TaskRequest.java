package io.flpmartins.dynamo;

import java.util.UUID;

public class TaskRequest {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task toDomain() {
        return new Task(UUID.randomUUID().toString(), description);
    }
}
