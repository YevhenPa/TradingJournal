package com.trading.journal.model;

import com.trading.journal.security.model.User;
import jakarta.persistence.*;
import java.util.List;

//Main page objects
@Entity
@Table(name = "deals")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int dealNumber;

    private String openTime;

    private String symbol;

    private String dealType;

    private int leverage;

    private double openPrice;

    private double lot;

    private double margin;

    private double stopLoss;

    private double takeProfit;

    private double profitDollars;

    private double profitPercents;

    private double commissions;

    private double closePrice;

    private String closeTime;

    private double contract;

    private double contractSize;

    //TODO try it
    //@Column(length = 100)
    private String note;

    // For ScreenShots (Images)

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deal")
    private List<Image> images;

    ////////////////////////////////
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.MERGE, CascadeType.REFRESH} )
    @JoinColumn(name = "user_id")
    private User user;

    ////////////////////////////////
    public Deal() {
    }

    public Deal(String openTime, String symbol, String dealType, int leverage, double openPrice, double lot, double stopLoss, double takeProfit, double commissions, double closePrice, String closeTime) {
        this.openTime = openTime;
        this.symbol = symbol;
        this.dealType = dealType;
        this.leverage = leverage;
        this.openPrice = openPrice;
        this.lot = lot;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.commissions = commissions;
        this.closePrice = closePrice;
        this.closeTime = closeTime;
    }

    public Deal(int id, String openTime, String symbol, String dealType, int leverage, double openPrice, double lot, double margin, double stopLoss, double takeProfit, double profitDollars, double profitPercents, double commissions, double closePrice, String closeTime, double contract, double contractSize, String note) {
        this.id = id;
        this.openTime = openTime;
        this.symbol = symbol;
        this.dealType = dealType;
        this.leverage = leverage;
        this.openPrice = openPrice;
        this.lot = lot;
        this.margin = margin;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.profitDollars = profitDollars;
        this.profitPercents = profitPercents;
        this.commissions = commissions;
        this.closePrice = closePrice;
        this.closeTime = closeTime;
        this.contract = contract;
        this.contractSize = contractSize;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getProfitDollars() {
        return profitDollars;
    }

    public void setProfitDollars(double profitDollars) {
        this.profitDollars = profitDollars;
    }

    public double getProfitPercents() {
        return profitPercents;
    }

    public void setProfitPercents(double profitPercents) {
        this.profitPercents = profitPercents;
    }

    public double getCommissions() {
        return commissions;
    }

    public void setCommissions(double commissions) {
        this.commissions = commissions;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public double getContract() {
        return contract;
    }

    public void setContract(double contract) {
        this.contract = contract;
    }

    public double getContractSize() {
        return contractSize;
    }

    public void setContractSize(double contractSize) {
        this.contractSize = contractSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(int dealNumber) {
        this.dealNumber = dealNumber;
    }

//    public User getUser() {
//        return user;
//    }
//
    public void setUser(User user) {
        this.user = user;
    }

}
