package com.experianhealth.ciam.scimapi.utils;


import com.experianhealth.ciam.CIAMTestBase;
import com.experianhealth.ciam.exception.CIAMInvalidRequestException;
import com.experianhealth.ciam.scimapi.entity.Operation;
import org.junit.jupiter.api.Test;

import javax.json.JsonPatch;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GroupPatchBuilderTest extends CIAMTestBase {

    @Test
    public void testApplyAddOperation() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("add");
        operation.setPath("members");
        Map<String, String> member = new HashMap<>();
        member.put("value", "123");
        operation.setValue(Arrays.asList(member));
        builder.applyOperation(operation);
        JsonPatch patch = builder.build();
        assertNotNull(patch);
    }


    @Test
    public void testApplyInvalidOperation() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("invalid");
        operation.setPath("members");
        assertThrows(CIAMInvalidRequestException.class, () -> builder.applyOperation(operation));
    }

    @Test
    public void testApplyOperationWithInvalidPath() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("add");
        operation.setPath("invalid");
        assertThrows(CIAMInvalidRequestException.class, () -> builder.applyOperation(operation));
    }

    @Test
    public void testApplyOperationWithInvalidValue() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("add");
        operation.setPath("members");
        operation.setValue("invalid");
        assertThrows(CIAMInvalidRequestException.class, () -> builder.applyOperation(operation));
    }

    @Test
    public void testApplyReplaceOperation() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("replace");
        operation.setPath("displayName");
        operation.setValue("New Group Name");
        builder.applyOperation(operation);
        JsonPatch patch = builder.build();
        assertNotNull(patch);
    }

    @Test
    public void testApplyReplaceDescriptionOperation() {
        GroupPatchBuilder builder = new GroupPatchBuilder();
        Operation operation = new Operation();
        operation.setOp("replace");
        operation.setPath("description");
        operation.setValue("Updated Group Description");
        builder.applyOperation(operation);
        JsonPatch patch = builder.build();
        assertNotNull(patch);
    }
}
