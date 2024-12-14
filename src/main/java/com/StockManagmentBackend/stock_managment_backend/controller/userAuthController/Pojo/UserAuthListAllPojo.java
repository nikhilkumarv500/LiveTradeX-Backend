package com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo;

import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserDetailsItem;

import java.util.List;

public class UserAuthListAllPojo {

    private UserAuthRegisterPojo loginStatus;

    private List<UserAuthItem> userAuthItemList;

    private List<UserDetailsItem> userDetailsItemList;

    public UserAuthListAllPojo(UserAuthRegisterPojo loginStatus, List<UserAuthItem> userAuthItemList, List<UserDetailsItem> userDetailsItemList) {
        this.loginStatus = loginStatus;
        this.userAuthItemList = userAuthItemList;
        this.userDetailsItemList = userDetailsItemList;
    }

    public UserAuthRegisterPojo getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(UserAuthRegisterPojo loginStatus) {
        this.loginStatus = loginStatus;
    }

    public List<UserAuthItem> getUserAuthItemList() {
        return userAuthItemList;
    }

    public void setUserAuthItemList(List<UserAuthItem> userAuthItemList) {
        this.userAuthItemList = userAuthItemList;
    }

    public List<UserDetailsItem> getUserDetailsItemList() {
        return userDetailsItemList;
    }

    public void setUserDetailsItemList(List<UserDetailsItem> userDetailsItemList) {
        this.userDetailsItemList = userDetailsItemList;
    }
}
