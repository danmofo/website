package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Patch;

/**
 * @author danielmoffat
 */
public class PatchDao extends GenericJpaDao<Patch, Long> {
    public PatchDao() {
        setType(Patch.class);
    }
}
