package com.example.demo.DAO;

import com.example.demo.model.BOTDB;
//import com.example.demo.utils.BaseHelper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    @Override
    public BOTDB detailDataSource(String dataSourceCode){
        try{
            return entityManager.
                    createQuery("SELECT u FROM BOTDB u WHERE u.dataSourceCode =:dataSourceCode", BOTDB.class)
                    .setParameter("dataSourceCode", dataSourceCode)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public BOTDB deleteDataSource(String dataSourceCode){

        BOTDB botdb = entityManager.find(BOTDB.class, dataSourceCode);

        if (botdb!=null){
            entityManager.remove(botdb);
        }
        return botdb;
    }

}
