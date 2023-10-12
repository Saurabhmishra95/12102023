package com.experianhealth.ciam.scimapi.utils;

import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonPatch;

import com.experianhealth.ciam.exception.CIAMInvalidRequestException;
import com.experianhealth.ciam.scimapi.entity.Operation;

public class GroupPatchBuilder {

    private static final String ADD = "add";
    private static final String REPLACE = "replace";
    private static final String REMOVE = "remove";


    private final JsonArrayBuilder operations = Json.createArrayBuilder();

    private GroupPatchBuilder applyAttributeOperation(String op, String path, Object value) {
        if (value != null || REMOVE.equalsIgnoreCase(op)) {
            JsonObjectBuilder operationBuilder = Json.createObjectBuilder()
                    .add("op", op)
                    .add("path", path);
            if (value != null) {
                if (value instanceof JsonObject) {
                    operationBuilder.add("value", (JsonObject) value);
                } else {
                    operationBuilder.add("value", value.toString());
                }
            }
            operations.add(operationBuilder.build());
        }
        return this;
    }

    private void handleMembersOperation(String op, Object value) {
        if (value instanceof List) {
            List<Map<String, String>> members = (List<Map<String, String>>) value;
            for (Map<String, String> member : members) {
                String userId = member.get("value");
                String memberPath = "/members/-";
                if (REMOVE.equalsIgnoreCase(op)) {
                    memberPath = "/members[value=\"" + userId + "\"]";
                }
                JsonObject memberValue = Json.createObjectBuilder().add("_ref", "managed/user/" + userId).build();
                applyAttributeOperation(op, memberPath, memberValue);
            }
        } else {
            throw new CIAMInvalidRequestException("The value for 'members' is not of type List");
        }
    }

    public void applyOperation(Operation operation) {
        String op = operation.getOp().toLowerCase();
        switch (op) {
            case ADD:
            case REPLACE:
            handleAttributeOperation(op, operation);
                break;
            default:
                throw new CIAMInvalidRequestException("Unsupported operation: " + op);
        }
    }

    private void handleAttributeOperation(String op, Operation operation) {
        String path = operation.getPath();
        Object value = operation.getValue();

        switch (path.toLowerCase()) {
            case "members":
                handleMembersOperation(op, value);
                break;
            case "displayname":
                applyAttributeOperation(op, "name", value);
            case "description":
                applyAttributeOperation(op, "description", value);
                break;
            default:
                throw new CIAMInvalidRequestException("Unsupported group attribute path: " + path);
        }
    }


    public JsonPatch build() {
        JsonPatch patch = Json.createPatch(operations.build());
        return patch;
    }
}
