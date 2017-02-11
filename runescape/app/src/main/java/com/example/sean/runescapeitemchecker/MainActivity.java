package com.example.sean.runescapeitemchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView mTextView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textbox);

        sendNetworkRequest();
    }

    private void sendNetworkRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=1515";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // log response
                        Log.i("MainActivity", response);

                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MainActivity", "Error with Volley request");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseResponse(String response) {
        JSONObject jsonObj = null;
        try {
             jsonObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("MainActivity", "jsonObj" + jsonObj.toString());

        JSONObject item = null;
        try {
            item = jsonObj.getJSONObject("item");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject current = null;
        try {
            current = item.getJSONObject("current");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int price = 0;
        try {
            price = (int) current.get("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mTextView.setText("Yew log current price: " + price);

    }
}
