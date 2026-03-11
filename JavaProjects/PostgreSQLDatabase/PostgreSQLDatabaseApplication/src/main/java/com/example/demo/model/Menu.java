package com.example.demo.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "menuid")
    private String menuID;
    @Column(name = "menuname")
    private String menuName;
    @Column(name = "menupath")
    private String menuPath;
    @Column(name = "description")
    private String description;
    @Column(name = "ismenuadmin")
    private String isMenuAdmin;
    @Column(name = "isparentgrouping")
    private String isParentGrouping;
    @Column(name = "typ")
    private String menuType;
    @Column(name = "seq")
    private int seq;
    @Column(name = "seq_typ")
    private int seqType;
    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pk.menu")
    private List<MenuRole> menuRoles = new LinkedList<MenuRole>();
    public List<MenuRole> getMenuRoles() {
        return menuRoles;
    }
    public void setMenuRoles(List<MenuRole> menuRoles) {
        this.menuRoles = menuRoles;
    }
    public String getMenuID() {
        return menuID;
    }
    public String getMenuName() {
        return menuName;
    }
    public String getMenuPath() {
        return menuPath;
    }
    public String getIsMenuAdmin() {
        return isMenuAdmin;
    }
    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }
    public void setIsMenuAdmin(String isMenuAdmin) {
        this.isMenuAdmin = isMenuAdmin;
    }
    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public String getIsParentGrouping() {
        return isParentGrouping;
    }
    public void setIsParentGrouping(String isParentGrouping) {
        this.isParentGrouping = isParentGrouping;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public int getSeqType() {
        return seqType;
    }
    public void setSeqType(int seqType) {
        this.seqType = seqType;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public static String MENU_ID= "MENUID";
    public static String MENU_NAME = "MENUNAME";
    public static String MENU_PATH = "MENUPATH";
    public static String MENU_TYPE = "TYP";
    public static String SEQ = "SEQ";
    public static String SEQ_TYPE = "SEQ_TYP";
    public static String IS_MENUADMIN = "ISMENUADMIN";
    public static String IS_PARENTGROUPING = "ISPARENTGROUPING";
}
