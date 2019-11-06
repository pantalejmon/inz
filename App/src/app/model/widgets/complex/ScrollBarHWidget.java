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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class ScrollBarHWidget extends Widget {
    private int height = 14;

    public ScrollBarHWidget(Project projekt, String Name) throws IOException {
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
        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight,
                Color.BLACK);

        preview.getGc().setFill(Color.DIMGRAY);
        preview.getGc().fillPolygon(
                new double[] { preview.getTempX() + coX + 5, preview.getTempX() + coX + 10,
                        preview.getTempX() + coX + 10 },
                new double[] { preview.getTempY() + coY + prefHeight / 2, preview.getTempY() + coY + prefHeight / 2 - 5,
                        preview.getTempY() + coY + prefHeight / 2 + 5 },
                3);
        preview.getGc().setFill(Color.DIMGRAY);
        preview.getGc().fillPolygon(
                new double[] { preview.getTempX() + coX + prefWidth - 5, preview.getTempX() + coX + prefWidth - 10,
                        preview.getTempX() + coX + prefWidth - 10 },
                new double[] { preview.getTempY() + coY + prefHeight / 2, preview.getTempY() + coY + prefHeight / 2 - 5,
                        preview.getTempY() + coY + prefHeight / 2 + 5 },
                3);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX + 20, preview.getTempY() + coY + 1, 20, prefHeight - 2,
                Color.DIMGRAY);

    }

    @Override
    public Element getCode(Document doc) {
        Element element = doc.createElement("ScrollBar");

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

        return element;
    }

    @Override
    public Widget copy() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

}
