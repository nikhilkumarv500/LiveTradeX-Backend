package com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo;

public class StocksGeneralResponsePojo {

    private Boolean success;

    private String message;

    public StocksGeneralResponsePojo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
