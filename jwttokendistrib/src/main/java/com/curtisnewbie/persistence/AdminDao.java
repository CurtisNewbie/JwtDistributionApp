package com.curtisnewbie.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class AdminDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(value = TxType.SUPPORTS)
    public Admin getAdmin(@NotNull String name) {
        return em.find(Admin.class, name);
    }
}