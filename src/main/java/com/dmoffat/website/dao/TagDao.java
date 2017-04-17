package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Tag;

/**
 * @author dan
 */
public abstract class TagDao extends GenericJpaDao<Tag, Long> {
    public TagDao() {
        setType(Tag.class);
    }

    public abstract Tag findOneByValue(String value);
}
