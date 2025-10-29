package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
@PrimaryKeyJoinColumn(name = "AdminID") 
public class Admin extends User {

    @Column(name = "Admin_Privileges")
    private String adminPrivileges;

    public Admin() {
    }

    public String getAdminPrivileges() {
        return adminPrivileges;
    }

    public void setAdminPrivileges(String adminPrivileges) {
        this.adminPrivileges = adminPrivileges;
    }

}