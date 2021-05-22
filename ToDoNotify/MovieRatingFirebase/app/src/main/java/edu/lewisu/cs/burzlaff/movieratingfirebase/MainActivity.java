package edu.lewisu.cs.burzlaff.movieratingfirebase;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MovieRatingAdapter.MovieRatingAdapterOnClick {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUserId;

    private final static int RC_SIGN_IN = 1;
    private MovieRatingAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        System.out.println("\n\n\n\n\n\n creating");
        Log.d("create", "\n\n\n\n\n done");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                intent.putExtra("uid", mUserId);
                startActivity(intent);
            }
        });

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user != null) mUserId = user.getUid();

        Log.d("create", "before adapter set");
        setThisAdapter();
        Log.d("create", "finished adapter set");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) mUserId = user.getUid();
                else{
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.Theme_MyApplication)
                                    .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(), RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        mAdapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        mAdapter.stopListening();
    }

    private void setThisAdapter() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("movie_rating").orderByChild("uid").equalTo(mUserId);

        FirebaseRecyclerOptions<MovieRating> options =
                new FirebaseRecyclerOptions.Builder<MovieRating>()
                        .setQuery(query, MovieRating.class)
                        .build();
        mAdapter = new MovieRatingAdapter(options,this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(int position) {
        Intent detailIntent = new Intent(this, RatingActivity.class);
        detailIntent.putExtra("uid", mUserId);

        DatabaseReference ref = mAdapter.getRef(position);
        String id = ref.getKey();

        detailIntent.putExtra("reference", id);
        startActivity(detailIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if(user != null){
                    mUserId = user.getUid();
                    setThisAdapter();
                }
            }
            if(resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }
}