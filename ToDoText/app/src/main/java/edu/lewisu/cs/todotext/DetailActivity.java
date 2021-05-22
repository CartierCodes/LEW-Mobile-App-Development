package edu.lewisu.cs.todotext;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TODO_ID = "toDoId";
    private ToDo mToDo;
    private ToDoDatabase mToDoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        EditText titleField = findViewById(R.id.title_field);
        titleField.addTextChangedListener(new TitleListener());

        Spinner prioritySpinner = findViewById(R.id.spinner);
        prioritySpinner.setOnItemSelectedListener(new PrioritySelectListener());

        CheckBox completeCheckBox = findViewById(R.id.complete_checkbox);
        completeCheckBox.setOnClickListener(new CompleteChangeListener());

        Button addEditButton = findViewById(R.id.add_edit_button);

        mToDoDatabase = ToDoDatabase.getInstance(this.getApplicationContext());


        int id = getIntent().getIntExtra(EXTRA_TODO_ID, 0);

        if (id != 0) {
            mToDo = mToDoDatabase.getToDo(id);
        }


        if(mToDo != null) {
            //set components to display detail information
            titleField.setText(mToDo.getTitle());
            prioritySpinner.setSelection(mToDo.getPriority());
            completeCheckBox.setChecked(mToDo.isComplete());
            addEditButton.setVisibility(View.GONE);

        }else{
            mToDo = new ToDo();
            addEditButton.setVisibility(View.VISIBLE);
            addEditButton.setOnClickListener(new OnAddButtonClick());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            mToDoDatabase.deleteToDo(mToDo.getId());

            try {
                mToDoDatabase.saveToFile(getApplicationContext());
            } catch (IOException e) {
                Log.d("DetailActivity", e.toString());
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class TitleListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mToDo.setTitle(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class CompleteChangeListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox)v;
            if (checkBox.isChecked()) {
                mToDo.setComplete(true);
            } else {
                mToDo.setComplete(false);
            }
        }
    }

    private class PrioritySelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mToDo.setPriority(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class OnAddButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mToDoDatabase.addToDo(mToDo);
            try {
                mToDoDatabase.saveToFile(getApplicationContext());
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}