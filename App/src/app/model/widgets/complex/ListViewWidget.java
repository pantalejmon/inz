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
public class ListViewWidget extends Widget {

    public ListViewWidget(Project projekt, String Name) throws IOException {
        super(projekt, Name);

        this.name = Name;
        this.text = Name;
        this.prefHeight = 100;
        this.prefWidth = 100;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, Color.BLACK);

        preview.DrawRectangle(preview.getTempX() + coX + 1, preview.getTempY() + coY + 1, prefWidth - 2, prefHeight - 2,
                Color.WHITESMOKE);

    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("ListView");

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
