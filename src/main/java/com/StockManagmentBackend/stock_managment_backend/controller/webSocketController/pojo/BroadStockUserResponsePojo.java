package com.StockManagmentBackend.stock_managment_backend.controller.webSocketController.pojo;

import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.ListAllStocksItemPojo;

import java.util.ArrayList;

public class BroadStockUserResponsePojo {
    private long userCount;

    private ArrayList<ListAllStocksItemPojo>  listAllStocks;

    public BroadStockUserResponsePojo(long userCount, ArrayList<ListAllStocksItemPojo> listAllStocks) {
        this.userCount = userCount;
        this.listAllStocks = listAllStocks;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public ArrayList<ListAllStocksItemPojo> getListAllStocks() {
        return listAllStocks;
    }

    public void setListAllStocks(ArrayList<ListAllStocksItemPojo> listAllStocks) {
        this.listAllStocks = listAllStocks;
    }
}
