##Welcome to To-Do List Maker
This JavaFX program allows the user to create a number of to-do lists, each of which can hold many tasks. Each task has a Description, a Due Date, and a Completion Status.

Made by novice Luis Curtiellas. For UCF COP3330 Fall 2021 Assignment 4.
###Guide
####Creating and Editing Lists
1. Add a List: Under **List Creation**, enter a title into the text field and press the **Add List** button.
2. Edit List Title: Under **List Creation**, enter a new title, then press **Edit** > **Edit List Title** in the top menu bar.
3. Delete a List: Press **Edit** > **Delete Selected List** in the top menu bar.
4. Add a Task: Under **Task Creation**, enter a description, a due date (_YYYY-MM-DD_), and select a completion status (_Incomplete_ by default), then press the **Add Task** button.
5. Edit a Task: Under **Task Creation**, enter one new value, then press **Edit** > **Edit Task...** in the top menu bar. To change completion status, the new value does not have to be entered.
6. Delete a Task: Press **Edit** > **Delete Task** in the top menu bar.
7. Delete all Tasks in a List: Press **Edit** > **Delete All Tasks in List** in the top menu bar.
8. Sort by Due Date: Press **Edit** > **Sort By Due Date** in the top menu bar.
9. Filter by Status: Under **Task Filter** select one of the three options: _All_, _Incomplete_, _Complete_. Only tasks with the selected status will be visible. This does not remove any tasks; only hides them.

####Saving and Loading Lists
1. Load a List: Press **File** > **Load One File** in the top menu bar, then select a file in the explorer window and click **Open**.
2. Load all Lists: Press **File** > **Load All Files** in the top menu bar. All lists in the default folder will be loaded.
3. Save a List: Press **File** > **Save Selected List** in the top menu bar, then set the name of the file and click **Save**.
4. Save all lists: Press **File** > **Save All Open Lists** in the top menu bar. All open lists will be saved in the default folder, the list titles will be the file names.

###Possible Problems
* If a list or task is added or edited without filling all relevant fields, it will not be added/edited.
* If a list or task is edited, deleted, or saved while no list/task is selected, the first list/task will be edited/deleted/saved.
* If the **Save All Open Lists** option is used, and one or more of the open lists have the same name/s as files in the save folder, those saved files will be replaced.
* Commas "," cannot be entered in a task description because they are used to separate task elements when parsing a list in a save file.
