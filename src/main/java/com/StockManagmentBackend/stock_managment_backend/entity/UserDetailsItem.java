package com.StockManagmentBackend.stock_managment_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_details_table", schema = "public")
public class UserDetailsItem {

    @Id
    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="accountBalance")
    private Long accountBalance;

    @Column(name = "investedAmount")
    private Long investedAmount;

    @Column(name = "profitMade")
    private Long profitMade;

    public UserDetailsItem() {
    }

    public UserDetailsItem(String email, String name, Long accountBalance, Long investedAmount, Long profitMade) {
        this.email = email;
        this.name = name;
        this.accountBalance = accountBalance;
        this.investedAmount = investedAmount;
        this.profitMade = profitMade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Long getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(Long investedAmount) {
        this.investedAmount = investedAmount;
    }

    public Long getProfitMade() {
        return profitMade;
    }

    public void setProfitMade(Long profitMade) {
        this.profitMade = profitMade;
    }

    @Override
    public String toString() {
        return "UserDetailsItem{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", accountBalance=" + accountBalance +
                ", investedAmount=" + investedAmount +
                ", profitMade=" + profitMade +
                '}';
    }
}
