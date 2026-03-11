package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_trail")
public class AuditTrail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cd")
    private String auditCode;
    @Column(name = "created_dt")
    private Timestamp createdDt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "action")
    private String action;
    @Column(name = "status")
    private String status;
    @Column(name="menu")
    private String menu;
    @Column(name="old_data")
    private String oldData;
    @Column(name="new_data")
    private String newData;

    public String getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(String auditCode) {
        this.auditCode = auditCode;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }
}
