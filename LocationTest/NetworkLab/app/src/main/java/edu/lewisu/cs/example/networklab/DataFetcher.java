package edu.lewisu.cs.example.networklab;


import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataFetcher {

    public interface OnDataReceivedListener{
        void onDataReceived(CovidData covidData);
        void onErrorResponse(VolleyError error);
    }

    private final String BASE_URL = "https://api.covidtracking.com/v2/us/daily/"; // https://api.covidtracking.com/v2/us/daily/2021-01-02.json
    private final String TAG = "Data_Fetcher";
    private final RequestQueue mRequestQueue;

    public DataFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fetchData(final OnDataReceivedListener listener){
        String url = BASE_URL + "2020" + java.time.LocalDate.now().toString().substring(4) + ".json";

        Log.d(TAG, url.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Log.d(TAG, ex.toString());
                }
                CovidData data = parseJsonData(response);
                listener.onDataReceived(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });

        mRequestQueue.add(request);
    }

    CovidData parseJsonData(JSONObject jsonObject) {
        String date;
        int cases;
        int testing;
        int deaths;

        try {
            JSONObject rawData = jsonObject.getJSONObject("data");
            date = rawData.getString("date");
            cases = rawData.getJSONObject("cases").getJSONObject("total").getInt("value");
            testing = rawData.getJSONObject("testing").getJSONObject("total").getInt("value");
            deaths = rawData.getJSONObject("outcomes").getJSONObject("death").getJSONObject("total").getInt("value");

            CovidData returnData = new CovidData(date, cases, testing, deaths);
            Log.d(TAG, returnData.toString());
            return returnData;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        CovidData defaultData = new CovidData("00-00-0000", 0, 0, 0);
        return defaultData;
    }
}
