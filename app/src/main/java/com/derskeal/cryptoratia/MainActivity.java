package com.derskeal.cryptoratia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DEBUG_TAG = "NetworkStatusExample";
    public static String cn = "";


    private SwipeRefreshLayout mySLR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        mySLR = (SwipeRefreshLayout) findViewById(R.id.myswiperefresh);


        mySLR.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                        mySLR.setRefreshing(false);
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                Log.i(LOG_TAG, "Refresh menu item selected");
                mySLR.setRefreshing(true);
                break;

            case R.id.action_quit:
                onStop();
                onDestroy();
                System.exit(0);
                break;
        }

        return true;
    }


    public void openBTC(View view) {
        mySLR.setRefreshing(true);
        cn = btc.class.getSimpleName();
        String link = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=ETH,USD,NGN,KWD,BHD,OMR,GBP,EUR,CHF,CAD,AUD,BND,SGD,PLN,NZD,BRL,ILS,RON,TRL,NOK,QAR,SAR,BGN";
        Log.d(LOG_TAG, cn);

        getTheJSON(link);
    }

    public void openETH(View view) {
        mySLR.setRefreshing(true);
        cn = eth.class.getSimpleName();
        String link = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC,USD,NGN,KWD,BHD,OMR,GBP,EUR,CHF,CAD,AUD,BND,SGD,PLN,NZD,BRL,ILS,RON,TRL,NOK,QAR,SAR,BGN";
        Log.d(LOG_TAG, cn);

        getTheJSON(link);
    }

    public void getTheJSON(String url) {

        try {
            new DownloadWebpageTask().execute(url);
        }
        catch (Exception e) {
            Log.i(LOG_TAG, "Error occured");
        }
    }


    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d(LOG_TAG, cn);
            switch (cn) {
                case "btc":
                    Intent btcintent = new Intent(MainActivity.this, btc.class);
                    btcintent.putExtra("jsonresult", result);
                    startActivity(btcintent);
                    break;
                case "eth":
                    Log.d(LOG_TAG, "weyrey yii shi click sha");
                    Intent ethintent = new Intent(MainActivity.this, eth.class);
                    ethintent.putExtra("jsonresult", result);
                    startActivity(ethintent);
                    break;
            }

        }
    }



}