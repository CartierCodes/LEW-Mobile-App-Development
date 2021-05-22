package edu.lewisu.cs.example.networklab;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView dateTextView;
    private TextView casesTextView;
    private TextView testingTextView;
    private TextView deathsTextView;

    private final DataFetcher.OnDataReceivedListener onDataReceivedListener = new DataFetcher.OnDataReceivedListener() {
        @Override
        public void onDataReceived(CovidData covidData) {
            //setContentView(R.layout.activity_main);
            Log.d(TAG, "ABout to set the things");

            dateTextView.setText("Date: " + covidData.getDate());
            Log.d(TAG, "Set the date");
            casesTextView.setText("Cases: " + String.valueOf(covidData.getCases()));
            testingTextView.setText("Testing: " + String.valueOf(covidData.getTested()));
            deathsTextView.setText("Deaths: " + String.valueOf(covidData.getDeaths()));

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTextView = findViewById(R.id.dateTextView);
        casesTextView = findViewById(R.id.casesTextView);
        testingTextView = findViewById(R.id.testingTextView);
        deathsTextView = findViewById(R.id.deathsTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goButtonClick(View v) {
        String jsonData = "";

        HttpURLConnection urlConnection=null;

        try{
            DataFetcher dataFetcher = new DataFetcher(this);
            dataFetcher.fetchData(onDataReceivedListener);
        }catch(Exception ex){
            Log.d(TAG, ex.toString());
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        

    }

}
