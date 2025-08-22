package com.automated.trading.alpaca;

import org.springframework.stereotype.Component;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;
import net.jacobpeterson.alpaca.openapi.trader.ApiException;
import net.jacobpeterson.alpaca.openapi.trader.model.Order;
import net.jacobpeterson.alpaca.openapi.trader.model.TimeInForce;
import net.jacobpeterson.alpaca.openapi.trader.model.PostOrderRequest;
import net.jacobpeterson.alpaca.openapi.trader.model.OrderSide;
import net.jacobpeterson.alpaca.openapi.trader.model.OrderType;

@Component
public class PlaceOrder{
    
    public Order placeSimpleOrder(String ticker,String qty, String side, String keyId, String secretKey) {  
        try {
            OrderSide orderSide = null;
            if (side.equals("buy")) {
                orderSide = OrderSide.BUY;
            } else {
                orderSide = OrderSide.SELL;
            }
            AlpacaAPI alpacaApi = new AlpacaAPI(keyId,secretKey,TraderAPIEndpointType.PAPER, MarketDataWebsocketSourceType.IEX);
            Order sentOrder = alpacaApi.trader().orders().postOrder(new PostOrderRequest()
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

    public String getSellAmount(String keyId, String secretKey,String ticker) {
        AlpacaAPI alpacaAPI = new AlpacaAPI(keyId,secretKey,TraderAPIEndpointType.PAPER,MarketDataWebsocketSourceType.IEX);
        try {
            String qty = alpacaAPI.trader().positions().getOpenPosition(ticker).getQty();
            return qty;
        } catch (ApiException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
