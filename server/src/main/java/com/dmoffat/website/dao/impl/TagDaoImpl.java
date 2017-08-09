package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * @author dan
 */
@Repository("tagDao")
public class TagDaoImpl extends TagDao {
    @Override
    public Tag findOneByValue(String value) {
        TypedQuery<Tag> query = getEntityManager()
                .createQuery("from Tag t where t.value = :value", Tag.class);

        query.setParameter("value", value);

        Tag result;

        try {
            result = query.getSingleResult();
        } catch (NoResultException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public Tag findOrCreateIfDoesntExist(Tag tag) {
        Tag lookup = findOneByValue(tag.getValue());

        if(lookup == null) {
            create(tag);
            return tag;
        }

        return lookup;
    }
}
