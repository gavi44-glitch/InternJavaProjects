package com.example.demo.DAO;

import com.example.demo.model.BOTDB;
//import com.example.demo.utils.BaseHelper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BOTDBDAOImpl implements BOTDBDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public BOTDB create(BOTDB botdb) {
        entityManager.persist(botdb);
        return botdb;
    }


}
