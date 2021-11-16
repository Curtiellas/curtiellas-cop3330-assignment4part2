package ucf.assignments;

import java.time.LocalDate;

class testableController {
    public static void addList1(ToDoList list) {
        //adding created list to our 'to-do list' list
        Data.getMainList().add(list);
    }

    public static void removeList1(int index) {
        //remove the list at the index
        Data.getMainList().remove(index);
    }

    public static void editListTitle1(int index, String title) {
        //edit the title at index selected
        Data.getMainList().get(index).setTitle(title);
    }

    public static void addTask1(int index, Task task) {
        //adding created task to our list
        Data.getMainList().get(index).getTaskList().add(task);
    }

    public static void removeTask1(int indexList, int indexTask) {
        //remove the object at index selected
        Data.getMainList().get(indexList).getTaskList().remove(indexTask);
    }

    public static void editDescription1(int indexList, int indexTask, String desc) {
        //edit mainList.taskList.description at index selected
        Data.getMainList().get(indexList).getTaskList().get(indexTask).setDescription(desc);
    }

    public static void editDate1(int indexList, int indexTask, LocalDate time) {
        //edit mainList.taskList.dueDate at index selected
        Data.getMainList().get(indexList).getTaskList().get(indexTask).setDueDate(time);
    }

    public static void editStatus1(int indexList, int indexTask) {
        //edit mainList.taskList.status at index selected to the other value
        if (Data.getMainList().get(indexList).getTaskList().get(indexTask).getStatus().equals("Complete"))
            Data.getMainList().get(indexList).getTaskList().get(indexTask).setStatus("Incomplete");
        else
            Data.getMainList().get(indexList).getTaskList().get(indexTask).setStatus("Complete");
    }
}