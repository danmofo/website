package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.UserDao;
import com.dmoffat.website.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * @author dan
 */
@Repository("userDao")
public class UserDaoImpl extends UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoImpl.class);
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = getEntityManager().createQuery("from User u where u.username = :username and password = :password", User.class);

        query.setParameter("username", username);
        query.setParameter("password", password);

        User found;

        try {
            found = query.getSingleResult();
        } catch (NoResultException exception) {
            found = null;
            logger.info("No user found for username=" + username + "and password=" + password);
        }

        return found;
    }

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = getEntityManager().createQuery("from User u where u.username = :username", User.class);

        query.setParameter("username", username);

        User found;

        try {
            found = query.getSingleResult();
        } catch (NoResultException exception) {
            found = null;
            logger.info("No user found for username=" + username);
        }

        return found;
    }
}
