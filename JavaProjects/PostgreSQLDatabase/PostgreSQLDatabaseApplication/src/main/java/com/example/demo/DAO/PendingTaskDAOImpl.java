package com.example.demo.DAO;

import com.example.demo.model.PendingTask;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PendingTaskDAOImpl implements PendingTaskDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PendingTask create(PendingTask pendingTask) {
        entityManager.persist(pendingTask);
        return pendingTask;
    }

    @Override
    public PendingTask findByReferenceNo(String referenceNo) {

        List<PendingTask> result =
                entityManager
                .createQuery("SELECT u FROM PendingTask u WHERE u.referenceNo =:referenceNo")
                .setParameter("referenceNo",referenceNo)
                // jangan getSingleResult, karena dia akan throw exception, bukan throw null kalo data kosong,
                        // nanti exceptionnya tabrakan sama validasi di bagian service;
//                .getSingleResult();
                .getResultList();

        if (result.isEmpty()){
            return null;
        }

        return result.get(0);
    }

    @Override
    public void deleteByReferenceNo(String referenceNo) {


            entityManager
                    .createQuery("DELETE FROM PendingTask u WHERE u.referenceNo =:referenceNo")
                    .setParameter("referenceNo",referenceNo)
                    .executeUpdate();

    }
}
