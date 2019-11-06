/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.widgets.complex;

import app.model.projects.Project;
import app.model.widgets.Widget;
import app.view.preview.FXMLDrawPreviewController;
import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class TableViewWidget extends Widget {

    public TableViewWidget(Project projekt, String Name) throws IOException {
        super(projekt, Name);

        this.name = Name;
        this.text = Name;
        this.prefHeight = 150;
        this.prefWidth = 150;
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
        preview.getGc().fillText("No columns in table", preview.getTempX() + coX + prefWidth / 2 - text.length() * 5,
                preview.getTempY() + coY + prefHeight / 2 + 6, prefWidth);
    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("TableView");

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

        return element;
    }

    @Override
    public Widget copy() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

}
