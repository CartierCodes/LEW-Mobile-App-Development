package edu.lewisu.cs.example.bestseller;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if(mBook != null) {
            TextView titleTextView = findViewById(R.id.titleTextView);
            titleTextView.setText(mBook.getTitle());

            TextView authorTextView = findViewById(R.id.authorTextView);
            authorTextView.setText(mBook.getAuthor());

            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(mBook.getDescription());

            TextView amazonTextView = findViewById(R.id.amazonUrlTextView);
            amazonTextView.setText(mBook.getAmazon());
        }

    }
}