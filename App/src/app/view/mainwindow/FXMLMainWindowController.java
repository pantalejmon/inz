/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.mainwindow;

import app.controller.MainController;
import app.model.code.JavaSample;
import app.model.projects.Project;
import app.model.widgets.Widget;
import app.model.widgets.basic.ButtonWidget;
import app.model.widgets.basic.CheckBoxWidget;
import app.model.widgets.basic.ChoiceBoxWidget;
import app.model.widgets.basic.ComboBoxWidget;
import app.model.widgets.basic.ExampleWidget;
import app.model.widgets.basic.LabelWidget;
import app.model.widgets.basic.PasswordFieldWidget;
import app.model.widgets.complex.ProgressBarWidget;
import app.model.widgets.complex.ProgressIndicatorWidget;
import app.model.widgets.basic.RadioButtonWidget;
import app.model.widgets.basic.SeparatorHWidget;
import app.model.widgets.basic.SeparatorVWidget;
import app.model.widgets.basic.SliderHWidget;
import app.model.widgets.basic.SliderVWidget;
import app.model.widgets.basic.TextFieldWidget;
import app.model.widgets.complex.ColorPickerWidget;
import app.model.widgets.complex.DatePickerWidget;
import app.model.widgets.complex.HTMLEditorWidget;
import app.model.widgets.complex.ListViewWidget;
import app.model.widgets.complex.ScrollBarHWidget;
import app.model.widgets.complex.ScrollBarVWidget;
import app.model.widgets.complex.TableViewWidget;
import app.model.widgets.complex.TextAreaWidget;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author janek
 */
public class FXMLMainWindowController implements Initializable {

    private MainController mainController;
    @FXML
    private ListView<Project> Projects;
    @FXML
    private TabPane MainTabPanel;
    @FXML
    private AnchorPane StartWindow;
    @FXML
    private TextArea Console;
    @FXML
    private Label CurrentProjectStatus;
    @FXML
    private Accordion WidgetPanel;
    @FXML
    private Tab Properties;

