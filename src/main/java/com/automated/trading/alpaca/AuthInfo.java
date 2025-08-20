package com.automated.trading.alpaca;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;
import net.jacobpeterson.alpaca.openapi.trader.ApiException;
import net.jacobpeterson.alpaca.openapi.trader.model.Account;

public class AuthInfo {
    public final String keyID = "PK596J0QXOWSFQU2C8VQ";
    public final String secretKey = "UDKrFB2yf88tUSlBXvghnknRMYyWNwgSTVWY5kgG";
    public final TraderAPIEndpointType endpointType = TraderAPIEndpointType.PAPER; // or 'LIVE'
    public final MarketDataWebsocketSourceType sourceType = MarketDataWebsocketSourceType.IEX; // or 'SIP'
    public final AlpacaAPI alpacaAPI = new AlpacaAPI(keyID, secretKey, endpointType, sourceType);


    public Account getAccount() {
        try {
            return alpacaAPI.trader().accounts().getAccount();
        } catch (ApiException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}