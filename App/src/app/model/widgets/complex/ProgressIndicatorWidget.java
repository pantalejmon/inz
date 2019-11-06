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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public class ProgressIndicatorWidget extends Widget {

    public ProgressIndicatorWidget(Project projekt, String Name) throws IOException {
        super(projekt, Name);

        this.name = Name;
        this.text = Name;
        this.prefHeight = 20;
        this.prefWidth = 20;
    }

    @Override
    public void Draw(FXMLDrawPreviewController preview) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Element getCode(Document doc) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Widget copy() {
        ProgressIndicatorWidget w = null;
        try {
            w = new ProgressIndicatorWidget(this.owner, this.name + this.owner.getWidgets().size());
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
