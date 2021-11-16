/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    //mock main list to test on, the 2 lists are in files "test1.csv" and "test2.csv" in the test folder
    @BeforeEach
    void createMainList() {
        Task task1 = new Task("task 1", LocalDate.parse("3333-01-01"), "Incomplete");
        Task task2 = new Task("task 2", LocalDate.parse("2222-02-02"), "Complete");
        Task task3 = new Task("task 3", LocalDate.parse("1111-03-03"), "Incomplete");

        Task task4 = new Task("task 4", LocalDate.parse("0000-01-01"), "Incomplete");
        Task task5 = new Task("task 5", LocalDate.parse("1212-12-12"), "Incomplete");

        ArrayList<Task> taskList1 = new ArrayList<>();
        taskList1.add(task1);
        taskList1.add(task2);
        taskList1.add(task3);
        ToDoList list1 = new ToDoList("List 1", taskList1);

        ArrayList<Task> taskList2 = new ArrayList<>();
        taskList2.add(task4);
        taskList2.add(task5);
        ToDoList list2 = new ToDoList("List 2", taskList2);

        Data.getMainList().add(list1);
        Data.getMainList().add(list2);
    }

    //list for adding
    ToDoList getList() {
        Task task6 = new Task("task6", LocalDate.parse("1212-05-05"), "Incomplete");
        ArrayList<Task> taskList1 = new ArrayList<>();
        taskList1.add(task6);
        return new ToDoList("List 3", taskList1);
    }

    //task for adding
    Task getTask() {
        return new Task("task7", LocalDate.parse("2121-05-05"), "Incomplete");
    }

    @Test
    void addList1() {
        //check that the new List object exists
        testableController.addList1( getList() );

        assertEquals("List 3", Data.getMainList().get(2).getTitle());
    }

    @Test
    void removeList1() {
        //check that the List object does not exist
        testableController.removeList1(0);

        //object at index 1 is moved down to index 0, check
        assertEquals("List 2", Data.getMainList().get(0).getTitle());
        assertEquals("task 4", Data.getMainList().get(0).getTaskList().get(0).getDescription());
        assertEquals("task 5", Data.getMainList().get(0).getTaskList().get(1).getDescription());
    }

    @Test
    void editListTitle() {
        //check that the title attribute value has changed
        testableController.editListTitle1(1, "This is pointless");

        assertEquals("This is pointless", Data.getMainList().get(1).getTitle());
    }

    @Test
    void addTask() {
        //check that the new Task object exists
        testableController.addTask1( 1, getTask() );

        //the second element [1] has now a third [2] task, check
        assertEquals("task7", Data.getMainList().get(1).getTaskList().get(2).getDescription());
    }

    @Test
    void removeTask() {
        //check that the Task object does not exist
        testableController.removeTask1(1, 0);

        //I removed the first task [0] in the second [1] list, check that the second [1] task was moved down
        assertEquals("task 5", Data.getMainList().get(1).getTaskList().get(0).getDescription());
    }

    @Test
    void editDescription() {
        //check that the description attribute value matches the user input
        testableController.editDescription1(1, 1, "new desc");

        //I changed the description of the second task [1] in the second [1] list, check
        assertEquals("new desc", Data.getMainList().get(1).getTaskList().get(1).getDescription());
    }

    @Test
    void editDate() {
        //check that the dueDate attribute value matches the user input
        testableController.editDate1(1, 1, LocalDate.parse("0000-01-01"));

        //I changed the date of the second task [1] in the second [1] list, check
        assertEquals(LocalDate.parse("0000-01-01"), Data.getMainList().get(1).getTaskList().get(1).getDueDate());
    }

    @Test
    void editStatus() {
        //check that the status attribute value matches the user input
        testableController.editStatus1(1, 1);

        //I changed the status of the second task [1] in the second [1] list, check
        assertEquals("Complete", Data.getMainList().get(1).getTaskList().get(1).getStatus());
    }
}