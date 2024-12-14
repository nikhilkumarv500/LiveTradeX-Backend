package com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo;

public class UserAuthAddMoneyPayloadPojo {

    private String email;

    private Long amount;

    private String password;

    private String couponCode;

    public UserAuthAddMoneyPayloadPojo(String email, Long amount, String password, String couponCode) {
        this.email = email;
        this.amount = amount;
        this.password = password;
        this.couponCode = couponCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
