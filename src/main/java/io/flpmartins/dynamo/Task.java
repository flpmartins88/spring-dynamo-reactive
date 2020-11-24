package io.flpmartins.dynamo;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Task {

    private final String id;
    private final String description;

    public Task(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Task fromAttributeMap(Map<String, AttributeValue> attributeMap) {
        var mapId = attributeMap.get(TASK_FIELD_ID).s();
        var mapDescription = attributeMap.get(TASK_FIELD_DESCRIPTION).s();

        return new Task(mapId, mapDescription);
    }

    public Map<String, AttributeValue> toAttributeMap() {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(TASK_FIELD_ID, AttributeValue.builder().s(id).build());
        map.put(TASK_FIELD_DESCRIPTION, AttributeValue.builder().s(description).build());

        return map;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static final String TASK_TABLE = "task";
    public static final String TASK_FIELD_ID = "id";
    public static final String TASK_FIELD_DESCRIPTION = "description";
}
