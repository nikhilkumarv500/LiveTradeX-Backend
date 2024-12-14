package com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo;

public class UserStocksGeneralResponsePojo {

    private Boolean success;

    private String message;

    public UserStocksGeneralResponsePojo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
