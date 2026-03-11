package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_role_menu_list")
@AssociationOverrides({ @AssociationOverride(name = "pk.menu", joinColumns = @JoinColumn(name = "menuid")),
        @AssociationOverride(name = "pk.role", joinColumns = @JoinColumn(name = "roleid")) })
public class MenuRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private MenuRoleId pk = new MenuRoleId();
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "createddate")
    private Date createdDate;
    @Column(name = "updatedby")
    private String updatedBy;
    @Column(name = "updateddate")
    private Date updatedDate;

    @EmbeddedId
    public MenuRoleId getPk() {
        return pk;
    }

    public void setPk(MenuRoleId pk) {
        this.pk = pk;
    }

    @Transient
    public Menu getMenu() {
        return getPk().getMenu();
    }

    public void setMenu(Menu menu) {
        getPk().setMenu(menu);
    }

    @Transient
    public Role getRole() {
        return getPk().getRole();
    }

    public void setRole(Role role) {
        getPk().setRole(role);
    }

    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MenuRole that = (MenuRole) o;
        if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
            return true;
        return true;
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

}
