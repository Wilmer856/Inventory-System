package com.example.wgjavafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//JavaDocs folder is located in the src directory within the main directory with the name 'Javadoc'.

/** This class creates, starts, and launches the program.
 FUTURE ENHANCEMENT: An enhancement for a future version would be to have more control of what parts can be duplicated within a product.
 In addition, adding a pop-up before canceling a modification stating that if you want to cancel the modification, then your changes will erase will allow
 for user-friendly usability.
 @author Wilmer Guzman
 */
public class MainApplication extends Application {

    /** Displays the starting UI.
      Method that creates and loads the initial FXML file by providing a Stage container for the Scene.
      @param stage Stage container for the scene  */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        stage.setScene(scene);
        stage.show();
    }

    /** Launches the program. */
    public static void main(String[] args) {

        launch();
    }
}