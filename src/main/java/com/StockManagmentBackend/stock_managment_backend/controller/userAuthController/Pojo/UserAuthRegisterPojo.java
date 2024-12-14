package com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo;

import org.springframework.stereotype.Component;

public class UserAuthRegisterPojo {

    private Boolean success;

    private String message;

    public UserAuthRegisterPojo(Boolean success, String message) {
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

