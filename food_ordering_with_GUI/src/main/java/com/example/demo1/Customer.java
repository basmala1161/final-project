package com.example.demo1;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;


public class Customer extends Person {

    private String email;
    private String phone_number;

    private String password;


    public Alert_window alert=new Alert_window();



    @Override
    public void logIn() {
        Stage window=new Stage();
        GridPane grid=new GridPane();
        grid.setPadding(new Insets(4,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        //title
        Label title=new Label("Log In");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title,0,0);
        title.setAlignment(Pos.CENTER);

        //Email
        Label email_label=new Label("Email: ");
        GridPane.setConstraints(email_label,0,1);
        TextField Email=new TextField();
        Email.setPromptText("Enter you email");
        GridPane.setConstraints(Email,1,1);
        //password
        Label password_label=new Label("Password: ");
        GridPane.setConstraints(password_label,0,5);
        PasswordField Password=new PasswordField();
        Password.setPromptText("password");
        GridPane.setConstraints(Password,1,5);
        Button submit=new Button("Submit");
        GridPane.setConstraints(submit,0,10);
        Button btn_Register=new Button(" Don't have an account yet? Register");
        btn_Register.setOnAction(e->{
            try {
                Register();
                window.close();
            }catch (Exception msg)
            {
                msg.getMessage();
            }


        });
        GridPane.setConstraints(btn_Register,0,12);
        btn_Register.getStyleClass().add("button-white");

        grid.getChildren().addAll(title,email_label,Email,password_label,Password,submit,btn_Register);
        grid.setAlignment(Pos.CENTER);
        Scene page1=new Scene(grid,500,500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Log In");
        window.show();
        submit.setOnAction(e->{
            boolean found = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("Register.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] record = line.split("/");
                    if (record.length == 8) {
                        String storedEmail = record[2].trim();
                        String storedPassword = record[3].trim();
                        if (storedEmail.equals(Email.getText().trim()) && storedPassword.equals(Password.getText().trim())) {
                            System.out.println("Login successful");
                            System.out.println("Welcome, " + record[0] + "!");
                            found = true;
                            window.close();


                            Order order = new Order();
                            order.start();
                            break;
                        }
                    }
                }
                if (!found) {
                    alert.display("Login", "Invalid email or password. Please try again.");
                }
            } catch (IOException msg) {
                alert.display("Error", "An error occurred while accessing the file.");
                msg.printStackTrace();
            }
        });

    }
    public void Register()  throws IOException {

        Stage window=new Stage();

        GridPane grid=new GridPane();
        grid.setPadding(new Insets(4,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label title=new Label("Register");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title,0,0);
        title.setAlignment(Pos.CENTER);
        //First Name
        Label F_name_label=new Label("First Name: ");
        GridPane.setConstraints(F_name_label,0,3);
        TextField F_name=new TextField();
        F_name.setPromptText("Enter you first name");
        GridPane.setConstraints(F_name,1,3);

        //Last Name
        Label L_name_label=new Label("Last Name");
        GridPane.setConstraints(L_name_label,0,4);
        TextField L_name=new TextField();
        F_name.setPromptText("Enter you last name");
        GridPane.setConstraints(L_name,1,4);

        //Email
        Label email_label=new Label("Email");
        GridPane.setConstraints(email_label,0,5);
        TextField email=new TextField();
        email.setPromptText("Enter you email");
        email.setOnAction(e->{

            if(email.getText().indexOf('@')==-1){
                alert.display("Invalid Email","Email must contain ---@---");
            }
        });

        GridPane.setConstraints(email,1,5);
        //password
        Label password_label=new Label("Password: ");
        GridPane.setConstraints(password_label,0,6);
        PasswordField password=new PasswordField();
        password.setPromptText("password");
        GridPane.setConstraints(password,1,6);
        // Confirm password
        Label C_password_label=new Label("Confirm Password: ");
        GridPane.setConstraints(C_password_label,0,7);
        PasswordField C_password=new PasswordField();
        C_password.setPromptText("Confirm password");
        GridPane.setConstraints(C_password,1,7);
        C_password.setOnAction(e->{
            if(password.getText().equals(C_password.getText())){
                System.out.println("equals");
            }
            else {

                alert.display("confirm password","Invalid password");
            }
        });
        //phone number
        Label PhoneNumber_label=new Label("Phone number: ");
        GridPane.setConstraints(PhoneNumber_label,0,8);
        PasswordField PhoneNumber=new PasswordField();
        C_password.setPromptText("Phone number");
        GridPane.setConstraints(PhoneNumber,1,8);
        //Gender
        ComboBox comboBox1=new ComboBox<>();
        comboBox1.setPromptText("Gender");
        comboBox1.getItems().addAll(
                "Femal",
                "Male"
        );

        GridPane.setConstraints(comboBox1,0,13);
        //governorte
        ComboBox comboBox2=new ComboBox<>();
        comboBox2.setPromptText("Governorate");
        comboBox2.getItems().addAll(
                "Cairo",
                "Qalyubia",
                "Alexandria"
        );

        GridPane.setConstraints(comboBox2,1,13);
        //area
        ComboBox comboBox3=new ComboBox<>();
        comboBox3.setPromptText("Area");
        comboBox2.setOnAction(e->{
            comboBox3.getItems().clear();
            if(comboBox2.getValue()=="Cairo") {

                comboBox3.getItems().addAll(
                        "Abbasiya", "Nasr City", "Maadi"

                );
            }
            else if(comboBox2.getValue()=="Qalyubia"){
                comboBox3.getItems().addAll(
                        "Benha", "Shubra", "Kaliub"
                );
            }
            else if(comboBox2.getValue()=="Alexandria"){
                comboBox3.getItems().addAll(
                        "Montazah", "Roushdy", "Smouha"
                );
            }
        });

        GridPane.setConstraints(comboBox3,2,13);
        //submit
        Button submit=new Button("Submit");
        submit.setOnAction(e->{
            this.firstname=F_name.getText();
            this.password=password.getText();
            this.lastname=L_name.getText();
            this.email=email.getText();
            this.phone_number=PhoneNumber.getText();
            //writing in file

            try  {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Register.txt", true));
                writer.write(this.firstname+'/'+this.lastname+'/'+this.email+'/'+this.password+'/'+this.phone_number+'/'+comboBox1.getValue()+'/'+comboBox2.getValue()+'/'+comboBox3.getValue());
                writer.newLine();
                System.out.println("the data stored successfully");
                writer.close();
            } catch (IOException msg) {
                msg.getMessage();

            }

        });
        GridPane.setConstraints(submit,0,14);
        Button btn_login=new Button("Already have an account? Log In");
        btn_login.setOnAction(e->{
            logIn();
            window.close();
        });
        GridPane.setConstraints(btn_login,0,15);
        btn_login.getStyleClass().add("button-white");

        grid.getChildren().addAll(title,F_name_label,F_name,L_name_label,L_name,email_label,email,password_label,password,C_password_label,C_password,PhoneNumber_label,PhoneNumber,comboBox1,comboBox2,comboBox3,submit,btn_login);
        grid.setAlignment(Pos.CENTER);
        Scene page1=new Scene(grid,500,500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Register");
        window.show();
    }
}