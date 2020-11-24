package io.flpmartins.dynamo;

public class TaskResponse {

    public String id;
    public String description;

    public TaskResponse(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
