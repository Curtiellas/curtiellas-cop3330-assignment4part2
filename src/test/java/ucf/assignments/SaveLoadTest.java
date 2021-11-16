/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SaveLoadTest {

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

    @Test
    void testSaveFile() throws FileNotFoundException {
        File folder = new File("src/test/java/ucf/assignments");

        //create the file variable (the files should be created and written to even if they do not exist)
        File file = new File("src/test/java/ucf/assignments/test1.csv");
        ToDoList list = getList().get(0);

        //run the save method
        SaveLoad.saveFile(file, list);

        //check that the correct info is written in the file
        Scanner sc = new Scanner(file);
        assertEquals("List 1", sc.nextLine());
        assertEquals("task 1,3333-01-01,Incomplete", sc.nextLine());
        assertEquals("task 2,2222-02-02,Complete", sc.nextLine());
        assertEquals("task 3,1111-03-03,Incomplete", sc.nextLine());
    }

    @Test
    void testLoadFile() throws FileNotFoundException {
        //file must exist for the test to succeed!!!
        File file = new File("src/test/java/ucf/assignments/test1.csv");

        //run the load method
        SaveLoad.loadFile(file);

        //check that the correct info is loaded to the objects
        assertEquals("List 1", Data.getMainList().get(0).getTitle());

        assertEquals("task 1", Data.getMainList().get(0).getTaskList().get(0).getDescription());
        assertEquals("3333-01-01", Data.getMainList().get(0).getTaskList().get(0).getDueDate().toString());
        assertEquals("Incomplete", Data.getMainList().get(0).getTaskList().get(0).getStatus());

        assertEquals("task 2", Data.getMainList().get(0).getTaskList().get(1).getDescription());
        assertEquals("2222-02-02", Data.getMainList().get(0).getTaskList().get(1).getDueDate().toString());
        assertEquals("Complete", Data.getMainList().get(0).getTaskList().get(1).getStatus());

        assertEquals("task 3", Data.getMainList().get(0).getTaskList().get(2).getDescription());
        assertEquals("1111-03-03", Data.getMainList().get(0).getTaskList().get(2).getDueDate().toString());
        assertEquals("Incomplete", Data.getMainList().get(0).getTaskList().get(2).getStatus());
    }

    @Test
    void testGetFilePaths() {
        //check that returned list of strings are the correct paths to actual save file/s
        //if the files in this test folder change, the result will change too!!!
        List<String> result = SaveLoad.getFilePaths("src/test/java/ucf/assignments");
        assertEquals("src\\test\\java\\ucf\\assignments\\ControllerTest.java", result.get(0));
        assertEquals("src\\test\\java\\ucf\\assignments\\DataTest.java", result.get(1));
        assertEquals("src\\test\\java\\ucf\\assignments\\SaveLoadTest.java", result.get(2));
        assertEquals("src\\test\\java\\ucf\\assignments\\test1.csv", result.get(3));
        assertEquals("src\\test\\java\\ucf\\assignments\\test2.csv", result.get(4));

    }
}