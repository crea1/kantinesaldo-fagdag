package com.kwc.kantinesaldo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * This is the main activity showing the balance etc.
 *
 * @author Marius Kristensen
 */
public class SaldoActivity extends Activity {

    private static final String TAG = "kantinesaldo";
    private static final String CARDNUMBER = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView saldoTekst = (TextView) findViewById(R.id.saldotekst);
        final Button theButton = (Button) findViewById(R.id.theButton);
        final Button saveButton = (Button) findViewById(R.id.saveButton);
        final EditText cardNumberText = (EditText) findViewById(R.id.cardNumber);

        SharedPreferences myPref = getSharedPreferences("myPref", MODE_PRIVATE);
        final SharedPreferences.Editor prefSave = myPref.edit();

        cardNumberText.setText(myPref.getString(CARDNUMBER, ""));
        Log.d(TAG, "Loaded cardnumber: "+myPref.getString(CARDNUMBER, ""));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefSave.putString(CARDNUMBER, cardNumberText.getText().toString());
                prefSave.commit();
            }
        });


        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpGetBalance httpGetBalance = new HttpGetBalance(getApplicationContext(), "425079021444", "1444") {
                    @Override
                    public void onResult(String balance) {
                        Log.d(TAG, "Balance is " + balance);
                        cardNumberText.setClickable(true);
                        saldoTekst.setText(balance);
                    }
                };
                cardNumberText.setClickable(false);
                httpGetBalance.execute();
                Log.d(TAG, "Click!");
            }
        });
        saldoTekst.setText("Hello World!");

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }


}
