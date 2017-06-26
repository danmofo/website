package com.dmoffat.website.rest.impl;

import com.dmoffat.website.rest.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SuccessApiResponse implements ApiResponse {
    private Map<String, Object> payload;
    private boolean success;

    private SuccessApiResponse() {
    }

    private SuccessApiResponse(Builder builder) {
        this.payload = builder.payload;
        this.success = builder.success;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public static final class Builder {
        private Map<String, Object> payload;
        private boolean success;

        public Builder() {
            payload = new HashMap();
            success = true;
        }

        public Builder addPayload(String key, Object value) {
            payload.putIfAbsent(key, value);
            return this;
        }

        public SuccessApiResponse build() {
            return new SuccessApiResponse(this);
        }
    }
}
