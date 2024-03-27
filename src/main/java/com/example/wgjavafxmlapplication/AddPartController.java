package com.example.wgjavafxmlapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** Controller class that allows you to add parts and provides functionality to the 'Add Part' screen. */
public class AddPartController {
    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup AddPRd;

    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private RadioButton outSourceBtn;

    @FXML
    private Label inOutLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField inOutTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    /** Takes you back to the Main Menu.
      Listens for a button click event that takes you back to the Main Menu.
      @param event The event the program is listening for. */
    @FXML
    void onCancelPBtnClick(ActionEvent event) throws IOException {

        sceneChanger(event);
    }

    /** Saves a part.
      Listens for a button click event that saves the provided information in the text fields and takes you back to the Main Menu.
      @param event The event the program is listening for. */
    @FXML
    void onSavePartBtnClick(ActionEvent event) throws IOException {
        String error = "";
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");

            int id = Inventory.getAllParts().size() + 1;
            String name = nameTxt.getText();
            if(name.isBlank()){
                alert.setContentText("Name is blank");
                alert.show();
                return;
            }
            error = "Inv";
            int stock = Integer.parseInt(invTxt.getText());
            error = "Price";
            double price = Double.parseDouble(priceTxt.getText());
            error = "Min";
            int min = Integer.parseInt(minTxt.getText());
            error = "Max";
            int max = Integer.parseInt(maxTxt.getText());


            if((stock >= min && stock <= max)){
                if(inHouseBtn.isSelected()) {
                    error = "machineID";
                    int machineId = Integer.parseInt(inOutTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else {
                    String companyName = inOutTxt.getText();
                    if(companyName.isBlank()){
                        alert.setContentText("Company Name is blank");
                        alert.show();
                        return;
                    }
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
                sceneChanger(event);
            }
            else {
                Alert numAlert = new Alert(Alert.AlertType.ERROR);
                numAlert.setTitle("Error Dialog");
                if(min > max){
                    numAlert.setContentText("Min should be less than max!");
                } else {
                    numAlert.setContentText("Inv should be between min and max!");
                }
                numAlert.showAndWait();
            }

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Problem in the "+ error + " text field. Please enter a valid value!");
            alert.showAndWait();
        }

    }

    /** Changes label text.
      Listens for a Radio Button click and changes the label text to 'Machine ID'.
      @param event The event the program is listening for. */
    @FXML
    void onInHouseRdClick(ActionEvent event) {
        inOutLbl.setText("Machine ID");
    }

    /** Changes label text.
     Listens for a Radio Button click and changes the label text to 'Company Name'.
     @param event The event the program is listening for. */
    @FXML
    void onOutRdClick(ActionEvent event) {
        inOutLbl.setText("Company Name");
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
}