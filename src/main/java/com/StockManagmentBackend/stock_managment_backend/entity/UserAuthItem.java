package com.StockManagmentBackend.stock_managment_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_auth_table", schema = "public")
public class UserAuthItem {


    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    private String name;

    public UserAuthItem() {
    }

    public UserAuthItem(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthItem{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
