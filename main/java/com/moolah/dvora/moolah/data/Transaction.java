package com.moolah.dvora.moolah.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (@link Transaction) The Transaction class stores information on a specific transaction, or movement of money into or
 * out of an account they have, that the user plans on making. Stores the dateInMilliseconds as a long so that a
 * group of transactions can easily be sorted by dateInMilliseconds but also parsed to month, day, and year with the
 * Calendar class. It stores the title as a String variable, which is a short description of what
 * the transaction is for. The amount of the transaction is stored as a BigDecimal, which can be made
 * either positive or negative based on helper methods makePositive() and makeNegative
 *
 * Created by dvora on 3/16/2018.
 */

public class Transaction {

    // dateInMilliseconds represents the dateInMilliseconds that the user plans on making this transaction
    private long dateInMilliseconds;
    // title represents a short description of the transaction
    private String title;
    // amount represents the amount the transaction will be for
    private BigDecimal amount;

    private Date date;


    public Transaction(long dateInMilliseconds, String title, BigDecimal amount){
        this.dateInMilliseconds = dateInMilliseconds;
        this.title = title;
        this.amount = amount;
        date = new Date(this.dateInMilliseconds);
    }

    public Transaction(Date date, String title, BigDecimal amount){
        this.date = date;
        this.title = title;
        this.amount = amount;
        dateInMilliseconds = this.date.getTime();
    }

    public String getSignOfAmount(){
        String sign = "";
        if (amount.doubleValue() < 0){
            sign = "-";
        }
        return sign;
    }

    public void setToNegative(){
        amount = amount.negate();
    }

    public void setToPositive(){
        if (amount.doubleValue() < 0){
            amount = amount.negate();
        }
    }

    // getters
    public long getDateInMilliseconds() {
        return dateInMilliseconds;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    // Returns a short summary of a Transaction for the purpose of logging
    public String toString(){
        String transactionString = dateInMilliseconds + ", " + date.toString() + ", " + title + ", " + amount.toString();
        return transactionString;
    }
}
