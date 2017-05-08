package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.User;

/**
 * @author dan
 */
public abstract class UserDao extends GenericJpaDao<User, Long> {
    public UserDao() {
        setType(User.class);
    }

    public abstract User findUserByUsernameAndPassword(String username, String password);
    public abstract User findUserByUsername(String username);
}
