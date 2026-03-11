package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MenuRoleId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Menu menu;
    private Role role;
    private String isActive;
    @ManyToOne(cascade = CascadeType.ALL)
    public Menu getMenu() {
        return menu;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    public Role getRole() {
        return role;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
