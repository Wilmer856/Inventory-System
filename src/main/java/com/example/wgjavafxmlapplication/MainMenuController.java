package com.example.wgjavafxmlapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/** Controller class that provides functionality and displays the initial UI, which consists of the list of parts/products.
 RUNTIME ERROR: There was a runtime error when clicking on the delete part button
 while not highlighting anything that results in selecting a null item.
 I provided a way on how to fix the issue in the onPartDeleteBtnClick() method. */
public class MainMenuController {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Integer> inventoryLvlCol;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Double> priceCostPerUnitCol;

    @FXML
    private TextField lookUpPartTxt;

    @FXML
    private TextField lookUpProductTxt;

    @FXML
    private TableColumn<Product, Integer> prodInvLevelCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCostPerUnitCol;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableView<Product> productsTableView;

    /** Takes you to the 'Add Part' screen.
     Listens for a button click event that takes you to the 'Add Part' screen.
     @param event The event the program is listening for. */
    @FXML
    void onPartAddBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        sceneChanger("AddPartForm.fxml");

    }

    /** Delete part.
     Listens for a button click event that will allow you to delete a part from the Parts Table.
     @param event The event the program is listening for. */
    @FXML
    void onPartDeleteBtnClick(ActionEvent event) {

//        RUNTIME ERROR: Example of catching the runtime error from occurring when selecting a null value
//                       when trying to delete a part.
        if(partsTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "No item was selected. Please select an item.");
            alert1.show();
        } else {
            int id = partsTableView.getSelectionModel().getSelectedItem().getId();
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part from the Parts list. Select OK to confirm.");
            Optional<ButtonType> result = alert2.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                Part part = Inventory.lookupPart(id);
                Inventory.deletePart(part);
                Inventory.orderParts();
                partsTableView.setItems(Inventory.getAllParts());
            }
        }

    }

    /** Takes you to the 'Modify Part' screen.
     Listens for a button click event that takes you to the 'Modify Part' screen.
     @param event The event the program is listening for. */
    @FXML
    void onPartModifyBtnClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartForm.fxml"));
        loader.load();

        ModifyPartController MPController = loader.getController();
        MPController.sendDetails(partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Takes you to the 'Add Product' screen.
     Listens for a button click event that takes you to the 'Add Product' screen.
     @param event The event the program is listening for. */
    @FXML
    void onProductAddBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        sceneChanger("addProductForm.fxml");
    }

    /** Delete product.
     Listens for a button click event that will allow you to delete a product from the Products Table.
     @param event The event the program is listening for. */
    @FXML
    void onProductDeleteBtnClick(ActionEvent event) {
        int id = productsTableView.getSelectionModel().getSelectedItem().getId();
        Product product = Inventory.lookupProduct(id);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the product from the Products list. Select OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if(product.getAllAssociatedParts().size() == 0){
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(product);
                Inventory.orderProducts();
                productsTableView.setItems(Inventory.getAllProducts());
            }
        } else {
            if(result.isPresent() && result.get() == ButtonType.OK){
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Product was not deleted. Remove all of its associated parts and try again.");
                alert1.showAndWait();
            }

        }

    }

    /** Takes you to the 'Modify Product' screen.
     Listens for a button click event that takes you to the 'Modify Product' screen.
     @param event The event the program is listening for. */
    @FXML
    void onProductModifyBtnClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProductForm.fxml"));
        loader.load();

        ModifyProductController MPController = loader.getController();
        MPController.sendDetails(productsTableView.getSelectionModel().getSelectedItem());
        MPController.sendSelectedProduct(productsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Exits the application. */
    @FXML
    void onExitClick(ActionEvent event) {
        System.exit(0);
    }

    /** Loads and switches screen.
     Takes in the name of a file and loads the screen.
     @param fileName Name of file to load. */
    private void sceneChanger(String fileName) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(fileName));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Searches for parts.
     Listens for an event by searching for a part in the search text field and displays matching searches in the Parts Table.
     @param event The event the program is listening for. */
    @FXML
    void onSearchForPart(ActionEvent event) {

        try{
            int id = Integer.parseInt(lookUpPartTxt.getText()) - 1;
            partsTableView.getSelectionModel().select(id);

        }catch(NumberFormatException e){

            String name = lookUpPartTxt.getText().toLowerCase();
            partsTableView.setItems(Inventory.lookupPart(name));
        }
    }

    /** Searches for products.
     Listens for an event by searching for a product in the search text field and displays matching searches in the Products Table.
     @param event The event the program is listening for. */
    @FXML
    void onSearchForProduct(ActionEvent event) {

        try{
            int id = Integer.parseInt(lookUpProductTxt.getText()) - 1;
            productsTableView.getSelectionModel().select(id);

        }catch(NumberFormatException e){

            String name = lookUpProductTxt.getText().toLowerCase();
            productsTableView.setItems(Inventory.lookupProduct(name));
        }
    }


    /** Initializes the tables and its columns. */
    public void initialize(){

        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productsTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}