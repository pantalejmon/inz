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
import javafx.scene.paint.Color;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class SeparatorHWidget extends Widget {

    private int height = 1;

    public SeparatorHWidget(Project projekt, String Name) throws IOException {
        super(projekt, Name);

        this.name = Name;
        this.text = Name;
        this.prefHeight = 10;
        this.prefWidth = 120;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX,
                preview.getTempY() + coY + prefHeight / 2 - height / 2, prefWidth, height, Color.GRAY);
    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("Separator");

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
