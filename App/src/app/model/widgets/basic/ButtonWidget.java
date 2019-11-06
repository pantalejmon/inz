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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class ButtonWidget extends Widget {

    public ButtonWidget(Project owner, String name) throws IOException {
        super(owner, name);
        this.name = name;
        this.text = name;
        this.prefHeight = 30;
        this.prefWidth = 60;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }

        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight,
                Color.BLACK);
        preview.getGc().setFill(Color.WHITESMOKE);
        preview.getGc().fillRoundRect(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, 5, 5);
        preview.getGc().setFill(Color.BLACK);
        preview.getGc().setFont(new Font("DejaVu Sans Mono", 12));
        preview.getGc().fillText(text, preview.getTempX() + coX + prefWidth / 2 - text.length() * 3.5,
                preview.getTempY() + coY + prefHeight / 2 + 6, prefWidth);
    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("Button");

        Attr layoutX = doc.createAttribute("layoutX");
        layoutX.setValue(String.format("%d", this.coX));
        element.setAttributeNode(layoutX);

        Attr layoutY = doc.createAttribute("layoutY");
        layoutY.setValue(String.format("%d", this.coY));
        element.setAttributeNode(layoutY);

        Attr mnemonicParsing = doc.createAttribute("mnemonicParsing");
        mnemonicParsing.setValue("false");
        element.setAttributeNode(mnemonicParsing);

        Attr prefHeight = doc.createAttribute("prefHeight");
        prefHeight.setValue(String.format("%d", this.prefHeight));
        element.setAttributeNode(prefHeight);

        Attr prefWidth = doc.createAttribute("prefWidth");
        prefWidth.setValue(String.format("%d", this.prefWidth));
        element.setAttributeNode(prefWidth);

        Attr text = doc.createAttribute("text");
        text.setValue(this.text);
        element.setAttributeNode(text);

        return element;

    }

    @Override
    public Widget copy() {
        ButtonWidget w = null;
        try {
            w = new ButtonWidget(this.owner, this.name + this.owner.getWidgets().size());
            w.setPrefWidth(this.prefWidth);
            w.setPrefHeight(prefHeight);
            FXMLLoader loader = new FXMLLoader(this.owner.getProjectController().getMainController().getApp().getClass()
                    .getResource("view/properties/FXMLWidgetProperties.fxml"));
            w.setProperties(new Tab("Properties", loader.load()));
            w.setWidgetPropertiesController(loader.getController());
            w.getWidgetPropertiesController().setOwner(w);
            w.setText("copy");
        } catch (IOException ex) {
            Logger.getLogger(ButtonWidget.class.getName()).log(Level.SEVERE, null, ex);
        }

        return w;
    }

}
