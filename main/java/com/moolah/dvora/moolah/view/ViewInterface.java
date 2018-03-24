package com.moolah.dvora.moolah.view;

import com.moolah.dvora.moolah.data.Transaction;

import java.util.List;

/**
 * Created by dvora on 3/19/2018.
 */

public interface ViewInterface {

    void startTransactionDetailActivity(String date, String title, double amount);

    void setUpAdapterAndView(List<Transaction> listOfTransactions);

}
