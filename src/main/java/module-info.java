/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 luis curtiellas
 */
module ucf.assignments {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ucf.assignments to javafx.fxml;
    exports ucf.assignments;
}