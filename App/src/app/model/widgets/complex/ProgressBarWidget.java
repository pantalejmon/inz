/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.widgets.complex;

import app.model.projects.Project;
import app.model.widgets.Widget;
import app.model.widgets.basic.ButtonWidget;
import app.view.preview.FXMLDrawPreviewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class ProgressBarWidget extends Widget {

    public ProgressBarWidget(Project projekt, String Name) throws IOException {
        super(projekt, Name);
        this.text = name;
        this.prefHeight = 20;
        this.prefWidth = 120;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight,
                Color.BLACK);
        preview.getGc().setFill(Color.DODGERBLUE);
        preview.getGc().fillRoundRect(preview.getTempX() + coX + 4, preview.getTempY() + coY + 4, prefWidth / 2 - 4,
                prefHeight - 8, 5, 5);

    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("ProgressBar");

        Attr layoutX = doc.createAttribute("layoutX");
        layoutX.setValue(String.format("%d", this.coX));
        element.setAttributeNode(layoutX);

        Attr layoutY = doc.createAttribute("layoutY");
        layoutY.setValue(String.format("%d", this.coY));
        element.setAttributeNode(layoutY);

        Attr prefHeight = doc.createAttribute("prefHeight");
        prefHeight.setValue(String.format("%d", this.prefHeight));
        element.setAttributeNode(prefHeight);

        Attr prefWidth = doc.createAttribute("prefWidth");
        prefWidth.setValue(String.format("%d", this.prefWidth));
        element.setAttributeNode(prefWidth);

        Attr progress = doc.createAttribute("progress");
        progress.setValue("0.50");
        element.setAttributeNode(progress);
        return element;
    }

    @Override
    public Widget copy() {
        ProgressBarWidget w = null;
        try {
            w = new ProgressBarWidget(this.owner, this.name + this.owner.getWidgets().size());
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
