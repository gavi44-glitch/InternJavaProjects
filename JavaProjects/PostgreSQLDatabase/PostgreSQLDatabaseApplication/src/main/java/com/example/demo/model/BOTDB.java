package com.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bo_tdb")
public class BOTDB {
    @Id
    @Column(name = "datasource_code")
    private String dataSourceCode;
    @Column(name = "datasource_name")
    private String dataSourceName;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_dt")
    private Timestamp createdDt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_dt")
    private Timestamp updatedDt;
    @Column(name = "approval_by")
    private String approvalBy;
    @Column(name = "approval_dt")
    private Timestamp approvalDt;
    @Column(name = "status")
    private String status;
    @Column(name = "description")
    private String description;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public Timestamp getApprovalDt() {
        return approvalDt;
    }

    public void setApprovalDt(Timestamp approvalDt) {
        this.approvalDt = approvalDt;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
