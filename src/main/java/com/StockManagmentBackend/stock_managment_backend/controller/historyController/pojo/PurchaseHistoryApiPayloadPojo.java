package com.StockManagmentBackend.stock_managment_backend.controller.historyController.pojo;

public class PurchaseHistoryApiPayloadPojo {

    private String email;

    private String password;

    private Long historyId;

    public PurchaseHistoryApiPayloadPojo(String email, String password, Long historyId) {
        this.email = email;
        this.password = password;
        this.historyId = historyId;
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

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
}
