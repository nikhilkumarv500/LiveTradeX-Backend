package com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo;

import com.StockManagmentBackend.stock_managment_backend.entity.PurchaseHistoryItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserDetailsItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserStocksItem;

import java.util.List;

public class UserAuthLoginPojo {

    private UserAuthRegisterPojo loginStatus;

    private UserDetailsItem userDetailsItem;

    private List<UserStocksItem> userStocksItemList;

    private List<PurchaseHistoryItem> userStocksHistoryList;

    private long userCount;

    public UserAuthLoginPojo(UserAuthRegisterPojo loginStatus, UserDetailsItem userDetailsItem, List<UserStocksItem> userStocksItemList, List<PurchaseHistoryItem> userStocksHistoryList, long userCount) {
        this.loginStatus = loginStatus;
        this.userDetailsItem = userDetailsItem;
        this.userStocksItemList = userStocksItemList;
        this.userStocksHistoryList = userStocksHistoryList;
        this.userCount = userCount;
    }

    public UserAuthLoginPojo() {
    }

    public UserAuthRegisterPojo getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(UserAuthRegisterPojo loginStatus) {
        this.loginStatus = loginStatus;
    }

    public UserDetailsItem getUserDetailsItem() {
        return userDetailsItem;
    }

    public void setUserDetailsItem(UserDetailsItem userDetailsItem) {
        this.userDetailsItem = userDetailsItem;
    }

    public List<UserStocksItem> getUserStocksItemList() {
        return userStocksItemList;
    }

    public void setUserStocksItemList(List<UserStocksItem> userStocksItemList) {
        this.userStocksItemList = userStocksItemList;
    }

    public List<PurchaseHistoryItem> getUserStocksHistoryList() {
        return userStocksHistoryList;
    }

    public void setUserStocksHistoryList(List<PurchaseHistoryItem> userStocksHistoryList) {
        this.userStocksHistoryList = userStocksHistoryList;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }
}
