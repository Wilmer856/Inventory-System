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

/** Controller class that allows you to add products and provides functionality to the 'Add Product' screen. */
public class AddProductController {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Double> costPerUnitCol;

    @FXML
    private TableColumn<Part, Integer> invLevelCol;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableView<Part> prodPartsTable;

    @FXML
    private TableColumn<Part, Integer> prodAddedIdCol;

    @FXML
    private TableColumn<Part, Integer> prodAddedInvCol;

    @FXML
    private TableColumn<Part, String> prodAddedNameCol;

    @FXML
    private TableView<Part> prodAddedPartsTable;

    @FXML
    private TableColumn<Part, Double> prodAddedPriceCol;

    @FXML
    private TextField prodPriceTxt;

    @FXML
    private TextField prodInvTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField searchForPartsTxt;


    /** Adds associated part.
      Method that adds associated part to its table.
      @param event The event the program is listening for. */
    @FXML
    void onProdAddBtn(ActionEvent event) {
        int id = prodPartsTable.getSelectionModel().getSelectedItem().getId();

        Part part = Inventory.lookupPart(id);
        Inventory.addProdAssociatedParts(part);
        prodAddedPartsTable.setItems(Inventory.getAllProdAssociatedParts());

    }

    /** Saves a product.
     Listens for a button click event that saves the provided information in the text fields and parts in the Associated Parts table, and takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onProdSaveBtn(ActionEvent event) throws IOException{
        String error = "";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        try {
            int id = Inventory.getAllProducts().size() + 1;
            String name = prodNameTxt.getText();
            if(name.isBlank()){
                alert.setContentText("Name is blank");
                alert.show();
                return;
            }
            error = "Inv";
            int stock = Integer.parseInt(prodInvTxt.getText());
            error = "Price";
            double price = Double.parseDouble(prodPriceTxt.getText());
            error = "Min";
            int min = Integer.parseInt(prodMinTxt.getText());
            error = "Max";
            int max = Integer.parseInt(prodMaxTxt.getText());

            if((stock >= min && stock <= max)){
                Product product = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(product);
                for(Part part: Inventory.getAllProdAssociatedParts()){
                    product.addAssociatedPart(part);
                }
                Inventory.getAllProdAssociatedParts().clear();

                sceneChanger(event);
            }
            else {
                if(min > max){
                    alert.setContentText("Min should be less than max!");
                } else {
                    alert.setContentText("Inv should be between min and max!");
                }
                alert.showAndWait();
            }


        } catch(NumberFormatException e) {
            alert.setContentText("Problem in the "+ error + " text field. Please enter a valid value!");
            alert.showAndWait();
        }
    }

    /** Searches for parts.
      Listens for an event by searching for a part in the search text field and displays matching searches in the Parts Table.
      @param event The event the program is listening for. */
    @FXML
    void onSearchForPart(ActionEvent event) {

        try{
            int id = Integer.parseInt(searchForPartsTxt.getText()) - 1;
            prodPartsTable.getSelectionModel().select(id);

        }catch(NumberFormatException e){

            String name = searchForPartsTxt.getText().toLowerCase();
            prodPartsTable.setItems(Inventory.lookupPart(name));
        }

    }

    /** Removes Associated Part.
     Removes the highlighted part in the Associated Parts Table by listening for a button click event.
     @param event The event the program is listening for. */
    @FXML
    void onRemovePartBtn(ActionEvent event) {
        int id = prodAddedPartsTable.getSelectionModel().getSelectedItem().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part from the Associated Parts list. Select OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part part = Inventory.lookupPart(id);
            Inventory.removeProdAssociatedPart(part);
            prodAddedPartsTable.setItems(Inventory.getAllProdAssociatedParts());
        }
    }

    /** Takes you back to the Main Menu.
     Listens for a button click event that takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onCancelProdBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        sceneChanger(event);
    }


    /** Loads and switches screen.
     Takes in an event and uses its source to switch the screen back to the Main Menu.
     @param event Source of the event. */
    private void sceneChanger(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Initializes the tables and its columns. */
    public void initialize(){

        prodPartsTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        costPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        prodAddedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodAddedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodAddedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodAddedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }
}