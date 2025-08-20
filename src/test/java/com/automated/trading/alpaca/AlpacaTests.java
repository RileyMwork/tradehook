package com.automated.trading.alpaca;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import net.jacobpeterson.alpaca.openapi.trader.ApiException;
import net.jacobpeterson.alpaca.openapi.trader.model.Order;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlpacaTests {

    @Autowired
    private AuthInfo authInfo;

    @Autowired
    private PlaceOrder placeOrder;

    @Test 
    public void getAccountDoesNotReturnNull () {
        assertFalse(authInfo.getAccount() == null);
    }

    @Test 
    public void placeSimpleOrderCreatesAlpacaOrder() {
        try {
            Order buyOrder = placeOrder.placeSimpleOrder("LTCUSD", "1", "buy");
            String qtyToSell = authInfo.alpacaAPI.trader().positions().getOpenPosition("LTCUSD").getQty();
            assertFalse(buyOrder == null);
            Order sellOrder = placeOrder.placeSimpleOrder("LTCUSD", qtyToSell, "sell");
            assertFalse(sellOrder == null);
        } catch (ApiException e) {
            System.out.println(e.getMessage());
        }
    }

}
