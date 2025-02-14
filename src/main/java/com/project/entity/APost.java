package com.project.entity;

import java.sql.Timestamp;
import jakarta.persistence.*;

/**
 * This is a class that models an APost.
 *
 * You should NOT make any modifications to this class.
 */
@Entity
public class APost {
    /**
     * An id for this APost. You should use this as the Entity's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
 
    private Integer userId;

    private Integer tvPostId;

    private String rawMessage;

    private Timestamp date;

    private String ticker;
    
    private String orderType;

    private String side;

    private boolean isSuccessful;

    private Double avgEntryPrice;

    private Double qty;

    private String timeInForce;

    private String orderId;

    private String clientOrderId;
 
    public APost(){

    }
    /**
     * When posting a new APost, the id can be generated by the database. In that case, a constructor without
     * id is needed.
     * @param userId
     * @param tvPostId
     * @param rawMessage
     * @param date
     * 
     * 
     */
    public APost(Integer userId, Integer tvPostId, String rawMessage, Timestamp date, String ticker, String orderType, String side, 
                boolean isSuccessful, Double avgEntryPrice, Double qty, String timeInForce, String orderId, String clientOrderId){
        this.userId = userId;
        this.tvPostId = tvPostId;
        this.rawMessage = rawMessage;
        this.date = date;
        this.ticker = ticker;
        this.orderType = orderType;
        this.side = side;
        this.isSuccessful = isSuccessful;
        this.avgEntryPrice = avgEntryPrice;
        this.qty = qty;
        this.timeInForce = timeInForce;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
    }
    /**
     * Whem retrieving an APost from the database, all fields will be needed. In that case, a constructor with all
     * fields is needed.
     * @param id
     * @param userId
     * @param userID
     * @param rawMessage
     */
    public APost(Integer id, Integer userId, Integer tvPostId, String rawMessage, Timestamp date, String ticker, String orderType, String side, 
                boolean isSuccessful, Double avgEntryPrice, Double qty, String timeInForce, String orderId, String clientOrderId){
        this.id = id;
        this.userId = userId;
        this.tvPostId = tvPostId;
        this.rawMessage = rawMessage;
        this.date = date;
        this.ticker = ticker;
        this.orderType = orderType;
        this.side = side;
        this.isSuccessful = isSuccessful;
        this.avgEntryPrice = avgEntryPrice;
        this.qty = qty;
        this.timeInForce = timeInForce;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return id
     */
    public Integer getId() {
        return id;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTvPostId() {
        return tvPostId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setTvPostId(Integer tvPostId) {
        this.tvPostId = tvPostId;
    }

    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return rawMessage
     */
    public String getRawMessage() {
        return rawMessage;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param rawMessage
     */
    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public Timestamp getDate() {
        return date;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param date
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getOrderType() {
        return orderType;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSide() {
        return side;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setSide(String side) {
        this.side = side;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public Double getAvgEntryPrice() {
        return avgEntryPrice;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setAvgEntryPrice(Double avgEntryPrice) {
        this.avgEntryPrice = avgEntryPrice;
    }

    public Double getQty() {
        return qty;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getTimeInForce() {
        return timeInForce;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getOrderId() {
        return orderId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param id
     */
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    /**
     * Overriding the default equals() method adds functionality to tell when two objects are identical, allowing
     * Assert.assertEquals and List.contains to function.
     * @param o the other object.
     * @return true if o is equal to this object.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        APost other = (APost) obj;
        
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (tvPostId == null) {
            if (other.tvPostId != null)
                return false;
        } else if (!tvPostId.equals(other.tvPostId))
            return false;
        if (rawMessage == null) {
            if (other.rawMessage != null)
                return false;
        } else if (!rawMessage.equals(other.rawMessage))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (ticker == null) {
            if (other.ticker != null)
                return false;
        } else if (!ticker.equals(other.ticker))
            return false;
        if (orderType == null) {
            if (other.orderType != null)
                return false;
        } else if (!orderType.equals(other.orderType))
            return false;
        if (side == null) {
            if (other.side != null)
                return false;
        } else if (!side.equals(other.side))
            return false;
        if (isSuccessful != other.isSuccessful)
            return false;
        if (avgEntryPrice == null) {
            if (other.avgEntryPrice != null)
                return false;
        } else if (!avgEntryPrice.equals(other.avgEntryPrice))
            return false;
        if (qty == null) {
            if (other.qty != null)
                return false;
        } else if (!qty.equals(other.qty))
            return false;
        if (timeInForce == null) {
            if (other.timeInForce != null)
                return false;
        } else if (!timeInForce.equals(other.timeInForce))
            return false;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (clientOrderId == null) {
            if (other.clientOrderId != null)
                return false;
        } else if (!clientOrderId.equals(other.clientOrderId))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "APost{" +
                "id=" + id +
                ", userId=" + userId +
                ", tvPostId=" + tvPostId +
                ", rawMessage='" + rawMessage + '\'' +
                ", date=" + date +
                ", ticker='" + ticker + '\'' +
                ", orderType='" + orderType + '\'' +
                ", side='" + side + '\'' +
                ", isSuccessful=" + isSuccessful +
                ", avgEntryPrice=" + avgEntryPrice +
                ", qty=" + qty +
                ", timeInForce='" + timeInForce + '\'' +
                ", orderId='" + orderId + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}'; 
    }
}
