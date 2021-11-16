/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import java.util.ArrayList;

public class ToDoList {
    private String title;               //arbitrary string from user
    private ArrayList<Task> taskList;   //ArrayList of the tasks in the specific to-do list

    /** Parametrized constructor - use this to set all attributes upon list creation **/
    public ToDoList(String title, ArrayList<Task> taskList) {
        this.title = title;
        this.taskList = taskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
