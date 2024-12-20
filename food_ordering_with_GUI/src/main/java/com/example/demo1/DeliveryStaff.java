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


public class DeliveryStaff extends Person implements Serializable {

    Alert_window alert = new Alert_window();


    private String firstName;
    private String lastName;
    private String location;

    public enum Status {FREE, WORK;}
    private Status status;
    public ArrayList<Order> assignedOrders;


    public DeliveryStaff(String firstName, String lastName, String location, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;

        this.status = status;
        this.assignedOrders = new ArrayList<>();
    }


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


        Label title = new Label("Log In As Delivery");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title, 0, 0);
        title.setAlignment(Pos.CENTER);


        Label email_label = new Label("Email: ");
        GridPane.setConstraints(email_label, 0, 1);
        TextField Email = new TextField();
        Email.setPromptText("Enter your email");
        GridPane.setConstraints(Email, 1, 1);


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
                boolean found = false;
                while ((line = reader.readLine()) != null) {

                    line = line.trim();
                    String[] record = line.split("/");


                    if (record.length >= 4) {
                        String storedEmail = record[2].trim();
                        String storedPassword = record[3].trim();
                        if (storedEmail.equals(Email.getText().trim()) && storedPassword.equals(Password.getText().trim())) {
                            System.out.println("Login successful");
                            System.out.println("Welcome " + record[0] + " " + record[1]);
                            found = true;
                            window.close();


                            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeliveryStaffGUI.fxml"));
                            AnchorPane root = loader.load();


                            DeliveryStaffController controller = loader.getController();
                            DeliveryStaff loggedInStaff = new DeliveryStaff(record[0], record[1], record[2], DeliveryStaff.Status.FREE);
                            controller.initialize(loggedInStaff);


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


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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


    @Override
    public String toString() {
        return "DeliveryStaff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +

                '}';
    }
}
