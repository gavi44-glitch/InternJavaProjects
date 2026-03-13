package com.example.demo.DAO;

import com.example.demo.model.PendingTask;

public interface PendingTaskDAO {
    public PendingTask create(PendingTask pendingTask);
    public PendingTask findByReferenceNo(String referenceNo);
    public void deleteByReferenceNo(String referenceNo);
}
