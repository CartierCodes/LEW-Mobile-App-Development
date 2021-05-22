package edu.lewisu.cs.example.todonotify;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    private ToDo mToDo;
    private EditText mTitleField;
    private Spinner mPrioritySpinner;
    private CheckBox mCompleteCheckBox;
    private Button mAddEditButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String mUserId;
    private String mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleField = findViewById(R.id.title_field);
        mTitleField.addTextChangedListener(new TitleListener());

        mPrioritySpinner = findViewById(R.id.spinner);
        mPrioritySpinner.setOnItemSelectedListener(new PrioritySelect());

        mCompleteCheckBox = findViewById(R.id.complete_checkbox);
        mCompleteCheckBox.setOnClickListener(new CompleteChangeListener());

        mAddEditButton = findViewById(R.id.add_edit_button);


        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mUserId = getIntent().getStringExtra("uid");
        mRef = getIntent().getStringExtra("ref");

        if (mRef != null) {
            //refer to the child node
            mDatabaseReference = mFirebaseDatabase.getReference().child("to_do").child(mRef);

            //set up listener
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mToDo = dataSnapshot.getValue(ToDo.class);
                    setUi();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                }
            };

            //attach listener to database ref
            mDatabaseReference.addValueEventListener(postListener);


        }else{
            //refer to the database
            mDatabaseReference = mFirebaseDatabase.getReference().child("to_do");
            mToDo = new ToDo(mUserId);
            mAddEditButton.setOnClickListener(new OnAddButtonClick());
        }

    }

    private void setUi(){
        if (mToDo != null) {
            mTitleField.setText(mToDo.getTitle());
            mPrioritySpinner.setSelection(mToDo.getPriority());
            mCompleteCheckBox.setChecked(mToDo.isComplete());
            mAddEditButton.setText(R.string.update);
            mAddEditButton.setOnClickListener(new OnUpdateButtonClick());
        }
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

    private class CompleteChangeListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(mCompleteCheckBox.isChecked()){
                mToDo.setComplete(true);
            }else{
                mToDo.setComplete(false);
            }
        }
    }

    private class PrioritySelect implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(mToDo != null)
                mToDo.setPriority(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private class OnAddButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            mDatabaseReference.push().setValue(mToDo);
            finish();
        }
    }

    private class OnUpdateButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mDatabaseReference.setValue(mToDo);
            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch(id){
            case R.id.delete:
                mDatabaseReference.removeValue();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}