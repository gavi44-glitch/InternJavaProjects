package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.persistence.Column;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "userid", unique = true, nullable = false)
    private String userID;
    @Column(name = "username")
    private String userName;
    @Column(name = "userpass")
    private String userPass;
    @Column(name = "emailaddress")
    private String emailAddress;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "createddate")
    private Date createdDate;
    @Column(name = "updatedby")
    private String updatedBy;
    @Column(name = "updateddate")
    private Date updatedDate;
    @Column(name = "lastactivity")
    private Date lastActivity;
    @Column(name = "userstatus")
    private String userStatus;
    @Column(name = "islogin")
    private String isLogin;
    @Column(name = "roleid")
    private String roleID;
//    @Id
//    private String id ;
//
//    @Column(unique = true, nullable = false)
//    private String userID;
//    private String userName;
//    private String userPass;
//    private String emailAddress;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;
//    private Date lastActivity;
//    private String userStatus;
//    private String isLogin;
//    private String roleID;

    public User(){

    }
    public User(String id, String userID, String userName, String userPass, String emailAddress, String createdBy, Date createdDate, String updatedBy, Date updatedDate, Date lastActivity, String userStatus, String isLogin, String roleID) {
        this.id = id;
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
        this.emailAddress = emailAddress;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.lastActivity = lastActivity;
        this.userStatus = userStatus;
        this.isLogin = isLogin;
        this.roleID = roleID;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
