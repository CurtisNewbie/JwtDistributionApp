package com.curtisnewbie.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class AdminDao {

    @Inject
    private EntityManager em;

    @Transactional(value = TxType.SUPPORTS)
    public Admin getAdmin(@NotNull String name) {
        return em.find(Admin.class, name);
    }
}