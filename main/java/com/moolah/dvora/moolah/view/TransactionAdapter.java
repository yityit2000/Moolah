package com.moolah.dvora.moolah.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.moolah.dvora.moolah.R;
import com.moolah.dvora.moolah.data.Transaction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dvora on 3/17/2018.
 */

public class TransactionAdapter extends RecyclerView.Adapter {

    private final static String LOG_TAG = TransactionAdapter.class.toString();

    private ArrayList<Transaction> transactions;
    private ArrayList<BigDecimal> accountSubtotals;

    private BigDecimal startingBalance;

    private NumberFormat format;
    private DateFormat dateFormat;

    private int lastPosition = -1;

    public TransactionAdapter(ArrayList<Transaction> transactions, BigDecimal startingBalance){
        this.transactions = transactions;
        this.startingBalance = startingBalance;

        accountSubtotals = new ArrayList<>();
        format = NumberFormat.getCurrencyInstance();
        dateFormat = DateFormat.getDateInstance();

        for (int i = 0; i < this.transactions.size(); i++){
            Log.v(LOG_TAG, this.transactions.get(i).toString());
        }

        updateSubtotals();


    }

    public void updateSubtotals() {
        BigDecimal subtotalHoldValue = startingBalance;
        accountSubtotals.clear();
        for (int i = 0; i < transactions.size(); i++){
            subtotalHoldValue = subtotalHoldValue.add(transactions.get(i).getAmount());
            accountSubtotals.add(subtotalHoldValue);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transactionName;
        TextView transactionDate;
        TextView transactionAmount;
        TextView accountSubtotal;

        Transaction transaction;

        public ViewHolder(View transactionLayout){
            super(transactionLayout);
            transactionName = (TextView) transactionLayout.findViewById(R.id.transaction_name);
            transactionDate = (TextView) transactionLayout.findViewById(R.id.transaction_date);
            transactionAmount = (TextView) transactionLayout.findViewById(R.id.transaction_amount);
            accountSubtotal = (TextView) transactionLayout.findViewById(R.id.account_subtotal);
        }

        // This method sets a transaction to this ViewHolder so it can know the data associated with the views
        public void setTransaction(Transaction transaction, int position){
            this.transaction = transaction;
            transactionName.setText(transaction.getTitle());
            transactionDate.setText(dateFormat.format(transaction.getDate()));
            transactionAmount.setText(format.format(transaction.getAmount().doubleValue()));
            accountSubtotal.setText(format.format(accountSubtotals.get(position).doubleValue()));

            //any onClickListeners will go here as well!

        }
    }


    // This method will inflate the layout laid out in transaction_list_item, attach it to a ViewHolder,
    // which is what is used in RecyclerViews. Suuuuuuper fancy. This method is called by the layout manager
    // also required by a RecyclerView. In it it needs to construct a ViewHolder object, so the input arg
    // in the constructor for the class above needs to match the type of view in transaction_list_item. In
    // this case, that's a CardView
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView transactionLayout = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_list_item, parent, false);

        ViewHolder transactionViewHolder = new ViewHolder(transactionLayout);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // fetch the data at this position
        ViewHolder transactionViewHolder = (ViewHolder) holder;
        transactionViewHolder.setTransaction(transactions.get(position), position);

        // replace the elements of the view with the data from currentTransaction
        setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
