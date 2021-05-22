package edu.lewisu.cs.todotext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ToDoListAdapter.TodoListAdapterOnClickHandler {

    ToDoListAdapter mToDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.todo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mToDoListAdapter = new ToDoListAdapter(this);
        recyclerView.setAdapter(mToDoListAdapter);
        mToDoListAdapter.setToDos(ToDoDatabase.getInstance(this).getToDos());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            ToDoDatabase.getInstance(getApplicationContext()).saveToFile(getApplicationContext());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            ToDoDatabase.getInstance(getApplicationContext()).readFromFile(getApplicationContext());
            mToDoListAdapter.setToDos(ToDoDatabase.getInstance(getApplicationContext()).getToDos());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("MainActivity", "destoryed\n\n\n");
    }

    @Override
    public void onClick(ToDo toDo) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_TODO_ID, toDo.getId());
        startActivity(detailIntent);
    }
}