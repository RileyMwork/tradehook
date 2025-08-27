package com.automated.trading.model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private String orderId;

    private String ticker;

    private String qty;  

    private Timestamp createdAt;

    private String side;

    public Order() {
    }

    public Order(String ticker, String qty, String side) {
        this.ticker = ticker;
        this.qty = qty;
        this.side = side;
    }

    public Order(String orderId, String ticker, String qty, Timestamp createdAt, String side) {
        this.orderId = orderId;
        this.ticker = ticker;
        this.qty = qty;
        this.createdAt = createdAt;
        this.side = side;
    }

    public Order(Integer userId, String orderId, String ticker, String qty, Timestamp createdAt, String side) {
        this.userId = userId;
        this.orderId = orderId;
        this.ticker = ticker;
        this.qty = qty;
        this.createdAt = createdAt;
        this.side = side;
    }

    public Order(Integer id, Integer userId, String orderId, String ticker, String qty, Timestamp createdAt, String side) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.ticker = ticker;
        this.qty = qty;
        this.createdAt = createdAt;
        this.side = side;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getQty() {  // Getter for qty
        return qty;
    }

    public void setQty(String qty) {  // Setter for qty
        this.qty = qty;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    // Overriding equals method to include qty
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order other = (Order) obj;

        if (userId != null ? !userId.equals(other.userId) : other.userId != null) return false;
        if (id != null ? !id.equals(other.id) : other.id != null) return false;
        if (orderId != null ? !orderId.equals(other.orderId) : other.orderId != null) return false;
        if (ticker != null ? !ticker.equals(other.ticker) : other.ticker != null) return false;
        if (qty != null ? !qty.equals(other.qty) : other.qty != null) return false;  // Include qty in equals check
        if (createdAt != null ? !createdAt.equals(other.createdAt) : other.createdAt != null) return false;
        if (side != null ? !side.equals(other.side) : other.side != null) return false;

        return true;
    }

    // Overriding toString method to include qty
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId='" + orderId + '\'' +
                ", ticker='" + ticker + '\'' +
                ", qty='" + qty + '\'' +  // Include qty in the toString output
                ", createdAt=" + createdAt +
                ", side='" + side + '\'' +
                '}';
    }
}
