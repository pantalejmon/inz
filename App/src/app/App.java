/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.controller.MainController;

import javafx.application.Application;
import javafx.application.Preloader.StateChangeNotification;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janek
 */
public class App extends Application {

    private MainController mainController;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainController = new MainController(this);
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/mainwindow/FXMLMainWindow.fxml"));
        Parent root = loader.load();
        mainController.setMainWindowController(loader.getController());
        mainController.getMainWindowController().setMainController(mainController);
        mainController.getMainWindowController().Updatelist();
        mainController.ConsoleStart();
        System.out.print("Uruchomiono program GUI edytor do javaFX \n");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(this.mainController.getConfigurationController().getWindowSizeX());
        stage.setHeight(this.mainController.getConfigurationController().getWindowSizeY());
        stage.getIcons().add(new Image("icon.png"));
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/icon.png")));

        stage.setTitle("GUI FX by Jan Jakubik");
        // scene.getStylesheets().add("theme/dark.css");
        scene.getStylesheets().add("theme/bootstrap.css");
        scene.getStylesheets().add("theme/xml-highlighting.css");
        scene.getStylesheets().add("theme/java.css");
        stage.show();
        primaryStage = stage;
        notifyPreloader(new StateChangeNotification(StateChangeNotification.Type.BEFORE_START));

    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
