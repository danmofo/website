package com.dmoffat.website.service;

import com.dmoffat.website.model.User;

/**
 * @author dan
 */
public interface AuthenticationService {
    String createTokenForUser(User user);
    boolean isValidToken(String token);
    boolean login(User user);
}
