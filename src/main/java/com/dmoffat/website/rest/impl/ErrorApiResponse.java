package com.dmoffat.website.rest.impl;

import com.dmoffat.website.rest.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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

    public static ErrorApiResponse fromBindingResult(BindingResult result) {
        StringBuilder errorMessage = new StringBuilder("The following fields: ");

        for(FieldError fieldError : result.getFieldErrors()) {
            errorMessage.append(fieldError.getField() + ", ");
        }

        errorMessage.append("were missing from the request.");

        // todo: maybe make this response less specific for production
        return new ErrorApiResponse("200", errorMessage.toString());
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
