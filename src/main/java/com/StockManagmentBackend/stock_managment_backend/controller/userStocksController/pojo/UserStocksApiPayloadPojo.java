package com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo;

public class UserStocksApiPayloadPojo {

    private Long stockId;

    private String email;

    private Long noOfStocks;

    private Long purchaseId;

    private String password;

    public UserStocksApiPayloadPojo(Long stockId, String email, Long noOfStocks, Long purchaseId, String password) {
        this.stockId = stockId;
        this.email = email;
        this.noOfStocks = noOfStocks;
        this.purchaseId = purchaseId;
        this.password = password;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
