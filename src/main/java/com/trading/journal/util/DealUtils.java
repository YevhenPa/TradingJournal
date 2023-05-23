package com.trading.journal.util;

import org.json.JSONException;
import org.json.JSONObject;
import com.trading.journal.model.Deal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DealUtils {

    final static String APP_URL = "https://openexchangerates.org/api/latest.json?app_id=";
    final static String APP_ID = "3184bcbe476e46d285da7b00ef5b3dde";
    final static String USDtoQuoteCurrencyExchangeRate = "&base=USD&symbols=";

    public static Deal profitDollarsCounter(Deal deal) throws JSONException, IOException {
        // TODO:
        deal.setContractSize(100000);

        String currency = deal.getSymbol();

        switch (currency) {
            case "Forex":
                deal.setContractSize(100_000);
                break;
            case "Crypto":
                deal.setContractSize(1);
                break;
            case "CFD":
                deal.setContractSize(1);
                break;
            case "Stock":
                deal.setContractSize(100_000);
                break;
        }

        // Contract: Lot * Contract Size
        double contract = deal.getLot() * deal.getContractSize();
        contract = Math.round(contract);

        deal.setContract(contract);

        // Calculating PROFIT/LOSS
        double profit = 0d;

        if (deal.getDealType().equalsIgnoreCase("LONG")) {
            profit = (deal.getContract() * deal.getClosePrice()) -
                    (deal.getContract() * deal.getOpenPrice());
        } else {
            profit = (deal.getContract() * deal.getOpenPrice() -
                    (deal.getContract() * deal.getClosePrice()));
        }

        //check base currency & exchange, if needed
        String quoteCurr =  deal.getSymbol().substring(3, 6);

        if(!quoteCurr.equalsIgnoreCase("USD")) {
            profit = (profit / convertToUSD(quoteCurr));
        }

        if(deal.getClosePrice() != 0) {
            profit = Double.parseDouble(String.format("%.2f", profit));
        } else {
            profit = 0;
        }

        deal.setProfitDollars(profit);
        return deal;
    }

    public static Deal marginDollarsCounter(Deal deal) throws JSONException, IOException {

        //count the margin
        if(deal.getLeverage() < 1) {
            deal.setLeverage(1);
            deal.setMargin(deal.getContract() / deal.getLeverage());
        } else {
            deal.setMargin(deal.getContract() / deal.getLeverage());
        }

        //check base currency & exchange, if needed
        double currentMargin = deal.getMargin();
        String baseCurr =  deal.getSymbol().substring(0, 3);

        if(!baseCurr.equalsIgnoreCase("USD")) {
            deal.setMargin(currentMargin / convertToUSD(baseCurr));
            deal.setNote("USD/" + baseCurr + " = " + convertToUSD(baseCurr));
        }

        //format to 2 numbers after point (.00<)
        double formatedMargin = Double.parseDouble(String.format("%.2f", deal.getMargin()));
        deal.setMargin(formatedMargin);
        return deal;
    }

    public static Deal profitPercentsCounter(Deal deal) {

        double profitPercents = deal.getProfitDollars() / deal.getMargin() * 100;

        profitPercents = Double.parseDouble(String.format("%.2f", profitPercents));
        deal.setProfitPercents(profitPercents);

        return deal;
    }

    public static Double convertToUSD(String currencyForConvert) throws IOException, JSONException {

        String urlStr = APP_URL + APP_ID + USDtoQuoteCurrencyExchangeRate + currencyForConvert;
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while((inputLine = in.readLine())!= null) {
            response.append(inputLine);
        }
        in.close();

        String jsonResponse = response.toString();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");

        double exchangeRate = ratesObject.getDouble(currencyForConvert);

        return exchangeRate;
    }

}
















