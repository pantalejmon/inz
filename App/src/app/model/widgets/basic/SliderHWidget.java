/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.widgets.basic;

import app.model.projects.Project;
import app.model.widgets.Widget;
import app.view.preview.FXMLDrawPreviewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class SliderHWidget extends Widget {
    private int height = 6;

    public SliderHWidget(Project projekt, String name) throws IOException {
        super(projekt, name);

        this.name = name;
        this.text = name;
        this.prefHeight = 10;
        this.prefWidth = 120;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY + prefHeight / 2 - height / 2,
                prefWidth, height, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX,
                preview.getTempY() + coY + prefHeight / 2 - height / 2, prefWidth, height, Color.BLACK);
        preview.getGc().fillOval(preview.getTempX() + coX + prefWidth / 2 - height / 2,
                preview.getTempY() + coY + prefHeight / 2 - height, 2 * height, 2 * height);
        preview.getGc().strokeOval(preview.getTempX() + coX + prefWidth / 2 - height / 2,
                preview.getTempY() + coY + prefHeight / 2 - height, 2 * height, 2 * height);
    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("Slider");

        Attr layoutX = doc.createAttribute("layoutX");
        layoutX.setValue(String.format("%d", this.coX));
        element.setAttributeNode(layoutX);

        Attr layoutY = doc.createAttribute("layoutY");
        layoutY.setValue(String.format("%d", this.coY));
        element.setAttributeNode(layoutY);

        Attr orientation = doc.createAttribute("orientation");
        orientation.setValue("HORIZONTAL");
        element.setAttributeNode(orientation);

        Attr prefHeight = doc.createAttribute("prefHeight");
        prefHeight.setValue(String.format("%d", this.prefHeight));
        element.setAttributeNode(prefHeight);

        Attr prefWidth = doc.createAttribute("prefWidth");
        prefWidth.setValue(String.format("%d", this.prefWidth));
        element.setAttributeNode(prefWidth);

        Attr value = doc.createAttribute("value");
        value.setValue(String.format("50"));
        element.setAttributeNode(value);

        return element;
    }

    @Override
    public Widget copy() {
        SliderHWidget w = null;
        try {
            w = new SliderHWidget(this.owner, this.name + this.owner.getWidgets().size());
            w.setPrefWidth(this.prefWidth);
            w.setPrefHeight(prefHeight);
            FXMLLoader loader = new FXMLLoader(this.owner.getProjectController().getMainController().getApp().getClass()
                    .getResource("view/properties/FXMLWidgetProperties.fxml"));
            w.setProperties(new Tab("Properties", loader.load()));
            w.setWidgetPropertiesController(loader.getController());
            w.getWidgetPropertiesController().setOwner(this);
            w.setText("copy");
        } catch (IOException ex) {
            Logger.getLogger(ButtonWidget.class.getName()).log(Level.SEVERE, null, ex);
        }

        return w;
    }

}
