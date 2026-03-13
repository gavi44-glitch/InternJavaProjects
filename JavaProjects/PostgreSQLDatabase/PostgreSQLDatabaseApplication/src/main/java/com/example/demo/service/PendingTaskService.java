package com.example.demo.service;

import com.example.demo.model.PendingTask;

public interface PendingTaskService {
    public PendingTask create(PendingTask pendingTask);
    public PendingTask findByReferenceNo(String referenceNo);
    public void deleteByReferenceNo(String referenceNo);
}
