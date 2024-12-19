package com.example.demo1;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaff extends Person implements Serializable {

    Alert_window alert = new Alert_window();

    // DeliveryStaff fields
    private String firstName;
    private String lastName;
    private String location;

    public enum Status {FREE, WORK;}
    private Status status;
    public ArrayList<Order> assignedOrders; // Initialized in constructor

    // Constructor
    public DeliveryStaff(String firstName, String lastName, String location, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;

        this.status = status;
        this.assignedOrders = new ArrayList<>(); // Initialize assignedOrders list
    }

    // Method to initialize staff list
    public static ArrayList<DeliveryStaff> createStaffList() {
        ArrayList<DeliveryStaff> staffList = new ArrayList<>();
        staffList.add(new DeliveryStaff("Muhamed", "Walied", "Cairo, Nasr City", Status.FREE));
        staffList.add(new DeliveryStaff("Abdullah", "Muhamed", "Giza, Dokki", Status.FREE));
        staffList.add(new DeliveryStaff("Youseef", "Muhsen", "Cairo, Heliopolis", Status.FREE));
        staffList.add(new DeliveryStaff("Amr", "Khaled", "Giza, Mohandessin", Status.FREE));
        staffList.add(new DeliveryStaff("Ibrahim", "Salam", "Cairo, Zamalek", Status.FREE));
        staffList.add(new DeliveryStaff("Ahmed", "Ashraf", "Cairo, New Cairo", Status.FREE));
        staffList.add(new DeliveryStaff("Mido", "Sameh", "Alexandria, Corniche", Status.FREE));
        return staffList;
    }

    @Override
    public void logIn() {
        Stage window = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(4, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // title
        Label title = new Label("Log In As Delivery");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title, 0, 0);
        title.setAlignment(Pos.CENTER);

        // Email
        Label email_label = new Label("Email: ");
        GridPane.setConstraints(email_label, 0, 1);
        TextField Email = new TextField();
        Email.setPromptText("Enter your email");
        GridPane.setConstraints(Email, 1, 1);

        // password
        Label password_label = new Label("Password: ");
        GridPane.setConstraints(password_label, 0, 5);
        PasswordField Password = new PasswordField();
        Password.setPromptText("password");
        GridPane.setConstraints(Password, 1, 5);

        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("deliverystaff.txt"));
                String line;
                boolean found = false;  // Flag to track if login is successful
                while ((line = reader.readLine()) != null) {
                    // Trim whitespace and split the line by '/'
                    line = line.trim();
                    String[] record = line.split("/");

                    // Ensure that the record has enough parts (at least 4 for email and password)
                    if (record.length >= 4) {
                        String storedEmail = record[2].trim();  // Trim to remove leading/trailing spaces
                        String storedPassword = record[3].trim();  // Trim to remove leading/trailing spaces
                        if (storedEmail.equals(Email.getText().trim()) && storedPassword.equals(Password.getText().trim())) {
                            System.out.println("Login successful");
                            System.out.println("Welcome " + record[0] + " " + record[1]);
                            found = true;
                            window.close();

                            // After successful login, load the FXML layout
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeliveryStaffGUI.fxml"));
                            AnchorPane root = loader.load();

                            // Get the controller and pass the logged-in staff to it
                            DeliveryStaffController controller = loader.getController();
                            DeliveryStaff loggedInStaff = new DeliveryStaff(record[0], record[1], record[2], DeliveryStaff.Status.FREE);
                            controller.initialize(loggedInStaff);

                            // Create a new stage and set the scene to the loaded FXML
                            Stage staffWindow = new Stage();
                            staffWindow.setScene(new Scene(root));
                            staffWindow.setTitle("Delivery Staff Dashboard");
                            staffWindow.show();

                            break;
                        }
                    }
                }

                if (!found) {
                    System.out.println("Invalid login information.");
                    alert.display("Log in", "Invalid email or password.");
                }

                reader.close();
            } catch (IOException msg) {
                msg.getMessage();
            }
        });

        GridPane.setConstraints(submit, 0, 10);

        Button btn_close = new Button("Close");
        btn_close.setOnAction(e -> {
            window.close();
        });
        GridPane.setConstraints(btn_close, 0, 12);
        btn_close.getStyleClass().add("button-white");

        grid.getChildren().addAll(title, email_label, Email, password_label, Password, submit, btn_close);
        grid.setAlignment(Pos.CENTER);
        Scene page1 = new Scene(grid, 500, 500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("HELLO PERSON!");
        window.show();
    }


    // Getter and Setter methods for DeliveryStaff class
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }




    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Assign orders based on location
    public String getDeliveryStatus(Order order, DeliveryStaff deliveryStaff) {
        if (order.getOrderLocation().equalsIgnoreCase(this.location)) {
            if (deliveryStaff.getStatus().equals(Status.FREE)) {
                return "The order assigned successfully";
            } else if (deliveryStaff.getStatus().equals(Status.WORK)) {
                return "Delivery staff is currently busy";
            } else {
                return "Invalid Delivery Staff Status: " + deliveryStaff.getStatus();
            }
        } else {
            return "Order is not in your location";
        }
    }

    // Assign orders to staff if location matches
    public void assignOrdersToStaff(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("hiiiiiiiiiiiiiiiiiiiiii");
            this.assignedOrders.add(order);
            System.out.println("Order assigned to " + firstName + " " + lastName);

        }
    }

    // View assigned orders
    public void viewAssignedOrders() {
        System.out.println("Assigned Orders for " + firstName + " " + lastName + ":");
        if (assignedOrders.isEmpty()) {
            System.out.println("No orders assigned.");
        } else {
            assignedOrders.forEach(order -> {
                System.out.println(order);
                System.out.println("*==========================*");
            });
        }
    }

    // toString Method
    @Override
    public String toString() {
        return "DeliveryStaff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +

                '}';
    }

    // Method to write DeliveryStaff object to file
    public void writeToFile(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            System.out.println("DeliveryStaff object saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing object to file: " + e.getMessage());
        }
    }

    // Method to read DeliveryStaff object from file
    public static DeliveryStaff readFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (DeliveryStaff) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading object from file: " + e.getMessage());
        }
        return null;
    }

    // Assign orders from a file
    public void assignOrdersFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            ArrayList<Order> orders = (ArrayList<Order>) in.readObject();
            for (Order order : orders) {
                if (order.getOrderLocation().equalsIgnoreCase(this.location) && status == Status.FREE) {
                    this.assignedOrders.add(order);
                } else if (status == Status.WORK) {
                    System.out.println("Delivery cannot be assigned because the worker is currently busy.");
                }
            }
            System.out.println("Orders assigned from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading orders from file: " + e.getMessage());
        }
    }
}
