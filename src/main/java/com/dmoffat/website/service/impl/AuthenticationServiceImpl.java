package com.dmoffat.website.service.impl;

import com.dmoffat.website.dao.UserDao;
import com.dmoffat.website.model.User;
import com.dmoffat.website.service.AuthenticationService;
import com.dmoffat.website.service.exception.UsernameAlreadyExistsException;
import com.dmoffat.website.util.JwtsUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author dan
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserDao userDao;
    private JwtsUtils jwtsUtils;

    @Autowired
    public AuthenticationServiceImpl(UserDao userDao, JwtsUtils jwtsUtils) {
        this.userDao = userDao;
        this.jwtsUtils = jwtsUtils;
    }

    @Override
    public String createTokenForUser(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getUsername());

        return jwtsUtils.createJwsFor(user.getUsername());
    }

    @Override
    public boolean isValidToken(String token) {
        return jwtsUtils.parse(token) != null;
    }

    @Override
    public boolean login(User user) {
        Objects.requireNonNull(user, "user cannot be null.");
        Objects.requireNonNull(user.getUsername());
        Objects.requireNonNull(user.getPassword());

        User foundUser = userDao.findUserByUsername(user.getUsername());

        if(foundUser == null) {
            return false;
        }

        return BCrypt.checkpw(user.getPassword(), foundUser.getPassword());
    }

    @Override
    public void create(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getUsername());

        User existing = userDao.findUserByUsername(user.getUsername());

        if(existing != null) {
            throw new UsernameAlreadyExistsException("The username " + user.getUsername() + " is already taken.");
        }

        // Hash the user's plaintext password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));

        userDao.create(user);
    }
}
