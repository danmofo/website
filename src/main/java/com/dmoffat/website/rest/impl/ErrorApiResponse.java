package com.dmoffat.website.rest.impl;

import com.dmoffat.website.rest.ApiResponse;

/**
 * @author dan
 */
public final class ErrorApiResponse implements ApiResponse {
    private final String errorCode;
    private final String errorMessage;

    public ErrorApiResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorApiResponse{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
