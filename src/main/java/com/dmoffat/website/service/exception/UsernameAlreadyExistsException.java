package com.dmoffat.website.service.exception;

/**
 * @author dan
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
