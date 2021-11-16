/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    /** List Creation controls **/
    @FXML
    private TextField listTitleInput;                   //created list will have this title
    @FXML
    private Button addListButton;                       //list is created when clicked

    /** Task Creation controls **/
    @FXML
    private TextField taskDescriptionInput;             //created task will have this description
    @FXML
    private TextField taskDueDateInput;                 //created task will have this due date
    @FXML
    private Button addTaskButton;                       //task is created when clicked
    @FXML
    private ToggleGroup taskCreationStatusToggleGroup;  //group of the following 2 radio buttons, their selection is mutually exclusive
    @FXML
    private RadioButton taskCreationIncompleteToggle;   //created task will be 'incomplete' if selected
    @FXML
    private RadioButton taskCreationCompleteToggle;     //created task will be 'complete' if selected

    /** Task Filter controls **/
    @FXML
    private RadioButton taskFilterAllToggle;            //show all tasks if selected (default)
    @FXML
    private RadioButton taskFilterIncompleteToggle;     //show incomplete tasks if selected
    @FXML
    private RadioButton taskFilterCompleteToggle;       //show complete tasks if selected
    @FXML
    private CheckMenuItem compactViewMenu;              //set the task list view to compact

    /** Message Area **/
    @FXML
    private Label helpMessage;                          //different messages to display

    /** Display Panels / List Holders **/
    @FXML
    private ListView<String> toDoListListView;          //panel where lists are displayed
    @FXML
    private ListView<String> taskListView;              //panel where tasks are displayed
    @FXML
    private ObservableList<String> observableListList = FXCollections.observableArrayList();    //list that can be displayed, for lists
    @FXML
    private ObservableList<String> observableListTask = FXCollections.observableArrayList();    //list that can be displayed, for tasks

    /** File Load/Save Screen **/
    private FileChooser fileChooser = new FileChooser();    //default window where a file can be chosen or created

    /** Color Constants **/
    //color text guide: helpMessage.setTextFill((Paint.valueOf(#ColorCode)));
    private final String redError = "#FF0000";
    private final String orangeNeutral = "#D18700";
    private final String greenSuccess = "#29A829";

    /************** General Management **************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //welcome message
        setHelpMessage("""
                To start, create a new to-do list. Type a title into the top text field and then click [Add List].
                To load saved to-do lists, click [File] in the top menu bar, and then click [Load...].""", orangeNeutral);

        //continuously displays the Observable List in the List View, like a mirror
        //Observable List is updated later when necessary
        toDoListListView.setItems(observableListList);
        taskListView.setItems(observableListTask);

        //create directory for file chooser
        File folder = SaveLoad.makeDir();
        if (folder == null)
            setHelpMessage("Unexpected error accessing default save directory", redError);

        //the load/save window will show this folder
        fileChooser.setInitialDirectory(folder);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma Separated Values(*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("Text Document(*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    @FXML
    protected void showAbout() {
        setHelpMessage("""
                Made by novice Luis Curtiellas.
                UCF COP3330 Fall 2021 Assignment 4.
                This application can create and edit to-do lists, each of which can hold tasks.
                Guide here: https://github.com/Curtiellas/curtiellas-cop3330-assignment4part2""", orangeNeutral);
    }

    @FXML
    protected void setHelpMessage(String message, String color) {
        //set and display a help message depending on the last action of the user
        //e.g. when creating a new task, instruct to input all fields
        helpMessage.setTextFill((Paint.valueOf(color)));
        helpMessage.setText(message);
    }

    @FXML
    protected void rightClick() {
        setHelpMessage("Rick clicking is not implemented, use the top menu bar.", orangeNeutral);
    }

    @FXML
    protected void exitGUI() {
        //exits the GUI
        Platform.exit();
    }

    /************** List Management **************/
    @FXML
    protected void displayLists() {
        //update Observable List, display all to-do Lists
        observableListList.setAll(Data.getTitleStrings(Data.getMainList()));
    }

    @FXML
    protected void addList() {
        //change to true if error
        boolean error = false;

        //creating an empty task list (goes inside the 'to-do list' list)
        ArrayList<Task> taskList = new ArrayList<>();

        //creating a to-do list with title from user
        ToDoList list = null;
        try {
            //if there is no title entered, display error and do not add
            if (listTitleInput.getText().isEmpty()) {
                setHelpMessage("Error adding list: Title must be entered", redError);
                return;
            }
            list = new ToDoList(listTitleInput.getText(), taskList);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error adding list: Unknown", redError);
            return;
        }

        //adding created list to our 'to-do list' list
        testableController.addList1(list);

        //displaying all lists, including new one
        displayLists();

        //auto-select last created list
        toDoListListView.getSelectionModel().selectLast();
        displayTasks();

        //remove text from text field
        listTitleInput.setText("");

        //success message
        setHelpMessage("Success: to-do list added", greenSuccess);
    }

    @FXML
    protected void removeList() {
        //change to true if error
        boolean error = false;

        //find index of selected list
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        try {
            //remove the object at index selected
            testableController.removeList1(index);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error removing list: There must be a list to remove", redError);
            return;
        }

        //success message
        setHelpMessage("Success: List removed", greenSuccess);

        //display lists and tasks again (without the removed list)
        displayLists();
        displayTasks();
    }

    @FXML
    protected void editListTitle() {
        //change to true if error
        boolean error = false;

        //check if there is any list to edit
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error editing list title: There is no list to edit", redError);
            return;
        }

        //check if there is a new title entered
        else if (listTitleInput.getText().isEmpty()) {
            setHelpMessage("Error editing list title: Enter a new title under List Creation and try again", redError);
            return;
        }

        //find index of selected list
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        try {
            //edit the title at index selected
            testableController.editListTitle1(index, listTitleInput.getText());
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error editing list title: Unknown", redError);
            return;
        }

        //display list of to-do lists again (with new title list)
        displayLists();

        //remove text from text field
        listTitleInput.setText("");

        //success message
        setHelpMessage("Success: List title edited", greenSuccess);
    }

    /************** Task Management **************/
    @FXML
    protected void displayTasks() {
        //set view to compact (separated by commas) or not Compact (separated by new line)
        if (compactViewMenu.isSelected())
            Data.setDisplaySeparator(", ");
        else
            Data.setDisplaySeparator("\n");

        //if there are no lists to contain any tasks, clear all from view
        if (Data.getMainList().size() < 1) {
            observableListTask.clear();
            return;
        }

        //get index of list selected
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        //a toggle option filters the tasks displayed by status
        if (taskFilterAllToggle.isSelected()) {
            observableListTask.setAll(Data.getTaskStrings(Data.getMainList(), index));
        }
        else if (taskFilterIncompleteToggle.isSelected()) {
            observableListTask.setAll(Data.getIncompleteTaskStrings(Data.getMainList(), index));
        }
        else if (taskFilterCompleteToggle.isSelected()) {
            observableListTask.setAll(Data.getCompleteTaskStrings(Data.getMainList(), index));
        }
        else {
            setHelpMessage("Error displaying tasks: One Task Filter must be selected", redError);
        }
    }

    @FXML
    protected void addTask() {
        //change to true if error
        boolean error = false;

        //check if there is any list
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error adding task: There must be a list to add a task.", redError);
            return;
        }

        if (taskDescriptionInput.getText().equals("") || taskDueDateInput.getText().equals("")) {
            setHelpMessage("Error adding task: You must enter description and due date.", redError);
            return;
        }

        if (taskDescriptionInput.getText().contains(",")) {
            setHelpMessage("Error adding task: " + SaveLoad.getSeparatorName() + "\"" + SaveLoad.getSeparator() + "\" cannot be included in the description. It is used internally.", redError);
            return;
        }

        //try to convert due date input to LocalTime
        LocalDate time;
        try {
            time = LocalDate.parse((taskDueDateInput.getText()));
        } catch (Exception e) {
            setHelpMessage("Error adding task: Invalid date or format. Correct format YYYY-MM-DD", redError);
            e.printStackTrace();
            return;
        }

        //if one of the fields is missing, display error and do not add
        if (taskDescriptionInput.getText().isEmpty()) {
            setHelpMessage("Error adding task: Description must be entered", redError);
            return;
        }
        else if (taskDueDateInput.getText().isEmpty()) {
            setHelpMessage("Error adding task: Due date must be entered", redError);
            return;
        }
        else if (!taskCreationIncompleteToggle.isSelected() && !taskCreationCompleteToggle.isSelected()) {
            setHelpMessage("Error adding task: Status must be selected", redError);
            return;
        }

        //creating a new empty task object
        Task task = new Task();

        try {
            //set the values of the task
            task.setDescription(taskDescriptionInput.getText());
            task.setDueDate(time);

            if (taskCreationIncompleteToggle.isSelected())
                task.setStatus("Incomplete");
            else
                task.setStatus("Complete");

        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error adding list: Unknown", redError);
            return;
        }

        //get index of selected list
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        //adding created task to our list
        testableController.addTask1(index, task);

        //displaying all tasks, including new one
        displayTasks();

        //remove text from text field, re-select incomplete as default
        taskDescriptionInput.setText("");
        taskDueDateInput.setText("");
        taskCreationIncompleteToggle.setSelected(true);

        //success message
        setHelpMessage("Success: Task added", greenSuccess);
    }

    @FXML
    protected void removeTask() {
        //set to true later if error
        boolean error = false;

        //check if there are no lists
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error removing task: There must be a list to remove a task.", redError);
            return;
        }

        //get list and task selected
        int indexList = toDoListListView.getFocusModel().getFocusedIndex();
        int indexTask = taskListView.getFocusModel().getFocusedIndex();

        //check if there are no tasks
        if (Data.getMainList().get(indexList).getTaskList().size() < 1) {
            setHelpMessage("Error removing task: There must be a task to remove.", redError);
            return;
        }

        try {
            //remove the object at index selected
            testableController.removeTask1(indexList, indexTask);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error removing task: Unknown", redError);
            return;
        }

        //displaying all tasks, without removed task
        displayTasks();

        //success message
        setHelpMessage("Success: Task removed", greenSuccess);
    }

    @FXML
    protected void removeAllTasks() {
        //set to true later if error
        boolean error = false;

        //check if there are no lists
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error removing all tasks: There must be a list to remove a task.", redError);
            return;
        }

        //get list selected
        int indexList = toDoListListView.getFocusModel().getFocusedIndex();

        //check if there are no tasks
        if (Data.getMainList().get(indexList).getTaskList().size() < 1) {
            setHelpMessage("Error removing all tasks: There must be a task to remove.", redError);
            return;
        }

        try {
            //remove the object at index selected
            Data.getMainList().get(indexList).getTaskList().clear();
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error removing all tasks: Unknown", redError);
            return;
        }

        //displaying empty task list
        displayTasks();

        //success message
        setHelpMessage("Success: All Tasks removed", greenSuccess);
    }

    @FXML
    protected void editDescription() {
        //set to true later if error
        boolean error = false;

        //check if there are no lists
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error editing task: There must be a list to edit a task.", redError);
            return;
        }

        if (taskDescriptionInput.getText().contains(",")) {
            setHelpMessage("Error editing task: " + SaveLoad.getSeparatorName() + "\"" + SaveLoad.getSeparator() + "\" cannot be included in the description. It is used internally.", redError);
            return;
        }

        //get list and task selected
        int indexList = toDoListListView.getFocusModel().getFocusedIndex();
        int indexTask = taskListView.getFocusModel().getFocusedIndex();

        //check if there are no tasks
        if (Data.getMainList().get(indexList).getTaskList().size() < 1) {
            setHelpMessage("Error editing task: There must be a task to edit.", redError);
            return;
        }

        //check that there is a new description in text field
        if (taskDescriptionInput.getText().equals("")) {
            setHelpMessage("Error editing task: Enter a new description under Task Creation and try again.", redError);
            return;
        }

        try {
            //edit mainList.taskList.description at index selected
            testableController.editDescription1(indexList, indexTask, taskDescriptionInput.getText());
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error editing task description: Unknown", redError);
            return;
        }

        //displaying all tasks, with new description
        displayTasks();

        //remove text from text field
        taskDescriptionInput.setText("");

        //success message
        setHelpMessage("Success: Task description edited", greenSuccess);
    }

    @FXML
    protected void editDate() {
        //set to true later if error
        boolean error = false;

        //check if there are no lists
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error editing task: There must be a list to edit a task.", redError);
            return;
        }

        //get list and task selected
        int indexList = toDoListListView.getFocusModel().getFocusedIndex();
        int indexTask = taskListView.getFocusModel().getFocusedIndex();

        //check if there are no tasks
        if (Data.getMainList().get(indexList).getTaskList().size() < 1) {
            setHelpMessage("Error editing task: There must be a task to edit.", redError);
            return;
        }

        //check that there is a new due date in text field
        if (taskDueDateInput.getText().equals("")) {
            setHelpMessage("Error editing task: Enter a new due date under Task Creation and try again.", redError);
            return;
        }

        //try to convert due date input to LocalTime
        LocalDate time;
        try {
            time = LocalDate.parse((taskDueDateInput.getText()));
        } catch (Exception e) {
            setHelpMessage("Error editing task: Invalid date or format. Correct format YYYY-MM-DD", redError);
            e.printStackTrace();
            return;
        }

        try {
            //edit mainList.taskList.dueDate at index selected
            testableController.editDate1(indexList, indexTask, time);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error editing task due date: Unknown", redError);
            return;
        }

        //displaying all tasks, with new due date
        displayTasks();

        //remove text from text field
        taskDueDateInput.setText("");

        //success message
        setHelpMessage("Success: Task due date edited", greenSuccess);
    }

    @FXML
    protected void editStatus() {
        //set to true later if error
        boolean error = false;

        //check if there are no lists
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error editing task: There must be a list to edit a task.", redError);
            return;
        }

        //get list and task selected
        int indexList = toDoListListView.getFocusModel().getFocusedIndex();
        int indexTask = taskListView.getFocusModel().getFocusedIndex();

        //check if there are no tasks
        if (Data.getMainList().get(indexList).getTaskList().size() < 1) {
            setHelpMessage("Error editing task: There must be a task to edit.", redError);
            return;
        }

        try {
            //edit mainList.taskList.status at index selected to the other value
            testableController.editStatus1(indexList, indexTask);
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        }

        //failure message
        if (error) {
            setHelpMessage("Error editing task status: Unknown", redError);
            return;
        }

        //displaying all tasks, with new status
        displayTasks();

        //re-select incomplete as default (just in case)
        taskCreationIncompleteToggle.setSelected(true);

        //success message
        setHelpMessage("Success: Task status edited", greenSuccess);
    }

    @FXML
    protected void sortByDate() {
        if (Data.getMainList().size() < 1 || Data.getMainList().get(toDoListListView.getFocusModel().getFocusedIndex()).getTaskList().size() < 1)
            return;

        //find index of selected list
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        //send index of the selected list to method that sorts tasks
        if (Data.sortByDate(index)) {
            displayTasks();
            setHelpMessage("Success: Tasks on selected list sorted by due date", greenSuccess);
        }
        else
            setHelpMessage("Error sorting by due date: Unknown", redError);
    }

    /************** Save/Load Management **************/
    @FXML
    protected void saveOneList() {
        //open the file chooser window and create a file variable of the selected file
        File file = fileChooser.showSaveDialog(new Stage());

        //check if there is any list to edit
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error saving list: There is no list to save", redError);
            return;
        }

        //get selected list
        int index = toDoListListView.getFocusModel().getFocusedIndex();

        //save the file and record success
        boolean success = SaveLoad.saveFile(file, Data.getMainList().get(index));

        if (success) {
            setHelpMessage("Success: List saved", greenSuccess);
        }
        else {
            setHelpMessage("Error saving list: Unknown", redError);
        }
    }

    @FXML
    protected void saveAllLists() {
        //check if there is any list to save
        if (Data.getMainList().size() < 1) {
            setHelpMessage("Error saving all lists: There is no list to save", redError);
            return;
        }

        //save the files and record success
        boolean success = SaveLoad.saveFile();

        if (success) {
            setHelpMessage("Success: All lists were saved", greenSuccess);
        }
        else {
            setHelpMessage("Error saving all lists: Invalid file content format", redError);
        }
    }

    @FXML
    protected void loadOneList() throws FileNotFoundException {
        //open the file chooser window and create a file variable for the selected file
        File file = fileChooser.showOpenDialog(new Stage());

        //load the file and record success
        boolean success = SaveLoad.loadFile(file);

        if (success) {
            displayLists();
            toDoListListView.getSelectionModel().selectLast();
            displayTasks();
            setHelpMessage("Success: List loaded", greenSuccess);
        }
        else {
            setHelpMessage("Error loading list: Invalid file type or content format", redError);
        }
    }

    @FXML
    protected void loadAllLists() throws FileNotFoundException {
        boolean success = SaveLoad.loadFile();

        if (success) {
            displayLists();
            toDoListListView.getSelectionModel().selectLast();
            displayTasks();
            setHelpMessage("Success: All lists in saveDirectory/ were loaded", greenSuccess);
        }
        else {
            setHelpMessage("Error loading all lists: Invalid file type or content format", redError);
        }
    }

}