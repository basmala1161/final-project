package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RestaurantReviewController {

    @FXML
    private Label restaurantNameLabel;
    @FXML
    private Label restaurantCategoryLabel;
    @FXML
    private Label restaurantAddressLabel;

    @FXML
    private TextField reviewerNameField;

    @FXML
    private ComboBox<Integer> ratingComboBox;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private TextArea reviewsDisplayArea;

    private RestaurantWithReviews restaurant;


    public void initialize() {

        restaurant = new RestaurantWithReviews("Sample", "123 Street", "Italian");

        try {

            restaurant.loadReviewsFromFile("reviews.dat");
        } catch (Exception e) {
            showAlert("Error", "Could not load reviews. Starting with an empty list.");
        }


        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        ratingComboBox.getSelectionModel().selectFirst();


        updateReviewDisplay();


        setRestaurant(restaurant,"");
    }


    @FXML
    private void handleAddReview() {
        String reviewerName = reviewerNameField.getText();
        Integer rating = ratingComboBox.getValue();
        String comment = commentTextArea.getText();


        if (reviewerName.isEmpty() || comment.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {

            restaurant.addReview(reviewerName, rating, comment, "reviews.dat");
            updateReviewDisplay();
            reviewerNameField.clear();
            commentTextArea.clear();
            ratingComboBox.getSelectionModel().selectFirst();
            showAlert("Success", "Review added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Could not add review. Please try again.");
        }
    }




    private void updateReviewDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Review review : restaurant.getReviews()) {
            displayText.append(review.toString()).append("\n*=======================*\n");
        }
        reviewsDisplayArea.setText(displayText.toString());
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setRestaurant(Object restaurant,String name) {
        Restaurant restaurantObj = (Restaurant) restaurant;

        restaurantNameLabel.setText(name);
        restaurantCategoryLabel.setText("Category: " + restaurantObj.getCategory());
        restaurantAddressLabel.setText("Address: " + restaurantObj.getLocation());

    }
}
