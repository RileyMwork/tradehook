package com.automated.trading.alpaca;

import org.springframework.stereotype.Component;

import net.jacobpeterson.alpaca.openapi.trader.ApiException;
import net.jacobpeterson.alpaca.openapi.trader.model.Order;
import net.jacobpeterson.alpaca.openapi.trader.model.TimeInForce;
import net.jacobpeterson.alpaca.openapi.trader.model.PostOrderRequest;
import net.jacobpeterson.alpaca.openapi.trader.model.OrderSide;
import net.jacobpeterson.alpaca.openapi.trader.model.OrderType;

@Component
public class PlaceOrder extends AuthInfo{

    public PlaceOrder() {
        super();
    }
    
    public Order placeSimpleOrder(String ticker,String qty, String side) {  
        try {
            OrderSide orderSide = null;
            if (side.equals("buy")) {
                orderSide = OrderSide.BUY;
            } else {
                orderSide = OrderSide.SELL;
            }
            Order sentOrder = alpacaAPI.trader().orders().postOrder(new PostOrderRequest()
                .symbol(ticker)
                .qty(qty)
                .side(orderSide)
                .type(OrderType.MARKET)
                .timeInForce(TimeInForce.GTC));
            return sentOrder;

        } catch (ApiException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
