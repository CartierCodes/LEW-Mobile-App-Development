package edu.lewisu.cs.example.todofragment;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ToDoDatabase {
    private static ToDoDatabase sToDoDatabase;
    private List<ToDo> mToDos;

    public static ToDoDatabase getInstance(Context context) {
        if (sToDoDatabase == null) {
            sToDoDatabase = new ToDoDatabase(context);
        }
        return sToDoDatabase;
    }

    private ToDoDatabase(Context context) {
        mToDos = new ArrayList<>();
        Resources res = context.getResources();
        String[] toDos = res.getStringArray(R.array.db_todos);
        String[] priorities = res.getStringArray(R.array.db_priorities);
        String[] completeStrings = res.getStringArray(R.array.db_complete);
        for (int i = 0; i < toDos.length; i++) {
            boolean complete;
            if(completeStrings[i].equals("true")) {
                complete = true;
            }else {
                complete = false;
            }
            mToDos.add(new ToDo(i + 1, toDos[i], Integer.parseInt(priorities[i]), complete));
            Log.d("Database", (mToDos.get(i)).toString());
        }
    }

    public List<ToDo> getToDos() {
        return mToDos;
    }

    public ToDo getToDo(int toDoId) {
        for (ToDo toDo : mToDos) {
            if (toDo.getId() == toDoId) {
                return toDo;
            }
        }
        return null;
    }
}
