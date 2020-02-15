package com.curtisnewbie.persistence;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

@Stateless
public class AdminDao {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(SUPPORTS)
    public Admin getAdmin(@NotNull String name) {
        return em.find(Admin.class, name);
    }
}