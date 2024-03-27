package com.example.wgjavafxmlapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** Controller class that allows you to modify existing parts and provides functionality to the 'Modify Part' screen. */
public class ModifyPartController {

    Stage stage;
    Parent scene;

    Part selectedPart;

    @FXML
    private ToggleGroup ModPRd;

    @FXML
    private RadioButton modOutBtn;

    @FXML
    private RadioButton modInBtn;

    @FXML
    private Label inOutLbl;

    @FXML
    private TextField modIdTxt;

    @FXML
    private TextField modInOutTxt;

    @FXML
    private TextField modInvTxt;

    @FXML
    private TextField modMaxTxt;

    @FXML
    private TextField modMinTxt;

    @FXML
    private TextField modNameTxt;

    @FXML
    private TextField modPriceTxt;

    /** Takes you back to the Main Menu.
     Listens for a button click event that takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onCancelPBtnClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        sceneChanger();
    }

    /** Saves a modified part.
     Listens for a button click event that saves the provided information in the text fields and takes you back to the Main Menu.
     @param event The event the program is listening for. */
    @FXML
    void onSaveModifiedPartClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        String error = "";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        try {
            int id = selectedPart.getId();
            String name = modNameTxt.getText();
            if(name.isBlank()){
                alert.setContentText("Name is blank");
                alert.show();
                return;
            }
            error = "Inv";
            int stock = Integer.parseInt(modInvTxt.getText());
            error = "Price";
            double price = Double.parseDouble(modPriceTxt.getText());
            error = "Min";
            int min = Integer.parseInt(modMinTxt.getText());
            error = "Max";
            int max = Integer.parseInt(modMaxTxt.getText());

            if((stock >= min && stock <= max)){
                if(modInBtn.isSelected()){
                    if(selectedPart instanceof InHouse){
                        selectedPart.setName(name);
                        selectedPart.setStock(Integer.parseInt(String.valueOf(stock)));
                        selectedPart.setPrice(Double.parseDouble(String.valueOf(price)));
                        selectedPart.setMin(Integer.parseInt(String.valueOf(min)));
                        selectedPart.setMax(Integer.parseInt(String.valueOf(max)));
                        error = "machineID";
                        ((InHouse) selectedPart).setMachineId(Integer.parseInt(modInOutTxt.getText()));
                    } else {
                        error = "machineID";
                        int machineId = Integer.parseInt(modInOutTxt.getText());
                        Inventory.getAllParts().set(Inventory.getAllParts().indexOf(selectedPart), new InHouse(id, name, price, stock, min, max, machineId));
                    }
                }
                 else {
                    if(selectedPart instanceof Outsourced) {
                        selectedPart.setName(modNameTxt.getText());
                        selectedPart.setStock(Integer.parseInt(modInvTxt.getText()));
                        selectedPart.setPrice(Double.parseDouble(modPriceTxt.getText()));
                        selectedPart.setMin(Integer.parseInt(modMinTxt.getText()));
                        selectedPart.setMax(Integer.parseInt(modMaxTxt.getText()));
                        ((Outsourced) selectedPart).setCompanyName(modInOutTxt.getText());
                    }
                    else {
                        String companyName = modInOutTxt.getText();
                        if(companyName.isBlank()){
                            alert.setContentText("Company Name is blank");
                            alert.show();
                            return;
                        }
                        Inventory.getAllParts().set(Inventory.getAllParts().indexOf(selectedPart), new Outsourced(id, name, price, stock, min, max, companyName));
                    }
                }

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

    /** Receives part information.
      The Main Menu will provide all the details of the part that will be modified and populates the screen with it.
      @param part The part the Main Menu will send. */
    public void sendDetails(Part part){

        modIdTxt.setText(String.valueOf(part.getId()));
        modNameTxt.setText(part.getName());
        modPriceTxt.setText(String.valueOf(part.getPrice()));
        modInvTxt.setText(String.valueOf(part.getStock()));
        modMinTxt.setText(String.valueOf(part.getMin()));
        modMaxTxt.setText(String.valueOf(part.getMax()));

        selectedPart = part;

        if(part instanceof InHouse){
            modInOutTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else if(part instanceof Outsourced){
            modOutBtn.setSelected(true);
            inOutLbl.setText("Company Name");
            modInOutTxt.setText(((Outsourced) part).getCompanyName());
        }
    }

    /** Loads and switches screen.
     Changes the screen back to the Main Menu. */
    private void sceneChanger() throws IOException {
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


}