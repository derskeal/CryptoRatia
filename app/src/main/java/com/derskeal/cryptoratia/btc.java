package com.derskeal.cryptoratia;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class btc extends AppCompatActivity {
    private static final String LOG_TAG = btc.class.getSimpleName();

    private SwipeRefreshLayout mySLR;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter theAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btc);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Window dgw = this.getWindow();
        dgw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        EditText btcet = (EditText) findViewById(R.id.btcinputtext);
        btcet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                actionId = 4;
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    convert(v);
                    handled = true;
                }
                return handled;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        workFunction(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.other, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_refresh:
                Log.d(LOG_TAG, "Refresh menu item selected");
                mySLR.setRefreshing(true);
                workFunction(1);
                break;

            case R.id.action_quit:
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void workFunction(double un) {
        String[] myDataset = {"ETH","NGN", "USD", "KWD", "BHD", "OMR", "GBP", "EUR", "CHF","CAD","AUD","BND","SGD","PLN",
                "NZD","BGN","BRL","ILS","RON","TRL","NOK","QAR","SAR"};
        String[] myDatasetValue = new String[23];


        Intent mybtcintent = getIntent();
        String myresult = mybtcintent.getStringExtra("jsonresult");
        Log.d(LOG_TAG+"in btc", myresult);

        for(int i = 0; i < myDatasetValue.length; i++ ) {

            try {
                JSONObject jt = new JSONObject(myresult);
                Double jtr = Double.parseDouble(jt.getString(myDataset[i]));
                jtr = jtr * un;
                myDatasetValue[i] = jtr.toString();

            } catch (JSONException jse) {
                Log.d(LOG_TAG, jse.getMessage().toString());
            }
        }


        theAdapter = new TheAdapter(myDataset, myDatasetValue);
        mRecyclerView.setAdapter(theAdapter);

        mySLR = (SwipeRefreshLayout) findViewById(R.id.myswiperefreshbtc);
        mySLR.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                        workFunction(1);
                    }
                }
        );

        mySLR.setRefreshing(false);

    }

    public void convert(View view) {
        EditText btcet = (EditText)findViewById(R.id.btcinputtext);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btcet.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        Double usernum = Double.parseDouble(btcet.getText().toString());
        workFunction(usernum);

    }

}