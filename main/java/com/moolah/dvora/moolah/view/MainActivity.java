package com.moolah.dvora.moolah.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.moolah.dvora.moolah.R;
import com.moolah.dvora.moolah.data.FakeTransactionSource;
import com.moolah.dvora.moolah.data.Transaction;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;
    private RecyclerView.LayoutManager transactionLayoutManager;

    private FakeTransactionSource transactionSource;

    public ArrayList<Transaction> transactions;
    public BigDecimal startingAccountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        transactions = new ArrayList<>();
//        transactions.add(new Transaction("1/1/1","a",BigDecimal.valueOf(10)));
//        transactions.add(new Transaction("2/2/2","b",BigDecimal.valueOf(20)));
//        transactions.add(new Transaction("3/3/3","c",BigDecimal.valueOf(-5)));

        transactionSource = new FakeTransactionSource();
        transactions = transactionSource.getListOfTransactions();

        startingAccountBalance = transactionSource.getStartingAmount();

        // update the toolbar with the starting balance for the month
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setSubtitle("Starting balance: " + startingAccountBalance.toString());

        final FloatingActionButton addTransactionButton = findViewById(R.id.add_transaction_FAB);
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), AddTransactionActivity.class),5);
            }
        });

        transactionRecyclerView = (RecyclerView) findViewById(R.id.transactions_list);

        transactionLayoutManager = new LinearLayoutManager(this);
        transactionRecyclerView.setLayoutManager(transactionLayoutManager);

        transactionAdapter = new TransactionAdapter(transactions,startingAccountBalance);
        transactionRecyclerView.setAdapter(transactionAdapter);
    }

    private void addNewTransaction(long date, String title, BigDecimal amount){
        Transaction newTransaction = new Transaction(date, title, amount);
        //transactions.add(0, newTransaction);
        transactionSource.addTransaction(newTransaction);
        Log.v(MainActivity.class.toString(), "Transaction info: " + transactions.get(transactions.indexOf(newTransaction)));
        transactionAdapter.notifyItemInserted(transactions.indexOf(newTransaction));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK) {
            long date = data.getLongExtra("newTransactionDate", 0L);
            String title = data.getStringExtra("newTransactionTitle");
            String amountString = data.getStringExtra("newTransactionAmount");
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString)/100);
            addNewTransaction(date, title, amount);
            transactionAdapter.updateSubtotals();

        }
    }
}
