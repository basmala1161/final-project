package com.example.demo1;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String type;
    private double price;


    public Food(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }


    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + price + " EGP";
    }
}