package com.example.oday.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oday.project1.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  //  TextView mResultsTextView;
    TextView mErrorMessgeTextView;
    ProgressBar mLoadingIndicator;
    ArrayList<String> casesKinds = new ArrayList<String>(Arrays.asList("total_cases", "total_recovered", "total_unresolved", "total_deaths", "total_new_cases_today",
            "total_new_deaths_today", "total_active_cases", "total_serious_cases", "total_affected_countries"));
    //ArrayList<Integer> casesKindsStringFile = new ArrayList<Integer>(Arrays.asList(R.string.total_cases, R.string.total_recovered, R.string.total_unresolved, R.string.total_deaths, R.string.total_new_cases_today,
      //      R.string.total_new_deaths_today, R.string.total_active_cases, R.string.total_serious_cases, R.string.total_affected_countries));
    ArrayList<Integer> jsonValues = new ArrayList<>();

    //ListView Variables
    ListView mListView;
    ArrayList<itemClass> listView = new ArrayList<itemClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    mResultsTextView = findViewById(R.id.tv_results);
        mErrorMessgeTextView = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (findViewById(R.id.pb_loading_indicator));
        makeWebsiteURL();

     //  Toast.makeText(this, String.valueOf(jsonValues.get(0)), Toast.LENGTH_SHORT).show();

        //List
        mListView = findViewById(R.id.list_items);




    }

// Creating_URL-Function
    void makeWebsiteURL() {
        URL websiteURL = NetworkUtils.buildUrl();
        //    Toast.makeText(this, websiteURL.toString(), Toast.LENGTH_SHORT).show();
        String results = null;

        new ConnectingToWebTask().execute(websiteURL);
    }

//Just a Function ** Not Imporotant

    private void showJsonDataView() {
        mErrorMessgeTextView.setVisibility(View.INVISIBLE);
    //    mListView.setVisibility(View.VISIBLE);


    }
//Just a Function ** Not Important
    private void showErrorMessage() {
        mErrorMessgeTextView.setVisibility(View.VISIBLE);
  //      mListView.setVisibility(View.INVISIBLE);
    }

    //from https://www.java67.com/2015/10/how-to-declare-arraylist-with-values-in-java.html

    /**
     * Parsing JSON to JAVA Function
     * it takes str that will be a response from http
     * and return values that were saved in JasonArray
     * @param strJson
     * @throws JSONException
     */

    private void ParsingJson(String strJson) throws JSONException {
        if (strJson != null) {
            JSONObject jsonObject = new JSONObject(strJson);
            // Getting JSON Array node
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject objFromArray = results.getJSONObject(0);
            for (int i = 0; i <= 8; i++) {
                jsonValues.add(objFromArray.getInt(casesKinds.get(i)));
            }

            Toast.makeText(MainActivity.this, "تم تحديث المعلومات", Toast.LENGTH_SHORT).show();


        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    //AsyncTask
    public class ConnectingToWebTask extends AsyncTask<URL, Void, String> {

        public ConnectingToWebTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {
            URL websiteUrl = urls[0];
            String results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(websiteUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showJsonDataView();
                try {
                    ParsingJson(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 8; i++) {
                    listView.add(new itemClass(casesKinds.get(i), String.valueOf(jsonValues.get(i))));
                }

                MainActivity.adapter ad = new MainActivity.adapter(listView);
                mListView.setAdapter(ad);


                //mResultsTextView.setText(s);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_test) {
            startActivity(new Intent(MainActivity.this, Questions.class));
            return true;
        } else if (menuItemThatWasSelected == R.id.action_refresh) {
            listView.clear();
            makeWebsiteURL();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    //Adapter:::
    class adapter extends BaseAdapter {
        ArrayList<itemClass> informationList = new ArrayList<itemClass>();

        public adapter(ArrayList<itemClass> alist) {
            this.informationList = alist;
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        @Override
        public Object getItem(int i) {
            return informationList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.item, null);


            TextView tv1 = (TextView) v.findViewById(R.id.leftTextView);
            TextView tv2 = (TextView) v.findViewById(R.id.RightTextView);
            //conncting to item class

            tv1.setText(informationList.get(i).mTextView1);
            tv1.setText(R.string.total_active_cases);

            tv2.setText(informationList.get(i).mTextView2);

            return v;
        }
    }

}
