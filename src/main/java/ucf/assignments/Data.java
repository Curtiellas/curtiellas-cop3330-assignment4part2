/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
package ucf.assignments;

import java.util.ArrayList;

//the variable mainList holds all lists, and tasks therein, currently open in the GUI
class Data {
    private static ArrayList<ToDoList> mainList = new ArrayList<>();
    private static String displaySeparator = ", "; //when displaying desc, date, status; this is used to separate them

    public static ArrayList<ToDoList> getMainList() {
        return mainList;
    }

    public static String getDisplaySeparator() {
        return displaySeparator;
    }

    public static void setDisplaySeparator(String displaySeparator) {
        Data.displaySeparator = displaySeparator;
    }

    /************** Mirror Management * stored variables --> displayable variables **************/
    //returns a String[] array with the list titles, for displaying
    public static String[] getTitleStrings(ArrayList<ToDoList> mainList) {
        String[] titles = new String[mainList.size()];

        for (int a = 0; a < mainList.size(); a++) {
            titles[a] = mainList.get(a).getTitle();
        }
        return titles;
    }

    //returns a String[] array with ALL task values, for displaying
    public static String[] getTaskStrings(ArrayList<ToDoList> mainList, int index) {
        //format of each element in array: "description, due date, status"
        String[] values = new String[ mainList.get(index).getTaskList().size() ];

        for (int a = 0; a < mainList.get(index).getTaskList().size(); a++) {
            mainList.get(index).getTaskList().get(a).setPseudoIndex(a);
            values[a] = mainList.get(index).getTaskList().get(a).toStringClean();
        }
        return values;
    }

    //returns only INCOMPLETE tasks
    public static String[] getIncompleteTaskStrings(ArrayList<ToDoList> mainList, int index) {
        //format of each element in array: "description, due date, status"
        String[] values = new String[ mainList.get(index).getTaskList().size() ];

        //gathering the array of strings to display
        for (int a = 0; a < mainList.get(index).getTaskList().size(); a++) {
            if (mainList.get(index).getTaskList().get(a).getStatus().equals("Incomplete"))
                values[a] = mainList.get(index).getTaskList().get(a).toStringClean();
            else
                values[a] = "";
        }

        return values;
    }

    //returns only COMPLETE tasks
    public static String[] getCompleteTaskStrings(ArrayList<ToDoList> mainList, int index) {
        //format of each element in array: "description, due date, status"
        String[] values = new String[ mainList.get(index).getTaskList().size() ];

        for (int a = 0; a < mainList.get(index).getTaskList().size(); a++) {
            if (mainList.get(index).getTaskList().get(a).getStatus().equals("Complete"))
                values[a] = mainList.get(index).getTaskList().get(a).toStringClean();
            else
                values[a] = "";
        }
        return values;
    }

    //selection sort by due dates
    public static boolean sortByDate(int listIndex) {
        for (int a = 0; a < mainList.get(listIndex).getTaskList().size() - 1; a++) {
            int L = a;

            for (int b = a + 1; b < mainList.get(listIndex).getTaskList().size(); b++) {
                if (mainList.get(listIndex).getTaskList().get(b).getDueDate().isBefore(mainList.get(listIndex).getTaskList().get(L).getDueDate()))
                    L = b;
            }
            //replacement
            Task temp = mainList.get(listIndex).getTaskList().get(a);
            mainList.get(listIndex).getTaskList().set(a , mainList.get(listIndex).getTaskList().get(L));
            mainList.get(listIndex).getTaskList().set(L , temp);
        }
        return true;
    }

}
