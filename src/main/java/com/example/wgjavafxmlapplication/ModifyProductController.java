package com.example.wgjavafxmlapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/** Controller class that allows you to modify existing products and provides functionality to the 'Modify Product' screen. */
public class ModifyProductController {

    Stage stage;
    Parent scene;

    Product productSelected;
    ObservableList<Part> modifiedProducts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> modAssocInvCol;

    @FXML
    private TableColumn<Part, Integer> modAssocPartId;

    @FXML
    private TableColumn<Part, String> modAssocPartNameCol;

    @FXML
    private TableView<Part> modAssocPartsTable;

    @FXML
    private TableColumn<Part, Double> modAssocPriceCol;

    @FXML
    private TableColumn<Part, Integer> modInvCol;

    @FXML
    private TableColumn<Part, Integer> modPartIdCol;

    @FXML
    private TableView<Part> modPartsTable;

    @FXML
    private TableColumn<Part, Double> modPriceCol;

    @FXML
    private TableColumn<Part, String> modPartNameCol;

    @FXML
    private TextField modProdIdTxt;

    @FXML
    private TextField modProdInvTxt;

    @FXML
    private TextField modProdMaxTxt;

    @FXML
    private TextField modProdMinTxt;

    @FXML
    private TextField modProdNameTxt;

    @FXML
    private TextField modProdPriceTxt;

    @FXML
    private TextField lookUpPartsTxt;

    /** Takes you back to the Main Menu.
     Listens for a button click event that takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onCancelProdBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        sceneChanger();
    }

    /** Removes Associated Part.
     Removes the highlighted part in the Associated Parts Table by listening for a button click event.
     @param event The event the program is listening for. */
    @FXML
    void onRemoveAssocPartClick(ActionEvent event) {
        int id = modAssocPartsTable.getSelectionModel().getSelectedItem().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part from the Associated Parts list. Select OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part part = Inventory.lookupPart(id);
            modifiedProducts.remove(part);
            modAssocPartsTable.setItems(modifiedProducts);
        }
    }

    /** Adds associated part.
     Method that adds associated part to its table.
     @param event The event the program is listening for. */
    @FXML
    void onAddPartClick(ActionEvent event) {

            int id = modPartsTable.getSelectionModel().getSelectedItem().getId();

            Part part = Inventory.lookupPart(id);
            modifiedProducts.add(part);

            modAssocPartsTable.setItems(modifiedProducts);

    }

    /** Saves a modified product.
     Listens for a button click event that saves the provided information in the text fields and parts in the Associated Parts table, and takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onSaveModProdCLick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        String error = "";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");

        try {
            String name = modProdNameTxt.getText();
            if(name.isBlank()){
                alert.setContentText("Name is blank");
                alert.show();
                return;
            }
            error = "Inv";
            int stock = Integer.parseInt(modProdInvTxt.getText());
            error = "Price";
            double price = Double.parseDouble(modProdPriceTxt.getText());
            error = "Min";
            int min = Integer.parseInt(modProdMinTxt.getText());
            error = "Max";
            int max = Integer.parseInt(modProdMaxTxt.getText());

            if((stock >= min && stock <= max)){
                productSelected.setName(name);
                productSelected.setStock(Integer.parseInt(String.valueOf(stock)));
                productSelected.setPrice(Double.parseDouble(String.valueOf(price)));
                productSelected.setMin(Integer.parseInt(String.valueOf(min)));
                productSelected.setMax(Integer.parseInt(String.valueOf(max)));

                productSelected.getAllAssociatedParts().clear();
                productSelected.getAllAssociatedParts().addAll(modifiedProducts);
                modifiedProducts.clear();

                sceneChanger();
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
    void onLookUpPart(ActionEvent event) {
        try{
            int id = Integer.parseInt(lookUpPartsTxt.getText()) - 1;
            modPartsTable.getSelectionModel().select(id);

        }catch(NumberFormatException e){

            String name = lookUpPartsTxt.getText().toLowerCase();
            modPartsTable.setItems(Inventory.lookupPart(name));
        }
    }

    /** Loads and switches screen.
     Changes the screen back to the Main Menu. */
    private void sceneChanger() throws IOException {
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Initializes the tables and its columns. */
    public void initialize(){

        modPartsTable.setItems(Inventory.getAllParts());

        modPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        modAssocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAssocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modAssocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

    /** Receives product information.
     The Main Menu will provide all the details of the product that will be modified and populates the screen with it.
     @param selectedItem The product the Main Menu will send. */
    public void sendDetails(Product selectedItem) {
        modProdIdTxt.setText(String.valueOf(selectedItem.getId()));
        modProdNameTxt.setText(selectedItem.getName());
        modProdPriceTxt.setText(String.valueOf(selectedItem.getPrice()));
        modProdInvTxt.setText(String.valueOf(selectedItem.getStock()));
        modProdMinTxt.setText(String.valueOf(selectedItem.getMin()));
        modProdMaxTxt.setText(String.valueOf(selectedItem.getMax()));

        productSelected = selectedItem;

        modifiedProducts.addAll(productSelected.getAllAssociatedParts());
    }

    /** Receives the product.
     The Main Menu will send the product, so it can be used.
     @param product The product the Main Menu will send. */
    public void sendSelectedProduct(Product product){
        modAssocPartsTable.setItems(product.getAllAssociatedParts());
    }

}