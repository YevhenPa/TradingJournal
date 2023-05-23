package com.trading.journal.util;

import org.json.JSONException;
import org.json.JSONObject;
import com.trading.journal.model.Deal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DealUtils {

    final static String APP_URL = "https://openexchangerates.org/api/latest.json?app_id=";
    final static String APP_ID = "3184bcbe476e46d285da7b00ef5b3dde";
    final static String USDtoQuoteCurrencyExchangeRate = "&base=USD&symbols=";

    public static Deal profitDollarsCounter(Deal deal) throws JSONException, IOException {

        deal.setContractSize(100000); // TODO: make with switch case ENUM
//        deal.setContract(deal.getLot() * deal.getContractSize());
        deal.setContract(250000); // TODO: check about counters of contracts

        double calc = (deal.getContract() * deal.getClosePrice()) -
                (deal.getContract() * deal.getOpenPrice());

        //check base currency & exchange, if needed
        String quoteCurr =  deal.getSymbol().substring(3, 6);

        if(!quoteCurr.equalsIgnoreCase("USD")) {
            calc = (calc / convertToUSD(quoteCurr));
            deal.setNote("USD/" + quoteCurr + " = " + convertToUSD(quoteCurr));
        }

        if(deal.getClosePrice() != 0) {
            calc = Double.parseDouble(String.format("%.2f", calc));
        } else {
            calc = 0;
        }

        deal.setProfitDollars(calc);
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

        double calc = deal.getProfitDollars() / deal.getMargin() * 100;

        calc = Double.parseDouble(String.format("%.2f", calc));
        deal.setProfitPercents(calc);

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
















