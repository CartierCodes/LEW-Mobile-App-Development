package edu.lewisu.cs.example.bestseller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BookAdapter.BookAdapterOnClickHandler{
    private final static String TAG = MainActivity.class.getSimpleName();
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;

    private final BooksFetcher.OnBooksReceivedListener mFetchBooksListener = new BooksFetcher.OnBooksReceivedListener() {
        @Override
        public void onBooksReceived(List<Book> books) {
            progressBar.setVisibility(View.GONE);
            bookAdapter.setBookData(books);
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

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);

        BooksFetcher booksFetcher = new BooksFetcher(this);
        booksFetcher.fetchBooks(mFetchBooksListener);
    }

    @Override
    public void onClick(Book book) {
        Toast.makeText(this,book.getTitle(), Toast.LENGTH_SHORT).show();
    }
}