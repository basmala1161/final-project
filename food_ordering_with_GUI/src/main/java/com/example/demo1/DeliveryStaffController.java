package com.example.demo1;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaffController {

    @FXML
    private TableView<DeliveryStaff> staffTable; // Table to display staff
    @FXML
    private TableColumn<DeliveryStaff, String> firstNameColumn;
    @FXML
    private TableColumn<DeliveryStaff, String> lastNameColumn;
    @FXML
    private TableColumn<DeliveryStaff, String> locationColumn;
    @FXML
    private TableColumn<DeliveryStaff, Double> reviewColumn;
    @FXML
    private TableColumn<DeliveryStaff, DeliveryStaff.Status> statusColumn;

    @FXML
    private ListView<String> assignedOrdersList; // List to display assigned orders as raw text
    @FXML
    private Label messageLabel;

    private ArrayList<DeliveryStaff> staffList = DeliveryStaff.createStaffList();

    // Initialize the table with staff data
    @FXML
    public void initialize(DeliveryStaff loggedInStaff) {
        // Initialize table columns
        firstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        locationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        // Set up the status column to show enum values as strings
        statusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()));

        // Clear the staff table
        staffTable.getItems().clear();

        // Add only the logged-in staff to the table
        if (loggedInStaff != null) {
            staffTable.getItems().add(loggedInStaff);  // Add the logged-in staff only
        }

        // Automatically clear the assigned orders list when no staff is selected
        staffTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                assignedOrdersList.getItems().clear();
                messageLabel.setText("No staff selected.");
            }
        });

        // Optionally, you can select the logged-in staff in the table
        if (loggedInStaff != null) {
            staffTable.getSelectionModel().select(loggedInStaff);
        }
    }

    // Modify the DeliveryStaffController to make staffList accessible
    public ArrayList<DeliveryStaff> getStaffList() {
        return staffList;
    }

    private List<String> readAssignedOrdersFromFile(String staffFirstName) {
        List<String> lines = new ArrayList<>();
        File file = new File(staffFirstName + "_orders.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line); // Add each line to the list
                }
            } catch (IOException e) {
                System.err.println("Error reading assigned orders from file: " + e.getMessage());
            }
        }
        return lines;
    }




    // Show assigned orders for the selected staff member
    @FXML
    public void showAssignedOrders() {
        DeliveryStaff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            List<String> lines = readAssignedOrdersFromFile(selectedStaff.getFirstName());
            assignedOrdersList.getItems().clear(); // Clear the current list
            if (!lines.isEmpty()) {
                assignedOrdersList.getItems().addAll(lines); // Add all the raw lines
                messageLabel.setText("Assigned orders for " + selectedStaff.getFirstName() + " " + selectedStaff.getLastName() + " are shown.");
            } else {
                messageLabel.setText(selectedStaff.getFirstName() + " " + selectedStaff.getLastName() + " has no assigned orders.");
            }
        } else {
            messageLabel.setText("Please select a staff member to show assigned orders.");
        }
    }



}
