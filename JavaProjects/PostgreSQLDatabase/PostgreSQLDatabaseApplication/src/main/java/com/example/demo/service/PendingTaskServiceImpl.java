package com.example.demo.service;

import com.example.demo.DAO.PendingTaskDAO;
import com.example.demo.model.PendingTask;
import org.springframework.stereotype.Service;

@Service
public class PendingTaskServiceImpl implements PendingTaskService{

    private final PendingTaskDAO pendingTaskDAO;

    public PendingTaskServiceImpl(PendingTaskDAO pendingTaskDAO) {
        this.pendingTaskDAO = pendingTaskDAO;
    }

    @Override
    public PendingTask create(PendingTask pendingTask) {
        return pendingTaskDAO.create(pendingTask);
    }

    @Override
    public PendingTask findByReferenceNo(String referenceNo) {
        return pendingTaskDAO.findByReferenceNo(referenceNo);
    }

    @Override
    public void deleteByReferenceNo(String referenceNo) {
        pendingTaskDAO.deleteByReferenceNo(referenceNo);
    }
}
