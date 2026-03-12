package com.example.demo.DAO;

import com.example.demo.model.PendingTask;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PendingTaskDAOImpl implements PendingTaskDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PendingTask create(PendingTask pendingTask) {
        entityManager.persist(pendingTask);
        return pendingTask;
    }
}
