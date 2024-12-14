package com.StockManagmentBackend.stock_managment_backend.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="stocks_table", schema = "public")
public class StocksItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "originalPrice")
    private Long originalPrice;

    @Column(name = "lowestPrice")
    private Long lowestPrice;

    @Column(name = "highestPrice")
    private Long highestPrice;

    @Column(name="curPrice")
    private Long curPrice;

    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "remQuantity")
    private Long remQuantity;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "stocks_prev_percentages",
            joinColumns = @JoinColumn(name = "stocks_item_id")
    )
    @Column(name = "prev_percentage", precision = 5, scale = 2)
    private List<BigDecimal> prevPercentages;

    @Column(name = "initial_quantity")
    private Long initialQuantity;

    public StocksItem() {
    }

    public StocksItem(Long id, String name, Long originalPrice, Long lowestPrice, Long highestPrice, Long curPrice, BigDecimal percentage, Long remQuantity, List<BigDecimal> prevPercentages, Long initialQuantity) {
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

    @Override
    public String toString() {
        return "StocksItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalPrice=" + originalPrice +
                ", lowestPrice=" + lowestPrice +
                ", highestPrice=" + highestPrice +
                ", curPrice=" + curPrice +
                ", percentage=" + percentage +
                ", remQuantity=" + remQuantity +
                ", prevPercentages=" + prevPercentages +
                ", initialQuantity=" + initialQuantity +
                '}';
    }
}
