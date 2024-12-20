package com.example.demo1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class AdminController {

    private ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    @FXML
    private ListView<Restaurant> restaurantListView;

    @FXML
    private TextField restaurantNameTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    public void initialize() {
        loadRestaurantsFromFile();
        restaurantListView.setItems(restaurantList);


        restaurantListView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });


        restaurantListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                restaurantNameTextField.setText(newValue.getName());
                locationTextField.setText(newValue.getLocation());
                categoryTextField.setText(newValue.getCategory());
            }
        });
    }


    @FXML
    public void addRestaurant() {
        String name = restaurantNameTextField.getText().trim();
        String location = locationTextField.getText().trim();
        String category = categoryTextField.getText().trim();

        if (name.isEmpty() || location.isEmpty() || category.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        Restaurant newRestaurant = new Restaurant(name, location, category);
        restaurantList.add(newRestaurant);

        saveRestaurantsToFile();
        clearInputFields();
    }

    @FXML
    public void removeRestaurant() {
        Restaurant selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();

        if (selectedRestaurant != null) {
            restaurantList.remove(selectedRestaurant);
            saveRestaurantsToFile();
        } else {
            showAlert("Error", "Please select a restaurant to remove.");
        }
    }

    @FXML
    private void updateRestaurant() {

        Restaurant selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();

        if (selectedRestaurant != null) {

            String name = restaurantNameTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String category = categoryTextField.getText().trim();


            if (!name.isEmpty() && !location.isEmpty() && !category.isEmpty()) {

                selectedRestaurant.setName(name);
                selectedRestaurant.setLocation(location);
                selectedRestaurant.setCategory(category);


                restaurantListView.refresh();


                saveRestaurantsToFile();


                clearInputFields();
            } else {
                showAlert("Error", "All fields must be filled out to update.");
            }
        } else {
            showAlert("Error", "Please select a restaurant to update.");
        }
    }




    private void clearInputFields() {
        restaurantNameTextField.clear();
        locationTextField.clear();
        categoryTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveRestaurantsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Restaurant.txt"))) {

            for (Restaurant restaurant : restaurantList) {
                writer.write(restaurant.getName() + "," + restaurant.getLocation() + "," + restaurant.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Could not save restaurants to file.");
        }
    }


    private void loadRestaurantsFromFile() {
        restaurantList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("Restaurant.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String location = parts[1].trim();
                    String category = parts[2].trim();
                    restaurantList.add(new Restaurant(name, location, category));
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Could not load restaurants from file.");
        }
    }
}
