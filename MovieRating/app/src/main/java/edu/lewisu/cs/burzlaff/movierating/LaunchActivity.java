package edu.lewisu.cs.burzlaff.movierating;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LaunchActivity extends AppCompatActivity {
    public static final int RATING_INTENT_RESULT = 100;
    public static final String TAG = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Button launchRatingButton = findViewById(R.id.movieRatingButton);
        launchRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ratingIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(ratingIntent, RATING_INTENT_RESULT);
            }
        });

        Button launchCompletedButton = findViewById(R.id.completedRatingsButton);
        launchCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent completedIntent = new Intent(getApplicationContext(), RatingsActivity.class);
                startActivity(completedIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK  && requestCode == RATING_INTENT_RESULT) {
            int rating = data.getIntExtra("rating", 0);
            String movieTitle = data.getStringExtra("movieTitle");
            String directorName = data.getStringExtra("directorName");
            String toastString = "Rating entered\n";
            toastString += "Movie Title: " + movieTitle + "\n";
            toastString += "Director Name: " + directorName + "\n";
            String ratingString = getResources().getQuantityString(R.plurals.star_rating, rating, rating);
            toastString += ratingString;

            //Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
        }
    }
}