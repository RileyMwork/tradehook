package com.automated.trading.alpaca;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;
import net.jacobpeterson.alpaca.openapi.trader.ApiException;
import net.jacobpeterson.alpaca.openapi.trader.model.Order;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlpacaTests {

    @Autowired
    private PlaceOrder placeOrder;

    @Test 
    public void placeSimpleOrderCreatesAlpacaOrder() {
        String keyId = "PKMDBNJDRPH703STQP49";
        String secretKey = "PRkNe668qslhlM9LDNsd83WtLrhLhPpTJ2wO89w8";
        Order buyOrder = placeOrder.placeSimpleOrder("LTCUSD", "1", "buy",keyId,secretKey);
        String qtyToSell = placeOrder.getSellAmount(keyId, secretKey, "LTCUSD");
        assertFalse(buyOrder == null);
        Order sellOrder = placeOrder.placeSimpleOrder("LTCUSD", qtyToSell, "sell",keyId,secretKey);
        assertFalse(sellOrder == null);
    }

}
