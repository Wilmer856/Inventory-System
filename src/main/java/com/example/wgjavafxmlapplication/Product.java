package com.example.wgjavafxmlapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class that creates and stores product values. */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** @return the id*/
    public int getId() {
        return id;
    }

    /**@param id the id to set*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return the name*/
    public String getName() {
        return name;
    }

    /** @param name the name to set*/
    public void setName(String name) {
        this.name = name;
    }

    /** @return the price*/
    public double getPrice() {
        return price;
    }

    /** @param price the price to set*/
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return the stock*/
    public int getStock() {
        return stock;
    }

    /** @param stock the stock to set*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @return the min*/
    public int getMin() {
        return min;
    }

    /** @param min the min to set*/
    public void setMin(int min) {
        this.min = min;
    }

    /** @return the max*/
    public int getMax() {
        return max;
    }

    /** @param max the max to set*/
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds a part to the associated parts list of the product.
     @param part part to add */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** Deletes a part from the associated parts list of the product.
     @param selectedAssociatedPart Part to delete.
     @return true if part in list */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** @return associatedParts*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
