package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "roleid")
    private String roleID;
    @Column(name = "rolename")
    private String roleName;
    @Column(name="description")
    private String description;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "createddate")
    private Timestamp createdDate;
    @Column(name = "updatedby")
    private String updatedBy;
    @Column(name = "updateddate")
    private Timestamp updatedDate;

    public String getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }



}
