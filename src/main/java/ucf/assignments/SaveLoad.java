/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveLoad {
    //folder where the files will be saved
    private static final String saveDirectory = "saveDirectory";
    private static final String separator = ",";
    private static final String separatorName = "Comma ";

    public static String getSeparator() {
        return separator;
    }

    public static String getSeparatorName() {
        return separatorName;
    }

    //to save ONE list to file (overloaded method)
    public static boolean saveFile(File file, ToDoList list) {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.write(list.getTitle() + "\n");

            for (int a = 0; a < list.getTaskList().size(); a++) {
                pw.write(list.getTaskList().get(a).getDescription() + separator);
                pw.write(list.getTaskList().get(a).getDueDate() + separator);
                pw.write(list.getTaskList().get(a).getStatus() + "\n");
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //to save ALL lists to files (overloaded method)
    public static boolean saveFile() {
        for (int a = 0; a < Data.getMainList().size(); a++) {
            String name = saveDirectory + "/" + Data.getMainList().get(a).getTitle() + ".csv";

            try {
                PrintWriter pw = new PrintWriter(name);
                pw.write(Data.getMainList().get(a).getTitle() + "\n");

                for (int b = 0; b < Data.getMainList().get(a).getTaskList().size(); b++) {
                    pw.write(Data.getMainList().get(a).getTaskList().get(b).getDescription() + separator);
                    pw.write(Data.getMainList().get(a).getTaskList().get(b).getDueDate() + separator);
                    pw.write(Data.getMainList().get(a).getTaskList().get(b).getStatus() + "\n");
                }

                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    //to load ONE list from file (overloaded method)
    public static boolean loadFile(File file) throws FileNotFoundException {
        //create task list to add when information is loaded
        ArrayList<Task> taskList = new ArrayList<>();

        //start scanner for each line
        Scanner scLine = new Scanner(file);

        //save list title, the first string
        String fileName;
        try {
            fileName = scLine.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        while (scLine.hasNext()) {
            String line = scLine.nextLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(separator);

            if (sc.hasNext())
                try {
                    Task task = new Task(sc.next(), LocalDate.parse(sc.next()), sc.next());

                if (!task.getStatus().equals("Complete") && !task.getStatus().equals("Incomplete")) {
                    return false;
                }
                taskList.add(task);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            sc.close();
        }
        scLine.close();

        //all information has been stored, now create the list
        ToDoList list = new ToDoList(fileName, taskList);

        //add the list to main list, it can now be displayed with the rest
        Data.getMainList().add(list);

        //return true for success
        return true;
    }

    //to load ALL lists from all files (overloaded method)
    public static boolean loadFile() throws FileNotFoundException {
        //get a list of file paths to load
        List<String> fileList = getFilePaths();

        //for each file in the folder toDoListsInfo/, repeat the loading process
        for (int a = 0; a < fileList.size(); a++) {
            //create task list and empty task to add when information is loaded
            ArrayList<Task> taskList = new ArrayList<>();

            //start scanner and set comma and next line new line as delimiters
            Scanner scLine = new Scanner(new File(fileList.get(a)));

            //save list title, the first string
            String fileName = null;
            try {
                fileName = scLine.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            while (scLine.hasNext()) {
                String line = scLine.nextLine();
                Scanner sc = new Scanner(line);
                sc.useDelimiter(separator);

                if (sc.hasNext())
                    try {
                        Task task = new Task(sc.next(), LocalDate.parse(sc.next()), sc.next());

                    if (!task.getStatus().equals("Complete") && !task.getStatus().equals("Incomplete"))
                        return false;

                    taskList.add(task);

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                sc.close();
            }

            //all information has been stored, now create the list
            ToDoList list = new ToDoList(fileName, taskList);

            //add the list to main list, it can now be displayed with the rest
            Data.getMainList().add(list);

            scLine.close();
        }

        return true;
    }

    //returns a list of the file names
    public static List<String> getFilePaths() {
        Path path = Paths.get(saveDirectory);
        List<String> fileList = null;

        try (Stream<Path> fileStream = Files.walk(path)){
            //creates a list of <Strings> with the file paths
            fileList = fileStream
                    .filter(Files::isRegularFile)
                    .map(Objects::toString)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }

    //creates the target directory
    public static File makeDir() {
        File folder = null;

        try {
            folder = new File(saveDirectory);
            folder.mkdirs();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return folder;
    }

}
