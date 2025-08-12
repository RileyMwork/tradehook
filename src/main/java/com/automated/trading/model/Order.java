package com.automated.trading.model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private String ticker;

    private Timestamp createdAt;

    private Timestamp filledAt;

    private String filledQty;

    private String filledAvgPrice;

    private String orderType;

    private String side;

    private String commission;

    public Order() {
    }

    public Order(Integer userId, String ticker, Timestamp createdAt, Timestamp filledAt, String filledQty, String filledAvgPrice,
                String orderType, String side, String commission) {
        this.userId = userId;
        this.ticker = ticker;
        this.createdAt = createdAt;
        this.filledAt = filledAt;
        this.filledQty = filledQty;
        this.filledAvgPrice = filledAvgPrice;
        this.orderType = orderType;
        this.side = side;
        this.commission = commission;
    }

    public Order(Integer id, Integer userId, String ticker, Timestamp createdAt, Timestamp filledAt, String filledQty, String filledAvgPrice,
                String orderType, String side, String commission) {
        this.id = id;
        this.userId = userId;
        this.ticker = ticker;
        this.createdAt = createdAt;
        this.filledAt = filledAt;
        this.filledQty = filledQty;
        this.filledAvgPrice = filledAvgPrice;
        this.orderType = orderType;
        this.side = side;
        this.commission = commission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getFilledAt() {
        return filledAt;
    }

    public void setFilledAt(Timestamp filledAt) {
        this.filledAt = filledAt;
    }

    public String getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(String filledQty) {
        this.filledQty = filledQty;
    }

    public String getFilledAvgPrice() {
        return filledAvgPrice;
    }

    public void setFilledAvgPrice(String filledAvgPrice) {
        this.filledAvgPrice = filledAvgPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order other = (Order) obj;

        if (userId != other.userId) return false;
        if (ticker != null ? !ticker.equals(other.ticker) : other.ticker != null) return false;
        if (id != null ? !id.equals(other.id) : other.id != null) return false;
        if (createdAt != null ? !createdAt.equals(other.createdAt) : other.createdAt != null) return false;
        if (filledAt != null ? !filledAt.equals(other.filledAt) : other.filledAt != null) return false;
        if (filledQty != null ? !filledQty.equals(other.filledQty) : other.filledQty != null) return false;
        if (filledAvgPrice != null ? !filledAvgPrice.equals(other.filledAvgPrice) : other.filledAvgPrice != null)
            return false;
        if (orderType != null ? !orderType.equals(other.orderType) : other.orderType != null) return false;
        if (side != null ? !side.equals(other.side) : other.side != null) return false;
        return commission != null ? commission.equals(other.commission) : other.commission == null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticker='" + ticker + '\'' +
                ", createdAt=" + createdAt +
                ", filledAt=" + filledAt +
                ", filledQty='" + filledQty + '\'' +
                ", filledAvgPrice='" + filledAvgPrice + '\'' +
                ", orderType='" + orderType + '\'' +
                ", side='" + side + '\'' +
                ", commission='" + commission + '\'' +
                '}';
    }
}
