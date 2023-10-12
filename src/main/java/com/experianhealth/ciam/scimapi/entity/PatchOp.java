package com.experianhealth.ciam.scimapi.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


public class PatchOp {
    @JsonProperty("schemas")
    @JsonAlias("Schemas")
    private List<String> schemas;

    @JsonProperty("Operations")
    @JsonAlias("operations")
    private List<Operation> Operations;

    public List<Operation> getOperations() {
        return Operations;
    }

    public void setOperations(List<Operation> operations) {
        this.Operations = operations;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("schemas", schemas)
                .append("Operations", Operations)
                .toString();
    }
}