    @FXML
    private ListView<Widget> Widgets;
    private Tab RealPreview;
    private boolean MouseExited = true;
    @FXML
    private CheckMenuItem BootstrapMenu;
    @FXML
    private CheckMenuItem MaterialMenu;
    @FXML
    private CheckMenuItem NoneMenu;
    @FXML
    private CheckMenuItem DarkMenu;
    @FXML
    private CheckMenuItem SeaMenu;
    @FXML
    private RadioMenuItem Menu10px;
    @FXML
    private RadioMenuItem Menu20px;
    @FXML
    private RadioMenuItem Menu30px;
    @FXML
    private RadioMenuItem Menu50px;
    @FXML
    private RadioMenuItem Menu100px;
    private RadioMenuItem lastpx = Menu30px;
    @FXML
    private CheckMenuItem CaspianMenu;
    @FXML
    private CheckMenuItem AquaMenu;
    @FXML
    private CheckMenuItem AeroMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Projects.setItems(this.mainController.getProjectController().getProjects());

    }

    @FXML
    private void NewProject(ActionEvent event) throws IOException {
        Tab tab;
        FXMLLoader loader = new FXMLLoader(
                mainController.getApp().getClass().getResource("view/newproject/FXMLNewProjectWindow.fxml"));
        tab = new Tab("New Project", loader.load());
        this.MainTabPanel.getTabs().add(tab);
        this.MainTabPanel.getSelectionModel().select(tab);
        this.mainController.setNewProjectController(loader.getController());
        this.mainController.getNewProjectController().setMainController(this.mainController);
        this.mainController.getNewProjectController().setRootTab(tab);
        this.mainController.getNewProjectController().init();
    }

    public void Updatelist() {
        Projects.setItems(this.mainController.getProjectController().getProjects());
    }

    public void UpdateWidgetList() {
        if (this.mainController.getProjectController().getCurrentProject() != null) {
            Widgets.setItems(this.mainController.getProjectController().getCurrentProject().getWidgets());
        }
    }

    @FXML
    private void ChooseProject(MouseEvent event) {
        Platform.runLater(() -> {
            if (!Projects.getSelectionModel().isEmpty()) {
                if (mainController.getProjectController().getCurrentProject() != Projects.getSelectionModel()
                        .getSelectedItem()) {
                    mainController.getProjectController()
                            .setCurrentProject(Projects.getSelectionModel().getSelectedItem());
                    UpdateWidgetList();
                    CurrentProjectStatus.setText(Projects.getSelectionModel().getSelectedItem().toString());
                    System.out.print(
                            "Wybrano projekt" + Projects.getSelectionModel().getSelectedItem().toString() + "\n");
                    UpdateWidgetList();

                } else {
                    try {
                        OpenCard();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }

    public Tab getRealPreview() {
        return RealPreview;
    }

    public void setRealPreview(Tab RealPreview) {
        this.RealPreview = RealPreview;
    }

    private void OpenCard() throws IOException {
        Tab tab = Projects.getSelectionModel().getSelectedItem().getPreview();

        MainTabPanel.getTabs().remove(tab);
        MainTabPanel.getTabs().add(tab);
        MainTabPanel.getSelectionModel().select(tab);
        CurrentProjectStatus.setText(Projects.getSelectionModel().getSelectedItem().toString());
        WidgetPanel.setDisable(false);
        FXMLLoader loader = new FXMLLoader(
                mainController.getApp().getClass().getResource("view/properties/FXMLWidgetProperties.fxml"));
        Properties.setContent(loader.load());
        System.out.print("Otworzono projekt" + Projects.getSelectionModel().getSelectedItem().toString() + "\n");
    }

    @FXML
    private void WidgetCanceled(MouseEvent event) {
        this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
    }

    @FXML
    private void WidgetDragged(MouseEvent event) {
        MouseExited = false;
        this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(true);
    }

    private void NewButtonCanceled(MouseEvent event) {

        System.out.print("Anulowano próbe dodania nowego przycisku\n");
        this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
    }

    private void NewButtonDragged(MouseEvent event) {
        MouseExited = false;
        this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(true);
    }

    @FXML
    private void NewButtonExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ButtonWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "Button" + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
            Image image = new Image("icons2/Button.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;

    }

    private void NewExampleExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ExampleWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "example" + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewLebelExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new LabelWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "Label" + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/Label.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewCheckBoxExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new CheckBoxWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "CheckBox" + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/CheckBox.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewRadioButtonExited(MouseEvent event) throws IOException {

        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new RadioButtonWidget(this.mainController.getProjectController().getCurrentProject(), "RadioButton"
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/RadioButton.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewSliderHExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new SliderHWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "Slider H " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/Slider-h.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewSliderVExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new SliderVWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "Slider V " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/Slider-v.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewTextFieldExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new TextFieldWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "TextField " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/TextField.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewPasswordFieldExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new PasswordFieldWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "TextField " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/PasswordField.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewTableViewExited(MouseEvent event) throws IOException {

        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new TableViewWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "TableView " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/TableView.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewListViewExited(MouseEvent event) throws IOException {

        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ListViewWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "ListView " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/ListView.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewHTMLEditorExited(MouseEvent event) throws IOException {

        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new HTMLEditorWidget(this.mainController.getProjectController().getCurrentProject(), "HTMLEditor "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/HTMLEditor.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewColorPickerExited(MouseEvent event) throws IOException {

        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new ColorPickerWidget(this.mainController.getProjectController().getCurrentProject(), "ColorPicker "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/ColorPicker.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewSeparatorHExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new SeparatorHWidget(this.mainController.getProjectController().getCurrentProject(), "Separator H "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/Separator-h.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewSeparatorVExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new SeparatorVWidget(this.mainController.getProjectController().getCurrentProject(), "Separator V  "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/Separator-v.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewScrollBarHExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new ScrollBarHWidget(this.mainController.getProjectController().getCurrentProject(), "ScrollBar H  "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/ScrollBar-h.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewScrollBarVExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new ScrollBarVWidget(this.mainController.getProjectController().getCurrentProject(), "ScrollBar V "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewDatePickerExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new DatePickerWidget(this.mainController.getProjectController().getCurrentProject(), "DatePicker "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/DatePicker.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewTextAreaExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {

            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new TextAreaWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "TextArea " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false); // Updatelist();
            Image image = new Image("icons2/TextArea.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewProgressBarExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(
                    new ProgressBarWidget(this.mainController.getProjectController().getCurrentProject(), "ProgressBar "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
            Image image = new Image("icons2/ProgressBar.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
            // Updatelist();
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewProgressIndicatorExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ProgressIndicatorWidget(
                    this.mainController.getProjectController().getCurrentProject(), "ProgressIndicator "
                            + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
            // Updatelist();
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewComboBoxExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ComboBoxWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "ComboBox " + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
            Image image = new Image("icons2/ComboBox.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
            // Updatelist();
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void NewChoiceBoxExited(MouseEvent event) throws IOException {
        if (this.mainController.getProjectController().getCurrentProject().isWidgetAddFlag()) {
            System.out.print("Proba dodania przycisku");
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(true);
            this.mainController.getProjectController().getCurrentProject().setWidgetToAdd(new ChoiceBoxWidget(
                    this.mainController.getProjectController().getCurrentProject(),
                    "ChoiceBox" + this.mainController.getProjectController().getCurrentProject().getWidgets().size()));
            this.mainController.getProjectController().getCurrentProject().setWidgetAddFlag(false);
            Image image = new Image("icons2/ChoiceBox.png"); // pass in the image path
            this.mainController.getApp().getPrimaryStage().getScene().setCursor(new ImageCursor(image));
            // Updatelist();
        } else {
            this.mainController.getProjectController().getCurrentProject().setReadyToAdd(false);
        }
        MouseExited = true;
    }

    @FXML
    private void GenerateCode(ActionEvent event) throws ParserConfigurationException, TransformerException,
            TransformerConfigurationException, FileNotFoundException {
        this.mainController.getProjectController().getCurrentProject().getCode().GenerateCode();
    }

    @FXML
    private void DeleteProject(ActionEvent event) {
    }

    @FXML
    private void ChooseWidget(MouseEvent event) throws IOException, ParserConfigurationException, TransformerException {
        if (mainController.getProjectController().getCurrentProject().getCurrentWidget() != Widgets.getSelectionModel()
                .getSelectedItem()) {
            mainController.getProjectController().getCurrentProject()
                    .SelectWidget(Widgets.getSelectionModel().getSelectedItem());

        }
    }

    @FXML
    private void OpenProject(ActionEvent event)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        this.mainController.getProjectController().OpenProject();
    }

    @FXML
    private void Save(ActionEvent event) throws ParserConfigurationException, TransformerException,
            TransformerConfigurationException, FileNotFoundException {
        this.mainController.getProjectController().getCurrentProject().getCode().GenerateCode();
    }

    @FXML
    private void SaveAs(ActionEvent event) throws ParserConfigurationException, TransformerException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose project path");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Project", "*.fxml"));
        fileChooser
                .setInitialFileName(this.mainController.getProjectController().getCurrentProject().getFile().getName());
        file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            this.mainController.getProjectController().getCurrentProject().setFile(file);
        }
        this.mainController.getProjectController().getCurrentProject().getCode().GenerateCode();
    }

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void PreviewCode(ActionEvent event) throws ParserConfigurationException, TransformerException {
        this.mainController.getProjectController().getCurrentProject().getCode().GenerateCode();
        Tab tab = this.mainController.getProjectController().getCurrentProject().getXmlviewer();
        this.MainTabPanel.getTabs().add(tab);
        this.MainTabPanel.getSelectionModel().select(tab);

    }

    @FXML
    private void RealPreview(ActionEvent event) throws IOException, ParserConfigurationException, TransformerException {
        this.mainController.getProjectController().getCurrentProject().GeneratePreview();
    }

    public ListView<Project> getProjects() {
        return Projects;
    }

    public void setProjects(ListView<Project> Projects) {
        this.Projects = Projects;
    }

    public TabPane getMainTabPanel() {
        return MainTabPanel;
    }

    public void setMainTabPanel(TabPane MainTabPanel) {
        this.MainTabPanel = MainTabPanel;
    }

    public AnchorPane getStartWindow() {
        return StartWindow;
    }

    public void setStartWindow(AnchorPane StartWindow) {
        this.StartWindow = StartWindow;
    }

    public TextArea getConsole() {
        return Console;
    }

    public Label getCurrentProjectStatus() {
        return CurrentProjectStatus;
    }

    public void setCurrentProjectStatus(Label CurrentProjectStatus) {
        this.CurrentProjectStatus = CurrentProjectStatus;
    }

    public void setConsole(TextArea Console) {
        this.Console = Console;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Accordion getWidgetPanel() {
        return WidgetPanel;
    }

    public void setWidgetPanel(Accordion WidgetPanel) {
        this.WidgetPanel = WidgetPanel;
    }

    public Tab getProperties() {
        return Properties;
    }

    public void setProperties(Tab Properties) {
        this.Properties = Properties;
    }

    public boolean isMouseExited() {
        return MouseExited;
    }

    public void setMouseExited(boolean MouseExited) {
        this.MouseExited = MouseExited;
    }

    public ListView<Widget> getWidgets() {
        return Widgets;
    }

    public void setWidgets(ListView<Widget> Widgets) {
        this.Widgets = Widgets;
    }

    @FXML
    private void BootstrapChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/bootstrap.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(true);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);

    }

    @FXML
    private void MaterialChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/test.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(true);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);

    }

    @FXML
    private void NoneChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");

        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(true);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);
    }

    @FXML
    private void DarkChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/dark.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(true);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);
    }

    @FXML
    private void CaspianChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/caspian.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(true);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);
    }

    @FXML
    private void SeaChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/sea.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(true);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(false);
    }

    @FXML
    private void AquaChangeChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/aqua.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(false);
        this.AquaMenu.setSelected(true);
    }

    @FXML
    private void AeroChange(ActionEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().clear();
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/aero.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/xml-highlighting.css");
        this.mainController.getApp().getPrimaryStage().getScene().getStylesheets().add("theme/java.css");
        this.MaterialMenu.setSelected(false);
        this.NoneMenu.setSelected(false);
        this.BootstrapMenu.setSelected(false);
        this.DarkMenu.setSelected(false);
        this.SeaMenu.setSelected(false);
        this.CaspianMenu.setSelected(false);
        this.AeroMenu.setSelected(true);
        this.AquaMenu.setSelected(false);
    }

    @FXML
    private void ShowGrid(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        if (this.mainController.getProjectController().getCurrentProject().isGrid()) {
            this.mainController.getProjectController().getCurrentProject().setGrid(false);
        } else {
            this.mainController.getProjectController().getCurrentProject().setGrid(true);
        }
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void Set10px(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        this.mainController.getProjectController().getCurrentProject().setGridSize(10);
        this.Menu10px.setSelected(true);
        if (lastpx != null)
            lastpx.setSelected(false);
        lastpx = Menu10px;
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void Set20px(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        this.mainController.getProjectController().getCurrentProject().setGridSize(20);
        this.Menu20px.setSelected(true);
        if (lastpx != null)
            lastpx.setSelected(false);
        lastpx = Menu20px;
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void Set30px(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        this.mainController.getProjectController().getCurrentProject().setGridSize(30);
        this.Menu30px.setSelected(true);
        if (lastpx != null)
            lastpx.setSelected(false);
        lastpx = Menu30px;
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void Set50px(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        this.mainController.getProjectController().getCurrentProject().setGridSize(50);
        this.Menu50px.setSelected(true);
        if (lastpx != null)
            lastpx.setSelected(false);
        lastpx = Menu50px;
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void Set100px(ActionEvent event) throws ParserConfigurationException, TransformerException, IOException {
        this.mainController.getProjectController().getCurrentProject().setGridSize(100);
        this.Menu100px.setSelected(true);
        if (lastpx != null)
            lastpx.setSelected(false);
        lastpx = Menu100px;
        this.mainController.getProjectController().getCurrentProject().getDrawPreviewController().updateView();
    }

    @FXML
    private void ShowSample(ActionEvent event) throws ParserConfigurationException, TransformerException {

        Tab tab = this.mainController.getProjectController().getCurrentProject().getSample();
        this.MainTabPanel.getTabs().add(tab);
        this.MainTabPanel.getSelectionModel().select(tab);

    }

    @FXML
    private void GenerateSample(ActionEvent event) throws IOException {
        this.mainController.getProjectController().getCurrentProject().getSample().generateFile();
    }

    @FXML
    private void GenerateFull(ActionEvent event) throws IOException {
        File directory = new File(
                this.mainController.getProjectController().getCurrentProject().getFile().getParentFile().getPath() + "/"
                        + this.mainController.getProjectController().getCurrentProject().getName()
                                .replaceFirst("[.][^.]+$", ""));
        directory.mkdir();
        File javacode = this.mainController.getProjectController().getCurrentProject().getSample().generateFile();
        File xmlcode = this.mainController.getProjectController().getCurrentProject().getFile();
        javacode.renameTo(new File(directory.getPath() + "/" + javacode.getName()));
        xmlcode.renameTo(new File(directory.getPath() + "/" + xmlcode.getName()));
    }

    @FXML
    private void SnapGrid(ActionEvent event) {
        if (this.mainController.getProjectController().getCurrentProject().isSnapGrid()) {
            this.mainController.getProjectController().getCurrentProject().setSnapGrid(false);
        } else {
            this.mainController.getProjectController().getCurrentProject().setSnapGrid(true);
        }
    }

    @FXML
    private void ClearCursor(MouseEvent event) {
        this.mainController.getApp().getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
    }

}
