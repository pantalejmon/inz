/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.widgets;

import app.model.auxiliary.Corner;
import app.model.projects.Project;
import app.view.preview.FXMLDrawPreviewController;
import app.view.properties.FXMLWidgetPropertiesController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author janek
 */
public abstract class Widget implements Cloneable {

    // Parameters
    protected int coX;
    protected int coY;
    protected Project owner;
    protected String name;
    protected String text;
    protected int prefWidth;
    protected int prefHeight;
    protected String ActionClickId;
    protected Color color;
    protected boolean visible;
    protected boolean disable;
    // TODO:
    protected int SelectWidth = 10;
    protected boolean selected;

    // Controlling
    protected FXMLWidgetPropertiesController widgetPropertiesController;
    protected FXMLLoader loader;
    protected Tab properties;

    public Widget(Project projekt, String Name) throws IOException {
        this.owner = projekt;
        FXMLLoader loader = new FXMLLoader(this.owner.getProjectController().getMainController().getApp().getClass()
                .getResource("view/properties/FXMLWidgetProperties.fxml"));
        this.name = Name;
        properties = new Tab("Properties", loader.load());
        this.setWidgetPropertiesController(loader.getController());
        this.getWidgetPropertiesController().setOwner(this);
    }

    public abstract void Draw(FXMLDrawPreviewController preview);

    public abstract Element getCode(Document doc);

    public boolean CheckCordinates(int x, int y) {

        if (selected) {
            if (x > this.coX - SelectWidth && x < this.coX + prefWidth + SelectWidth && y > this.coY - SelectWidth
                    && y < this.coY + prefHeight + SelectWidth) {
                return true;
            } else {
                return false;
            }
        } else {
            if (x > this.coX && x < this.coX + prefWidth && y > this.coY && y < this.coY + prefHeight) {
                return true;
            } else {
                return false;
            }
        }

    }

    public void drawSelection(FXMLDrawPreviewController preview) {
        preview.getGc().setStroke(Color.LIGHTGREEN);
        preview.getGc().setLineWidth(SelectWidth / 2);
        preview.getGc().strokeRect(preview.getTempX() + coX - SelectWidth / 4,
                preview.getTempY() + coY - SelectWidth / 4, prefWidth + 2 * SelectWidth / 4,
                prefHeight + 2 * SelectWidth / 4);
        // Lewy górny
        preview.DrawRectangle(preview.getTempX() + coX - SelectWidth, preview.getTempY() + coY - SelectWidth,
                SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX - SelectWidth, preview.getTempY() + coY - SelectWidth,
                SelectWidth, SelectWidth, Color.BLACK);
        // Lewy dolny
        preview.DrawRectangle(preview.getTempX() + coX - SelectWidth, preview.getTempY() + coY + prefHeight,
                SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX - SelectWidth, preview.getTempY() + coY + prefHeight,
                SelectWidth, SelectWidth, Color.BLACK);
        // Prawy górny
        preview.DrawRectangle(preview.getTempX() + coX + prefWidth, preview.getTempY() + coY - SelectWidth, SelectWidth,
                SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX + prefWidth, preview.getTempY() + coY - SelectWidth,
                SelectWidth, SelectWidth, Color.BLACK);
        // Prawy dolny
        preview.DrawRectangle(preview.getTempX() + coX + prefWidth, preview.getTempY() + coY + prefHeight, SelectWidth,
                SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(preview.getTempX() + coX + prefWidth, preview.getTempY() + coY + prefHeight,
                SelectWidth, SelectWidth, Color.BLACK);

    }

    public Corner CheckCorner(int x, int y) {

        if (x > this.coX - SelectWidth && x < this.coX && y > this.coY - SelectWidth && y < this.coY) {

            return Corner.LG;
        } else if (x > this.coX - SelectWidth && x < this.coX + prefWidth && y > this.coY + prefHeight
                && y < this.coY + prefHeight + SelectWidth) {
            return Corner.LD;
        } else if (x > this.coX + prefWidth && x < this.coX + prefWidth + SelectWidth && y > this.coY - SelectWidth
                && y < this.coY) {
            return Corner.PG;
        } else if (x > this.coX + prefWidth && x < this.coX + prefWidth + SelectWidth && y > this.coY + prefHeight
                && y < this.coY + prefHeight + SelectWidth) {
            return Corner.PD;
        } else {
            return null;
        }

    }

    abstract public Widget copy();

    public Widget getCopy(Widget g) throws CloneNotSupportedException, IOException {
        Widget d;
        d = (Widget) g.clone();
        FXMLLoader loader = new FXMLLoader(this.owner.getProjectController().getMainController().getApp().getClass()
                .getResource("view/properties/FXMLWidgetProperties.fxml"));
        d.setProperties(new Tab("Properties", loader.load()));
        d.setWidgetPropertiesController(loader.getController());
        d.getWidgetPropertiesController().setOwner(d);
        return d;
    }

    public int getCoX() {
        return coX;
    }

    public void setCoX(int coX) {
        this.coX = coX;
    }

    public int getCoY() {
        return coY;
    }

    public void setCoY(int coY) {
        this.coY = coY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrefWidth() {
        return prefWidth;
    }

    public void setPrefWidth(int prefWidth) {
        this.prefWidth = prefWidth;
    }

    public int getPrefHeight() {
        return prefHeight;
    }

    public void setPrefHeight(int prefHeight) {
        this.prefHeight = prefHeight;
    }

    public String getActionClickId() {
        return ActionClickId;
    }

    public void setActionClickId(String ActionClickId) {
        this.ActionClickId = ActionClickId;
    }

    public int getSelectWidth() {
        return SelectWidth;
    }

    public void setSelectWidth(int SelectWidth) {
        this.SelectWidth = SelectWidth;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;
    }

    public FXMLWidgetPropertiesController getWidgetPropertiesController() {
        return widgetPropertiesController;
    }

    public void setWidgetPropertiesController(FXMLWidgetPropertiesController widgetPropertiesController) {
        this.widgetPropertiesController = widgetPropertiesController;
    }

    public Project getOwner() {
        return owner;
    }

    public void setOwner(Project owner) {
        this.owner = owner;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Tab getProperties() {
        return properties;
    }

    public void setProperties(Tab properties) {
        this.properties = properties;
    }

    public String getText() {
        return text;
    }

    public void setText(String Text) {
        this.text = Text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

}
