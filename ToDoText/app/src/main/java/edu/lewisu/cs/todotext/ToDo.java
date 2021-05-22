package edu.lewisu.cs.todotext;

public class ToDo {
    private int id;
    private String title;
    private int priority;
    private boolean complete;


    public ToDo(int id, String title, int priority, boolean complete) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.complete = complete;
    }

    public ToDo(String title, int priority, boolean complete) {
        this.title = title;
        this.priority = priority;
        this.complete = complete;
    }

    public ToDo(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + priority + "," + complete;
    }
}
