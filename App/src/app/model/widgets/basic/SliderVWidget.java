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
public class SliderVWidget extends Widget {

    private int width = 6;

    public SliderVWidget(Project projekt, String name) throws IOException {
        super(projekt, name);
        this.name = name;
        this.text = name;
        this.prefHeight = 60;
        this.prefWidth = 10;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(preview.getTempX() + coX + prefWidth / 2 - width / 2, preview.getTempY() + coY, width,
                prefHeight, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX + prefWidth / 2 - width / 2, preview.getTempY() + coY,
                width, prefHeight, Color.BLACK);
        preview.getGc().fillOval(preview.getTempX() + coX + prefWidth / 2 - width,
                preview.getTempY() + coY + prefHeight / 2 - width / 2, 2 * width, 2 * width);
        preview.getGc().strokeOval(preview.getTempX() + coX + prefWidth / 2 - width,
                preview.getTempY() + coY + prefHeight / 2 - width / 2, 2 * width, 2 * width);
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
        orientation.setValue("VERTICAL");
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
        SliderVWidget w = null;
        try {
            w = new SliderVWidget(this.owner, this.name + this.owner.getWidgets().size());
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
