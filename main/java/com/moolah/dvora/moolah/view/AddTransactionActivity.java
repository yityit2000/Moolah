package com.moolah.dvora.moolah.view;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moolah.dvora.moolah.data.Transaction;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.moolah.dvora.moolah.R;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private final static String LOG_TAG = AddTransactionActivity.class.toString();

    EditText titleEditText;
    TextView dateTextView;
    EditText amountEditText;
    RadioButton incomingRadioButton;
    RadioButton outgoingRadioButton;
    TextView saveButton;

    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        titleEditText = (EditText) findViewById(R.id.title_edittext);

        dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        // Obtained from external github library, basic Material Design datepicker that works
        // on APIs earlier than 21
        dateTextView = (TextView) findViewById(R.id.new_transaction_date);
        final Calendar calendar = Calendar.getInstance();
        dateTextView.setText(dateFormat.format(calendar.getTimeInMillis()));//buildDate(calendar.get(Calendar.YEAR), (calendar.get((Calendar.MONTH))), calendar.get(Calendar.DAY_OF_MONTH)));
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        AddTransactionActivity.this//,
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
                datePickerDialog.setLocale(Locale.getDefault());
            }
        });

        amountEditText = findViewById(R.id.amount_edittext);

        // Create a formatted hint with the currency of the device
        double hint = 453.25;
        final NumberFormat currency = NumberFormat.getCurrencyInstance();
        String formattedHint = currency.format(hint);
        amountEditText.setHint(formattedHint);

        amountEditText.addTextChangedListener(new MoneyTextWatcher(amountEditText));

        incomingRadioButton = findViewById(R.id.incoming_radiobutton);
        outgoingRadioButton = findViewById(R.id.outgoing_radiobutton);

        saveButton = findViewById(R.id.toolbar_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parsedAmount = amountEditText.getText().toString().replaceAll("[^0-9]","");
                String newDateString = dateTextView.getText().toString();
                long newDate = 1L;
                try{
                    newDate = dateFormat.parse(newDateString).getTime();
                }catch (ParseException exception){
                    Log.e(LOG_TAG,"Error parsing date");
                    exception.printStackTrace();
                }

                String newTitle = titleEditText.getText().toString();
                BigDecimal newAmount = BigDecimal.valueOf(Double.parseDouble(parsedAmount));
                if (outgoingRadioButton.isChecked()){
                    newAmount.negate();
                }
                Intent returnToMain = new Intent(view.getContext(),MainActivity.class);
                returnToMain.putExtra("newTransactionDate",newDate);
                returnToMain.putExtra("newTransactionTitle",newTitle);
                returnToMain.putExtra("newTransactionAmount",newAmount.toString());
                setResult(RESULT_OK,returnToMain);
                finish();
                //startActivityForResult(returnToMain, 5);

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        dateTextView.setText(dateFormat.format(newDate.getTime()));
    }

}
