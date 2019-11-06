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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class ExampleWidget extends Widget {

    public ExampleWidget(Project owner, int x, int y, int sizex, int sizey) throws IOException {
        super(owner, "To nie powinno powstać");
        this.coX = x;
        this.coY = y;
        this.prefHeight = sizey;
        this.prefWidth = sizex;
    }

    public ExampleWidget(Project owner, String name) throws IOException {
        super(owner, name);
        this.name = name;
        this.text = name;
        this.prefHeight = 60;
        this.prefWidth = 120;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(preview.getTempX() + coX, preview.getTempY() + coY, prefWidth, prefHeight, Color.CORAL);
        preview.getGc().setFill(Color.WHITE);
        preview.getGc().setFont(new Font("Calibri", 20));
        preview.getGc().fillText(text, preview.getTempX() + (this.prefWidth) / 2 + coX - 20,
                preview.getTempY() + (this.prefHeight) / 2 + coY);

    }

    @Override
    public Element getCode(Document doc) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Widget copy() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

}
