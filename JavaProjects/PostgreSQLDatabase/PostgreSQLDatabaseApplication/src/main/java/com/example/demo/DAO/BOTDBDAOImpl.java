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
import java.util.Map;

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

    //GAUSAH MERGE LAGI, KARENA UPDATE ENTITY ADA DALAM 1 TRANSAKSI YANG SAMA DENGAN detailDataSource
    // jadi flownya find botdb dulu -> baru update gitu
    // kalo udah di find, udah ada dalam persistence context, jadi kalo di .merge lagi -> redundant.

//    @Override
//    public BOTDB updateDataSource(String dataSourceCode, Map<String,Object> updates){
//        return (BOTDB) entityManager.merge(updates);
//    }

}
