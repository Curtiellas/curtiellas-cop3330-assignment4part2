/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import java.time.LocalDate;

public class Task {
    private String description; //any string from the user
    private LocalDate dueDate;  //in the format YYYY-MM-DD
    private String status;      //"Complete" or "Incomplete"
    private int pseudoIndex;

    /** Parametrized constructor - use this to set all attributes upon task creation **/
    public Task(String description, LocalDate dueDate, String status) {
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task() {
        description = "Missing Description";
        dueDate = LocalDate.now();
        status = "Missing Status";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getPseudoIndex() {
        return pseudoIndex;
    }

    public void setPseudoIndex(int pseudoIndex) {
        this.pseudoIndex = pseudoIndex;
    }

    //return string of task elements in the format that will be displayed
    public String toStringClean() {
        return description + Data.getDisplaySeparator() + dueDate + Data.getDisplaySeparator() + status;
    }
}