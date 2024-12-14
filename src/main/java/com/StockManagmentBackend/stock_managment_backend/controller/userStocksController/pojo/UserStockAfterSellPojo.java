package com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo;

import com.StockManagmentBackend.stock_managment_backend.entity.UserDetailsItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserStocksItem;

import java.util.List;

public class UserStockAfterSellPojo {

    private UserDetailsItem userDetailsItem;

    private List<UserStocksItem> userStocksItemList;

    public UserStockAfterSellPojo(UserDetailsItem userDetailsItem, List<UserStocksItem> userStocksItemList) {
        this.userDetailsItem = userDetailsItem;
        this.userStocksItemList = userStocksItemList;
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
}
