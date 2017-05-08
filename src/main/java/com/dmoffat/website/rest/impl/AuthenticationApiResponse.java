package com.dmoffat.website.rest.impl;

import com.dmoffat.website.rest.ApiResponse;

/**
 * @author dan
 */
public final class AuthenticationApiResponse implements ApiResponse {
    private final String authToken;

    public AuthenticationApiResponse(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthenticationApiResponse{");
        sb.append("authToken='").append(authToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
