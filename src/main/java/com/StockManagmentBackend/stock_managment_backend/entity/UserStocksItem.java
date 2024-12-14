package com.StockManagmentBackend.stock_managment_backend.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="user_stocks_table", schema = "public")
public class UserStocksItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="purchaseId")
    private Long purchaseId;

    @Column(name="email")
    private String email;

    @Column(name="stockId")
    private Long stockId;

    @Column(name = "stockName")
    private String stockName;

    @Column(name="noOfStocks")
    private Long noOfStocks;

    @Column(name="purchasedPrice")
    private Long purchasedPrice;

    @Column(name = "purchasedPercentage", precision = 5, scale = 2)
    private BigDecimal purchasedPercentage;

    public UserStocksItem() {
    }

    public UserStocksItem(Long purchaseId, String email, Long stockId, String stockName, Long noOfStocks, Long purchasedPrice, BigDecimal purchasedPercentage) {
        this.purchaseId = purchaseId;
        this.email = email;
        this.stockId = stockId;
        this.stockName = stockName;
        this.noOfStocks = noOfStocks;
        this.purchasedPrice = purchasedPrice;
        this.purchasedPercentage = purchasedPercentage;
    }

    public Long getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(Long purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public BigDecimal getPurchasedPercentage() {
        return purchasedPercentage;
    }

    public void setPurchasedPercentage(BigDecimal purchasedPercentage) {
        this.purchasedPercentage = purchasedPercentage;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(Long noOfStocks) {
        this.noOfStocks = noOfStocks;
    }


}
