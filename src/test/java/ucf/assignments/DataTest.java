/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

class DataTest {

    //mock main list to test on, the 2 lists are in files "test1.csv" and "test2.csv" in the test folder
    ArrayList<ToDoList> getList() {
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

        ArrayList<ToDoList> mainList = new ArrayList<>();
        mainList.add(list1);
        mainList.add(list2);

        return mainList;
    }

    ToDoList getSortedList() {
        Task task1 = new Task("task 1", LocalDate.parse("1111-01-01"), "Incomplete");
        Task task2 = new Task("task 2", LocalDate.parse("2222-02-02"), "Complete");
        Task task3 = new Task("task 3", LocalDate.parse("3333-03-03"), "Incomplete");

        ArrayList<Task> taskList1 = new ArrayList<>();
        taskList1.add(task1);
        taskList1.add(task2);
        taskList1.add(task3);

        return new ToDoList("List 1", taskList1);
    }

    @Test
    void getTitleStrings() {
        //our expected result, the list titles
        String[] titles = {"List 1", "List 2"};

        //should be true, the result is as expected
        assertArrayEquals(titles, Data.getTitleStrings( getList() ));
    }

    @Test
    void getTaskStrings() {
        //our expected result, the task information
        String[] tasks0 = {"task 1, 3333-01-01, Incomplete", "task 2, 2222-02-02, Complete", "task 3, 1111-03-03, Incomplete"};
        String[] tasks1 = {"task 4, 0000-01-01, Incomplete", "task 5, 1212-12-12, Incomplete"};

        //should be true, the result is as expected
        assertArrayEquals(tasks0, Data.getTaskStrings( getList(), 0 ));
        assertArrayEquals(tasks1, Data.getTaskStrings( getList(), 1 ));
    }

    @Test
    void getIncompleteTaskStrings() {
        //our expected result, the task information
        String[] tasks0 = {"task 1, 3333-01-01, Incomplete", "", "task 3, 1111-03-03, Incomplete"};
        String[] tasks1 = {"task 4, 0000-01-01, Incomplete", "task 5, 1212-12-12, Incomplete"};

        //should be true, the result is as expected
        assertArrayEquals(tasks0, Data.getIncompleteTaskStrings( getList(), 0 ));
        assertArrayEquals(tasks1, Data.getIncompleteTaskStrings( getList(), 1 ));
    }

    @Test
    void getCompleteTaskStrings() {
        //our expected result, the task information
        String[] tasks0 = {"", "task 2, 2222-02-02, Complete", ""};
        String[] tasks1 = {"", ""};

        //should be true, the result is as expected
        assertArrayEquals(tasks0, Data.getCompleteTaskStrings( getList(), 0 ));
        assertArrayEquals(tasks1, Data.getCompleteTaskStrings( getList(), 1 ));
    }

}