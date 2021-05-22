package edu.lewisu.cs.example.todofragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private ToDo mToDo;
    private static final String ARG_TODO_ID = "toDoId";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int toDoId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TODO_ID, toDoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int toDoId=1;
        if(getArguments() != null){
            toDoId = getArguments().getInt(ARG_TODO_ID);
        }
        ToDoDatabase toDoDatabase = ToDoDatabase.getInstance(getContext());
        mToDo = toDoDatabase.getToDo(toDoId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        EditText titleField = view.findViewById(R.id.title_field);
        Spinner prioritySpinner = view.findViewById(R.id.spinner);
        CheckBox completeCheckBox = view.findViewById(R.id.complete_checkbox);

        titleField.setText(mToDo.getTitle());
        prioritySpinner.setSelection(mToDo.getPriority());
        completeCheckBox.setChecked(mToDo.isComplete());
        return view;
    }
}