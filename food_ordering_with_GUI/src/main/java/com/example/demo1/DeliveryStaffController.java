package com.example.demo1;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaffController {

    @FXML
    private TableView<DeliveryStaff> staffTable;
    @FXML
    private TableColumn<DeliveryStaff, String> firstNameColumn;
    @FXML
    private TableColumn<DeliveryStaff, String> lastNameColumn;
    @FXML
    private TableColumn<DeliveryStaff, String> locationColumn;
    @FXML
    private TableColumn<DeliveryStaff, DeliveryStaff.Status> statusColumn;

    @FXML
    private ListView<String> assignedOrdersList;
    @FXML
    private Label messageLabel;

    private ArrayList<DeliveryStaff> staffList = DeliveryStaff.createStaffList();


    @FXML
    public void initialize(DeliveryStaff loggedInStaff) {

        firstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        locationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));


        statusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()));


        staffTable.getItems().clear();


        if (loggedInStaff != null) {
            staffTable.getItems().add(loggedInStaff);
        }


        staffTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                assignedOrdersList.getItems().clear();
                messageLabel.setText("No staff selected.");
            }
        });


        if (loggedInStaff != null) {
            staffTable.getSelectionModel().select(loggedInStaff);
        }
    }


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
                    lines.add(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading assigned orders from file: " + e.getMessage());
            }
        }
        return lines;
    }





    @FXML
    public void showAssignedOrders() {
        DeliveryStaff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            List<String> lines = readAssignedOrdersFromFile(selectedStaff.getFirstName());
            assignedOrdersList.getItems().clear();
            if (!lines.isEmpty()) {
                assignedOrdersList.getItems().addAll(lines);
                messageLabel.setText("Assigned orders for " + selectedStaff.getFirstName() + " " + selectedStaff.getLastName() + " are shown.");
            } else {
                messageLabel.setText(selectedStaff.getFirstName() + " " + selectedStaff.getLastName() + " has no assigned orders.");
            }
        } else {
            messageLabel.setText("Please select a staff member to show assigned orders.");
        }
    }



}
