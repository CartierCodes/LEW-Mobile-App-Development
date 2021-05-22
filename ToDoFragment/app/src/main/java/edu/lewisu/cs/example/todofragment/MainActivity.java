package edu.lewisu.cs.example.todofragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ToDoListAdapter.ToDoListAdapterOnClickHandler {
    private boolean twoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.detail_container) != null){
            twoPane=true;
        }

        RecyclerView recyclerView = findViewById(R.id.todo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ToDoListAdapter toDoListAdapter = new ToDoListAdapter(this);
        recyclerView.setAdapter(toDoListAdapter);
        toDoListAdapter.setToDos(ToDoDatabase.getInstance(this).getToDos());
    }

    @Override
    public void onClick(ToDo toDo) {
        if(twoPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = DetailFragment.newInstance(toDo.getId());
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }else {
            Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
            detailIntent.putExtra(DetailActivity.EXTRA_TODO_ID, toDo.getId());
            startActivity(detailIntent);
        }
    }
}