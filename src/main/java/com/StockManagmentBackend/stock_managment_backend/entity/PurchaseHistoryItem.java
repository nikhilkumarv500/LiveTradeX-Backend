package com.StockManagmentBackend.stock_managment_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="purchase_history_table", schema = "public")
public class PurchaseHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="historyId")
    private Long historyId;

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

    @Column(name="soldPrice")
    private Long soldPrice;

    @Column(name="soldPercentage", precision = 5, scale = 2)
    private BigDecimal soldPercentage;

    public PurchaseHistoryItem() {
    }

    public PurchaseHistoryItem(Long historyId, Long purchaseId, String email, Long stockId, String stockName, Long noOfStocks, Long purchasedPrice, BigDecimal purchasedPercentage, Long soldPrice, BigDecimal soldPercentage) {
        this.historyId = historyId;
        this.purchaseId = purchaseId;
        this.email = email;
        this.stockId = stockId;
        this.stockName = stockName;
        this.noOfStocks = noOfStocks;
        this.purchasedPrice = purchasedPrice;
        this.purchasedPercentage = purchasedPercentage;
        this.soldPrice = soldPrice;
        this.soldPercentage = soldPercentage;
    }

    public BigDecimal getSoldPercentage() {
        return soldPercentage;
    }

    public void setSoldPercentage(BigDecimal soldPercentage) {
        this.soldPercentage = soldPercentage;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(Long noOfStocks) {
        this.noOfStocks = noOfStocks;
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

    public Long getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(Long soldPrice) {
        this.soldPrice = soldPrice;
    }
}
