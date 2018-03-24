package com.moolah.dvora.moolah.data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 1. This is a contract between Classes that dictate how they can talk to each other
 * without giving implementation details.
 * Created by dvora on 3/19/2018.
 */

public interface TransactionSourceInterface {

    List<Transaction> getListOfTransactions();

    BigDecimal getStartingAmount();

    void addTransaction();

    void removeTransaction();
}
