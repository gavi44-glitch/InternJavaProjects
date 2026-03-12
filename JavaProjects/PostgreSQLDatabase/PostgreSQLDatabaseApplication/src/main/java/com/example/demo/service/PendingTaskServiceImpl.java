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
}
