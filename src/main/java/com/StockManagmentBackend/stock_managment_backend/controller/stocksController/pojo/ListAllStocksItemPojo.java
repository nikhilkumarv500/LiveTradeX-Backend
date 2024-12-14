package com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.List;

public class ListAllStocksItemPojo {

    private Long id;

    private String name;

    private Long originalPrice;

    private Long lowestPrice;

    private Long highestPrice;

    private Long curPrice;

    private BigDecimal percentage;

    private Long remQuantity;

    private List<BigDecimal> prevPercentages;

    private Long initialQuantity;

    private Long purchasedQuantity;

    private Long totalInvestedUserCnt;

    public ListAllStocksItemPojo()
    {}


    public ListAllStocksItemPojo(Long id, String name, Long originalPrice, Long lowestPrice, Long highestPrice, Long curPrice, BigDecimal percentage, Long remQuantity, List<BigDecimal> prevPercentages, Long initialQuantity, Long purchasedQuantity, Long totalInvestedUserCnt) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.curPrice = curPrice;
        this.percentage = percentage;
        this.remQuantity = remQuantity;
        this.prevPercentages = prevPercentages;
        this.initialQuantity = initialQuantity;
        this.purchasedQuantity = purchasedQuantity;
        this.totalInvestedUserCnt = totalInvestedUserCnt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Long lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Long getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Long highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Long getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Long curPrice) {
        this.curPrice = curPrice;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Long getRemQuantity() {
        return remQuantity;
    }

    public void setRemQuantity(Long remQuantity) {
        this.remQuantity = remQuantity;
    }

    public List<BigDecimal> getPrevPercentages() {
        return prevPercentages;
    }

    public void setPrevPercentages(List<BigDecimal> prevPercentages) {
        this.prevPercentages = prevPercentages;
    }

    public Long getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(Long initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public Long getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(Long purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public Long getTotalInvestedUserCnt() {
        return totalInvestedUserCnt;
    }

    public void setTotalInvestedUserCnt(Long totalInvestedUserCnt) {
        this.totalInvestedUserCnt = totalInvestedUserCnt;
    }
}
