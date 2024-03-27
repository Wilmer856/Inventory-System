package com.example.wgjavafxmlapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/** Class that stores all the parts and products*/
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> prodAssociatedParts = FXCollections.observableArrayList();

    /** Adds part to the allParts list.
     @param newPart part to add */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Adds product to the allProducts list.
     @param newProduct product to add */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Find a part in the allParts list.
     @param partId ID of part to find.
     @return the part*/
    public static Part lookupPart(int partId){

        for(Part part: Inventory.getAllParts()){
            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /** Find a product in the allProducts list.
     @param productId ID of part to find.
     @return the product*/
    public static Product lookupProduct(int productId){

        for(Product product: Inventory.getAllProducts()){
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /** Search for parts by name.
     @param partName full or partial name of part
     @return list of parts*/
    public static ObservableList<Part> lookupPart(String partName){

        if(!(Inventory.filteredParts.isEmpty())){
            filteredParts.clear();
        }

        for(Part part: Inventory.getAllParts()){
            if(part.getName().toLowerCase().contains(partName)){
                filteredParts.add(part);
            }
        }

        if(Inventory.filteredParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("No parts have been found!");
            alert.show();
            return allParts;
        }
        else {
            return filteredParts;
        }

    }

    /** Search for products by name.
     @param productName full or partial name of product
     @return list of products*/
    public static ObservableList<Product> lookupProduct(String productName){

        if(!(Inventory.filteredProducts.isEmpty())){
            filteredProducts.clear();
        }

        for(Product product: Inventory.getAllProducts()){
            if(product.getName().toLowerCase().contains(productName)){
                filteredProducts.add(product);
            }
        }

        if(Inventory.filteredProducts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("No products have been found!");
            alert.show();
            return allProducts;
        }
        else {
            return filteredProducts;
        }
    }

    /** Replace a part in the list.
     @param index index to place part
     @param selectedPart part to insert*/
    public static void updatePart(int index, Part selectedPart) {
        int idx = -1;

        for(Part p: Inventory.getAllParts()) {
            idx++;

            if(p.getId() == index){
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }

    /** Replace a product in the list.
     @param index index to place product.
     @param newProduct product to insert*/
    public static void updateProduct(int index, Product newProduct) {
        int idx = -1;

        for(Product p: Inventory.getAllProducts()) {
            idx++;

            if(p.getId() == index){
                Inventory.getAllProducts().set(index, newProduct);
            }
        }
    }

    /** Deletes a part from the list of all parts.
     @param selectedPart Part to delete.
     @return true if part in list */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /** Deletes a product from the list of all products.
     @param selectedProduct Product to delete.
     @return true if product in list */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** @return allParts*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** @return allProducts*/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Adds part to the temporary associated parts for products list.
     @param part product to add */
    public static void addProdAssociatedParts(Part part){
        prodAssociatedParts.add(part);
    }

    /** @return prodAssociatedParts*/
    public static ObservableList<Part> getAllProdAssociatedParts(){
        return prodAssociatedParts;
    }

    /** Deletes a part from the list of temporary associated parts for products list.
     @param part part to delete.*/
    public static void removeProdAssociatedPart(Part part){
        prodAssociatedParts.remove(part);
    }

    /** Re-order the ID values for parts to make them unique.*/
    public static void orderParts(){
        int index = 0;
        for(Part part : allParts){
            part.setId(index + 1);
            index++;
        }
    }

    /** Re-order the ID values for products to make them unique.*/
    public static void orderProducts() {
        int index = 0;
        for(Product product : allProducts){
            product.setId(index + 1);
            index++;
        }
    }

}
