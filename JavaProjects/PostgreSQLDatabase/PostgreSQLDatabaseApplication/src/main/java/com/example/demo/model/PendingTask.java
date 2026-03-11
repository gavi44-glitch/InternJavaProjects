package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pending_task")
public class PendingTask implements Serializable {

    private static final long serialVersionUID = 1449678107573747911L;

    @Id
    @Column(name = "cd")
    private String code;

    @Column(name = "srvc_typ")
    private String serviceType;

    @Column(name = "ref_no")
    private String referenceNo;

    @Column(name = "info_1")
    private String info1;

    @Column(name = "info_2")
    private String info2;

    @Column(name = "info_3")
    private String info3;

    @Column(name = "info_4")
    private String info4;

    @Column(name = "info_5")
    private String info5;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_dt")
    private Timestamp createdDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_dt")
    private Timestamp approvedDate;

    @Column(name = "wf_sts")
    private String workflowStatus;

    @Column(name = "action_info")
    private String actionInfo;

    @Column(name = "action_url")
    private String actionURL;

    @Column(name = "rejected_by")
    private String rejectedBy;

    @Column(name = "rejected_dt")
    private Timestamp rejectedDt;

    @Column(name = "old_data")
    private String oldData;

    @Column(name = "new_data")
    private String newData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Timestamp approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public String getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(String actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Timestamp getRejectedDt() {
        return rejectedDt;
    }

    public void setRejectedDt(Timestamp rejectedDt) {
        this.rejectedDt = rejectedDt;
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
