/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.newproject;

import app.controller.MainController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author janek
 */
public class FXMLNewProjectWindowController implements Initializable {

    private Tab rootTab;
    private File file;
    private MainController mainController;
    @FXML
    private TextField Projectname;
    @FXML
    private TextField size_x;
    @FXML
    private TextField Path;
    @FXML
    private Slider size_xSlider;
    @FXML
    private Slider size_ySlider;
    @FXML
    private TextField size_y;
    @FXML
    private Label Warning;
    @FXML
    private Label Warning_Path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }

    @FXML
    private void CreateNewProject(ActionEvent event)
            throws IOException, ParserConfigurationException, TransformerException {
        if (!this.Projectname.getText().isEmpty() && this.file != null) {
            this.mainController.getProjectController().NewProject(this.Projectname.getText(),
                    Integer.parseInt(this.size_x.getText()), Integer.parseInt(this.size_y.getText()), file);
            System.out.print("Tworzenie nowego projektu... \n");
            this.mainController.getMainWindowController().getMainTabPanel().getTabs().remove(this.rootTab);
            this.mainController.getMainWindowController().getCurrentProjectStatus().setText(this.Projectname.getText());
        }

        if (this.Projectname.getText().isEmpty()) {
            this.Warning.setVisible(true);
            System.out.print("Projekt musi mieć nazwe\n");
        }
        if (this.file == null) {
            this.Warning_Path.setVisible(true);
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        this.mainController.getMainWindowController().getMainTabPanel().getTabs().remove(this.rootTab);
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void init() {
        this.Projectname.setText("Project_" + mainController.getProjectController().getProjects().size());
        this.size_xSlider.setValue(this.mainController.getConfigurationController().getDefaultProjectX());
        this.size_ySlider.setValue(this.mainController.getConfigurationController().getDefaultProjectY());
        size_y.setText(String.format("%.0f", size_ySlider.getValue()));
        size_x.setText(String.format("%.0f", size_xSlider.getValue()));
    }

    public Tab getRootTab() {
        return rootTab;
    }

    public void setRootTab(Tab rootTab) {
        this.rootTab = rootTab;
    }

    @FXML
    private void ChoosePath(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose project path");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Project", "*.fxml"));
        fileChooser.setInitialFileName(this.Projectname.getText());
        file = fileChooser.showSaveDialog(new Stage());

        this.Path.setText(file.getPath());
        this.Projectname.setText(file.getName().replaceFirst("[.][^.]+$", ""));
    }

    @FXML
    private void size_xChange(MouseEvent event) {
        size_x.setText(String.format("%.0f", size_xSlider.getValue()));
    }

    @FXML
    private void size_yChange(MouseEvent event) {
        size_y.setText(String.format("%.0f", size_ySlider.getValue()));
    }
}
