package com.example.demo1;
import java.util.ArrayList;
public class RestaurantWithReviews extends Restaurant {
    private ArrayList<Review> reviews;

    public RestaurantWithReviews(String name, String location, String category) {
        super(name, location, category);
        this.reviews = new ArrayList<>();
    }

    public void addReview(String reviewerName, int rating, String comment, String filename) {
        Review review = new Review(reviewerName, rating, comment);
        this.reviews.add(review);
        saveReviewsToFile(filename);
    }

    public void saveReviewsToFile(String filename) {

        Review.saveReviewsToFile(this.reviews, filename);
    }

    public void loadReviewsFromFile(String filename) {

        this.reviews = Review.loadReviewsFromFile(filename);
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }


}
