package edu.lewisu.cs.todotext;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ToDoDatabase {

    private static int sNextId = 1;
    private static ToDoDatabase sToDoDatabase;
    public static final String FILENAME = "toDoList.txt";

    private List<ToDo> mToDoList;

    public static ToDoDatabase getInstance(Context context) {
        if (sToDoDatabase == null) {
            sToDoDatabase = new ToDoDatabase(context);
        }
        return sToDoDatabase;
    }


    private ToDoDatabase(Context context) {
        mToDoList = new ArrayList<>();
    }


    public List<ToDo> getToDos() {
        return mToDoList;
    }

    public ToDo getToDo(int toDoId) {
        for (ToDo toDo : mToDoList) {
            if (toDo.getId() == toDoId) {
                return toDo;
            }
        }
        return null;
    }

    public void addToDo(ToDo toDo) {
        toDo.setId(sNextId);
        sNextId++;
        mToDoList.add(toDo);
    }

    public void deleteToDo(int id) {
        Iterator<ToDo> itr = mToDoList.iterator();

        while (itr.hasNext()) {
            ToDo toDo = itr.next();
            if (toDo.getId() == id) {
                itr.remove();
            }
        }
    }

    public void saveToFile(Context context) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(sNextId);

        for (ToDo toDo : mToDoList) {
            writer.println(toDo.toString());
        }
        writer.close();
    }

    public void readFromFile(Context context) throws IOException {
        BufferedReader reader = null;

        try {
            FileInputStream inputStream = context.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            mToDoList.clear();

            String line;
            ToDo toDo;

            if ((line = reader.readLine()) != null) {
                sNextId = Integer.parseInt(line);
            }

            while ((line = reader.readLine()) != null) {
                toDo = new ToDo();
                String[] tokens = line.split(",");
                toDo.setId(Integer.parseInt(tokens[0]));
                toDo.setTitle(tokens[1]);
                toDo.setPriority(Integer.parseInt(tokens[2]));
                toDo.setComplete(Boolean.parseBoolean(tokens[3]));
                mToDoList.add(toDo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No File Found");
            System.out.println(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
