package com.moolah.dvora.moolah.data;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Test Double
 * (Specifically a "Fake")
 * Created by dvora on 3/19/2018.
 */

public class FakeTransactionSource implements TransactionSourceInterface{

    // Defines how many items we want to list in our Transaction list
    private static final int SIZE_OF_COLLECTION = 12;

    // Contains the Strings for the dates
    private ArrayList<Long> dates;

    private ArrayList<String> titles;

    private ArrayList<BigDecimal> amounts;

    private final BigDecimal startingAmount;

    private ArrayList<Transaction> listOfTransactions = new ArrayList<>();

    public FakeTransactionSource() {
        dates = new ArrayList<>();
        dates.add((long)1519902332);
        dates.add((long)1519902332);
        dates.add((long)1520075132);
        dates.add((long)1520679932);
        dates.add((long)1520849132);
        dates.add((long)1521108332);
        dates.add((long)1521281132);
        dates.add((long)1521972332);
        dates.add((long)1522490732);

        titles = new ArrayList<>();
        titles.add("Paycheck");
        titles.add("Credit Card Bill: CSR");
        titles.add("Credit Card Bill: CSP");
        titles.add("Car Bill");
        titles.add("Cash Withdrawal");
        titles.add("Venmo: Splitting Aiello's Pizza");
        titles.add("Credit Card Bill: BoA");
        titles.add("Rent");
        titles.add("Transfer to Savings");

        amounts = new ArrayList<>();
        amounts.add(BigDecimal.valueOf(4000));
        amounts.add(BigDecimal.valueOf(-800.0));
        amounts.add(BigDecimal.valueOf(-200.0));
        amounts.add(BigDecimal.valueOf(-238.0));
        amounts.add(BigDecimal.valueOf(-40.0));
        amounts.add(BigDecimal.valueOf(-12.32));
        amounts.add(BigDecimal.valueOf(-145.0));
        amounts.add(BigDecimal.valueOf(-550.0));
        amounts.add(BigDecimal.valueOf(-2000.0));

        startingAmount = BigDecimal.valueOf(500);
    }

    // We use List because the ArrayList is a type of list (List is an interface), and the rest of the
    // app doesn't need to know that we're using an ArrayList. INTERFACES!
    @Override
    public ArrayList<Transaction> getListOfTransactions() {

        for (int i = 0; i < titles.size(); i++){
            listOfTransactions.add(new Transaction(
                    dates.get(i) * 1000,
                    titles.get(i),
                    amounts.get(i)
            ));
        }
        sortTransactions();
        return listOfTransactions;
    }

    public void addTransaction(Transaction transaction){
        listOfTransactions.add(transaction);
        sortTransactions();
    }

    private class dateComparator implements Comparator<Transaction>{

        @Override
        public int compare(Transaction transaction1, Transaction transaction2) {
            int compare = 1;
            if (transaction1.getDateInMilliseconds() < transaction2.getDateInMilliseconds()){
                compare = -1;
            } else if (transaction1.getDateInMilliseconds() == transaction2.getDateInMilliseconds()){
                compare = 0;
            }
            return compare;
        }
    }

    public void sortTransactions(){
        Collections.sort(listOfTransactions, new dateComparator());
    }

    @Override
    public BigDecimal getStartingAmount() {
        return startingAmount;
    }

    @Override
    public void addTransaction() {

    }

    @Override
    public void removeTransaction() {

    }

}
