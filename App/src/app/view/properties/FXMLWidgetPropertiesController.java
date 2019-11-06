/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.properties;

import app.model.widgets.Widget;
import app.model.widgets.basic.ExampleWidget;
import app.view.preview.FXMLDrawPreviewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author janek
 */
public class FXMLWidgetPropertiesController implements Initializable {

    private Widget owner;
    @FXML
    private TextField Text;
    @FXML
    private TextField Name;
    @FXML
    private TextField PosX;
    @FXML
    private TextField PosY;
    @FXML
    private TextField Width;
    @FXML
    private TextField Height;

    @FXML
    private TextField OnAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Accept(ActionEvent event)
            throws IOException, InterruptedException, ParserConfigurationException, TransformerException {

        this.owner.setCoX(Integer.parseInt(this.PosX.getText()));
        this.owner.setCoY(Integer.parseInt(this.PosY.getText()));
        this.owner.setPrefWidth(Integer.parseInt(this.Width.getText()));
        this.owner.setPrefHeight(Integer.parseInt(this.Height.getText()));
        this.owner.setName(this.Name.getText());
        this.owner.setText(this.Text.getText());
        this.owner.setActionClickId(this.OnAction.getText());

        // this.owner.getOwner().getWidgets().add(new
        // ExampleWidget(this.owner.getOwner(), "test"));
        // this.owner.getOwner().getWidgets().remove(this.owner.getOwner().getWidgets().size()-1);

        // Widget t = this.owner.getOwner().getWidgets().get(0);
        int i = this.owner.getOwner().getWidgets().indexOf(owner);
        this.owner.getOwner().getWidgets().set(i, owner);
        // this.owner.getOwner().getWidgets().set(i, t);
        this.owner.getOwner().getDrawPreviewController().updateView();
    }

    public Widget getOwner() {
        return owner;
    }

    public void setOwner(Widget owner) {
        this.owner = owner;
    }

    public TextField getText() {
        return Text;
    }

    public void setText(TextField Text) {
        this.Text = Text;
    }

    public TextField getName() {
        return Name;
    }

    public void setName(TextField Name) {
        this.Name = Name;
    }

    public TextField getPosX() {
        return PosX;
    }

    public void setPosX(TextField PosX) {
        this.PosX = PosX;
    }

    public TextField getPosY() {
        return PosY;
    }

    public void setPosY(TextField PosY) {
        this.PosY = PosY;
    }

    public TextField getWidth() {
        return Width;
    }

    public void setWidth(TextField Width) {
        this.Width = Width;
    }

    public TextField getHeight() {
        return Height;
    }

    public void setHeight(TextField Height) {
        this.Height = Height;
    }

    public TextField getOnAction() {
        return OnAction;
    }

    public void setOnAction(TextField OnAction) {
        this.OnAction = OnAction;
    }

}
