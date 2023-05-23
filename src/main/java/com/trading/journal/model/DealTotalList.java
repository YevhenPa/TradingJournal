package com.trading.journal.model;

public class DealTotalList {

    private int totalDeals;

    private int totalWinDeals;

    private int totalWasteDeals;

    private double totalProfitDollars;

    private double totalProfitPercents;

    public int getTotalDeals() {
        return totalDeals;
    }

    public void setTotalDeals(int totalDeals) {
        this.totalDeals = totalDeals;
    }

    public int getTotalWinDeals() {
        return totalWinDeals;
    }

    public void setTotalWinDeals(int totalWinDeals) {
        this.totalWinDeals = totalWinDeals;
    }

    public int getTotalWasteDeals() {
        return totalWasteDeals;
    }

    public void setTotalWasteDeals(int totalWasteDeals) {
        this.totalWasteDeals = totalWasteDeals;
    }

    public double getTotalProfitDollars() {
        return totalProfitDollars;
    }

    public void setTotalProfitDollars(double totalProfitDollars) {
        this.totalProfitDollars = totalProfitDollars;
    }

    public double getTotalProfitPercents() {
        return totalProfitPercents;
    }

    public void setTotalProfitPercents(double totalProfitPercents) {
        this.totalProfitPercents = totalProfitPercents;
    }
}
