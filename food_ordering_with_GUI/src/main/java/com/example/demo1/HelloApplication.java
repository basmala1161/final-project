package com.example.demo1;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    Stage window;
    Scene page1, page2;

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setOnCloseRequest(e -> {
            e.consume();
            close_window();
        });


        Button button1 = new Button("Admin");
        Button button2 = new Button("User");
        Button button3 = new Button("DeliveryStaff");



        button1.getStyleClass().add("button-Background");
        button2.getStyleClass().add("button-Background");
        button3.getStyleClass().add("button-Background");



        button1.setOnAction(e -> {
            Person admin = new Admin();
            admin.logIn();
        });


        button2.setOnAction(e -> {
            Person customer = new Customer();
            customer.logIn();
        });


        button3.setOnAction(e -> {
            Person delivery = new DeliveryStaff("potato","so","giza", DeliveryStaff.Status.FREE);
            delivery.logIn();
        });


        Label label = new Label("Welcome ");
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);


        VBox center = new VBox(30);
        center.getChildren().addAll(button1, button2, button3);
        center.setAlignment(Pos.CENTER);


        BorderPane all = new BorderPane();
        all.setCenter(center);
        all.setMargin(center, new Insets(0, 0, 10, 0));
        all.setTop(vBox);
        all.getStyleClass().add("background");


        page1 = new Scene(all, 500, 500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Hello Roqia!");
        window.show();
    }





    private void close_window() {
        Confirm c = new Confirm();
        boolean confirm = c.display("Confirm", "Do you want to exit this program?");
        if (confirm)
            window.close();
    }

    public static void main(String[] args) {
        launch();
    }
}
