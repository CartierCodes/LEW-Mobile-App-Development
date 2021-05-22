package edu.lewisu.cs.example.quizapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class QuestionFetcher {

    public interface OnQuestionReceivedListener {
        void onQuestionReceived(Question question);
        void onErrorResponse(VolleyError error);
    }

    private static final String BASE_URL = "https://opentdb.com/api.php?amount=1&category=18&type=boolean";
    private final String TAG = QuestionFetcher.class.getSimpleName();

    private final RequestQueue mRequestQueue;

    public QuestionFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void fetchQuestion(final OnQuestionReceivedListener listener) {
        String url = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("amount", "1")
                .appendQueryParameter("category", "18")
                .appendQueryParameter("type", "boolean")
                .build().toString();
    }

    private Question parseJsonQuestion(JSONObject json) {
        Question question = null;
        try {


        }
        catch (Exception e) {
            Log.e(TAG, "One or more fields not found in the JSON data");
        }

        return question;
    }

}
