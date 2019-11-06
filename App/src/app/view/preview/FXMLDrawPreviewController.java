/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.preview;

import app.model.auxiliary.Area;
import app.model.auxiliary.Corner;
import static app.model.auxiliary.Corner.*;
import app.model.auxiliary.Point;

import app.model.projects.Project;
import app.model.widgets.Widget;
import app.model.widgets.basic.ExampleWidget;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author janek
 */
public class FXMLDrawPreviewController implements Initializable {

    @FXML
    private Canvas DrawField;
    private GraphicsContext gc;
    private Project owner;
    private Widget currentWidget;
    private boolean selectedWidget;
    private boolean entryWidget;
    private Area window;
    private int windowX = 30; // Window position
    private int windowY = 30; // Window position
    private int windowHeight; // Window height
    private int windowWidth; // Window Width
    private int tempX = windowX;
    private int tempY = windowY;
    private int distanceX = 0;
    private int distanceY = 0;
    private int mouseX;
    private int mouseY;
    private Point Context;
    private boolean ContextFlag = false;
    private boolean windowMoving = false;
    private boolean cornerchange;
    private Corner corner;
    private ArrayList<Widget> widgetToDelete = new ArrayList<Widget>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init() {
        gc = DrawField.getGraphicsContext2D();

        tempX = owner.getWindowX();
        tempY = owner.getWindowX();
    }

    /**
     * ************************CALLBACKS*******************************************************************
     */
    @FXML
    private void DragEntered(DragEvent event) {
        // int x = (int) event.getX();
        // int y = (int) event.getY();
        // DrawRectangle(x, y, 40, 20, Color.GREEN);
        System.out.print("Wywolano drag event");
    }

    @FXML
    private void DragStart(MouseEvent event) {
        // System.out.print("Wywolano drag event: DragStart\n");
    }

    @FXML
    private void DragEnd(DragEvent event) {
    }

    @FXML
    private void ContextMenu(ContextMenuEvent event) {
        Context = new Point((int) event.getX(), (int) event.getY());

    }

    @FXML
    private void RemoveWidget(ActionEvent event)
            throws ParserConfigurationException, TransformerException, IOException {
        int cox = Context.getX();
        int coy = Context.getY();
        boolean flag = false;

        if (this.owner.CheckPosition(cox, coy)) {
            for (Widget d : this.owner.getWidgets()) {
                if (d.CheckCordinates(cox - owner.getWindowX(), coy - owner.getWindowY())) {
                    flag = true;
                    break;

                }
            }
            if (flag) {
                this.owner.RemoveWidget(currentWidget);
            }
        }
        updateView();
    }

    @FXML
    private void DragExited(DragEvent event) {
    }

    @FXML
    private void DragOver(DragEvent event) {
    }

    @FXML
    private void DragDropped(DragEvent event) {
    }

    @FXML
    private void MouseDragged(MouseEvent event) throws IOException, ParserConfigurationException, TransformerException {
        int cox = (int) event.getX();
        int coy = (int) event.getY();

        if (cox > owner.getWindowX() && cox < this.owner.getSizeX() + owner.getWindowX() && coy > owner.getWindowY()
                && coy < this.owner.getSizeY() + owner.getWindowY()) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            updateView();
        }

        if (windowMoving) {
            tempX = cox - distanceX;
            tempY = coy - distanceY;
            this.owner.setWindowX(tempX);
            this.owner.setWindowY(tempY);
            updateView();
        }
        if (this.owner.isSelected()) {
            if (cornerchange) {
                int LastX = this.owner.getWindowX();
                int LastY = this.owner.getWindowY();
                if (corner.equals(LD)) {

                    this.DrawField.setCursor(Cursor.SW_RESIZE);
                    this.owner.setSizeY((int) event.getY() - LastY);
                    this.owner.setSizeX((int) (this.owner.getSizeX() + LastX - event.getX()));
                    this.owner.setWindowX((int) event.getX());

                } else if (corner.equals(LG)) {

                    this.DrawField.setCursor(Cursor.NW_RESIZE);
                    this.owner.setSizeY((int) (this.owner.getSizeY() + LastY - event.getY()));
                    this.owner.setSizeX((int) (this.owner.getSizeX() + LastX - event.getX()));
                    this.owner.setWindowX((int) event.getX());
                    this.owner.setWindowY((int) event.getY());
                    // this.currentWidget.setPrefWidth((int) (this.owner.getWindowX() +
                    // this.currentWidget.getPrefWidth() + LastX - event.getX()));
                    // this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX());
                    // this.currentWidget.setPrefHeight((int) (this.owner.getWindowY() +
                    // this.currentWidget.getPrefHeight() + LastY - event.getY()));
                    // this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY());

                } else if (corner.equals(PG)) {

                    this.DrawField.setCursor(Cursor.NE_RESIZE);
                    this.owner.setSizeY((int) (this.owner.getSizeY() - event.getY() + LastY));
                    this.owner.setSizeX((int) (event.getX() - LastX));
                    this.owner.setWindowY((int) event.getY());

                } else if (corner.equals(PD)) {

                    this.DrawField.setCursor(Cursor.SE_RESIZE);
                    this.owner.setSizeX((int) (event.getX() - LastX));
                    this.owner.setSizeY((int) event.getY() - LastY);

                }
            } else {

                // this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX() -
                // distanceX);
                // this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY() -
                // distanceY);
            }
            // this.owner.showProperites();
            updateView();
        }

        if (selectedWidget) {
            if (cornerchange) {
                int LastX = currentWidget.getCoX();
                int LastY = currentWidget.getCoY();
                if (corner.equals(LD)) {
                    if ((int) event.getY() - owner.getWindowY() - LastY > 0 && (int) (owner.getWindowX()
                            + this.currentWidget.getPrefWidth() + LastX - event.getX()) > 0) {
                        this.DrawField.setCursor(Cursor.SW_RESIZE);
                        this.currentWidget.setPrefHeight((int) event.getY() - this.owner.getWindowY() - LastY);
                        this.currentWidget.setPrefWidth((int) (this.owner.getWindowX()
                                + this.currentWidget.getPrefWidth() + LastX - event.getX()));
                        this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX());
                    }
                } else if (corner.equals(LG)) {
                    if ((int) (this.owner.getWindowX() + this.currentWidget.getPrefWidth() + LastX - event.getX()) > 0
                            && (int) (this.owner.getWindowY() + this.currentWidget.getPrefHeight() + LastY
                                    - event.getY()) > 0) {
                        this.DrawField.setCursor(Cursor.NW_RESIZE);
                        this.currentWidget.setPrefWidth((int) (this.owner.getWindowX()
                                + this.currentWidget.getPrefWidth() + LastX - event.getX()));
                        this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX());
                        this.currentWidget.setPrefHeight((int) (this.owner.getWindowY()
                                + this.currentWidget.getPrefHeight() + LastY - event.getY()));
                        this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY());
                    }
                } else if (corner.equals(PG)) {
                    if ((int) (this.owner.getWindowY() + this.currentWidget.getPrefHeight() + LastY - event.getY()) > 0
                            && (int) event.getX() - this.owner.getWindowX() - LastX > 0) {
                        this.DrawField.setCursor(Cursor.NE_RESIZE);
                        this.currentWidget.setPrefHeight((int) (this.owner.getWindowY()
                                + this.currentWidget.getPrefHeight() + LastY - event.getY()));
                        this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY());
                        // this.currentWidget.setPrefHeight((int) event.getY() - this.owner.getWindowY()
                        // - LastY);
                        this.currentWidget.setPrefWidth((int) event.getX() - this.owner.getWindowX() - LastX);
                    }
                } else if (corner.equals(PD)) {
                    if ((int) event.getY() - this.owner.getWindowY() - LastY > 0
                            && (int) event.getX() - this.owner.getWindowX() - LastX > 0) {
                        this.DrawField.setCursor(Cursor.SE_RESIZE);
                        this.currentWidget.setPrefHeight((int) event.getY() - this.owner.getWindowY() - LastY);
                        this.currentWidget.setPrefWidth((int) event.getX() - this.owner.getWindowX() - LastX);
                    }
                }
            } else {

                this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX() - distanceX);
                this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY() - distanceY);

            }
            this.owner.showProperites();
            updateView();
        }
    }

    @FXML
    private void MouseExited(MouseEvent event) {
    }

    @FXML
    private void CopyWidget(ActionEvent event) throws CloneNotSupportedException, IOException {
        int cox = Context.getX();// - this.owner.getWindowX();
        int coy = Context.getY();// - this.owner.getWindowHeight();
        boolean flag = false;

        if (this.owner.CheckPosition(cox, coy)) {
            for (Widget d : this.owner.getWidgets()) {
                if (d.CheckCordinates(cox - owner.getWindowX(), coy - owner.getWindowY())) {
                    flag = true;
                    break;

                }
            }
            if (flag) {
                this.owner.copyWidget(currentWidget);
            }
        }
        // updateView();
    }

    @FXML
    private void PasteWidget(ActionEvent event)
            throws ParserConfigurationException, IOException, TransformerException, CloneNotSupportedException {
        int cox = Context.getX() - this.owner.getWindowX();
        int coy = Context.getY() - this.owner.getWindowHeight();

        this.owner.pasteWidget(cox, coy);
        updateView();
    }

    @FXML
    private void MousePressed(MouseEvent event) throws IOException, ParserConfigurationException, TransformerException {

        // currentWidget.setSelected(false);
        cornerchange = false;
        boolean selectFlag = false;
        int cox = (int) event.getX();
        int coy = (int) event.getY();

        // Kliknięto na pasek okna
        if (cox > this.owner.getWindowX() && cox < this.owner.getSizeX() + this.owner.getWindowX()
                && coy > this.owner.getWindowY() - 20 && coy < this.owner.getWindowY()) {
            distanceX = cox - this.owner.getWindowX();
            distanceY = coy - this.owner.getWindowY();
            this.owner.setSelected(true);
            windowMoving = true;
        } else if (this.owner.isSelected()) {
            this.corner = this.owner.CheckCorner(cox, coy);
            if (corner != null) {
                cornerchange = true;
                System.out.print("Kliknieto rog" + this.corner.name());
            } else {
                cornerchange = false;
                this.owner.setSelected(false);
            }
        }

        if (selectedWidget) {
            if (currentWidget.CheckCordinates(cox - this.owner.getWindowX(), coy - this.owner.getWindowY())) {
                // System.out.print("Sprawdzanie rogu\n");
                this.corner = this.currentWidget.CheckCorner(cox - this.owner.getWindowX(),
                        coy - this.owner.getWindowY());
                // System.out.print(this.corner);
                if (corner != null) {
                    cornerchange = true;
                    System.out.print("Kliknieto rog" + this.corner.name());
                } else {
                    cornerchange = false;
                }
            }
        }

        if (this.owner.CheckPosition(cox, coy)) {
            // this.owner.setSelected(false);
            for (Widget d : this.owner.getWidgets()) {
                if (d.CheckCordinates(cox - this.owner.getWindowX(), coy - this.owner.getWindowY())) {

                    if (!d.equals(this.owner.getCurrentWidget())) {
                        if (currentWidget != null) {
                            currentWidget.setSelected(false);
                        }
                        this.currentWidget = d;
                        this.selectedWidget = true;
                        // d.setSelected(true);
                        System.out.print("Wybrano Widget: " + d.toString() + "\n");
                        this.owner.SelectWidget(currentWidget);

                    }
                    this.selectedWidget = true;
                    distanceX = (cox - this.owner.getWindowX()) - this.currentWidget.getCoX();
                    distanceY = (coy - this.owner.getWindowY()) - this.currentWidget.getCoY();
                    selectFlag = true;
                    d.setSelected(true);
                }
            }

        }
        if (!selectFlag) {
            selectedWidget = false;
            if (currentWidget != null) {
                currentWidget.setSelected(false);
            }
            // this.owner.setSelected(false);
            // currentWidget = null;
        }
        updateView();

    }

    @FXML
    private void MouseReleased(MouseEvent event)
            throws IOException, ParserConfigurationException, TransformerException {
        this.owner.getProjectController().getMainController().getApp().getPrimaryStage().getScene()
                .setCursor(Cursor.DEFAULT);
        this.DrawField.setCursor(Cursor.DEFAULT);
        int cox = (int) event.getX();
        int coy = (int) event.getY();
        if (windowMoving) {
            this.owner.setWindowX(cox - distanceX);
            this.owner.setWindowY(coy - distanceY);

            tempX = this.owner.getWindowX();
            tempY = this.owner.getWindowY();
            distanceX = 0;
            distanceY = 0;
            // this.owner.setSelected(false);
            updateView();
            windowMoving = false;

        }
        if (selectedWidget) {
            if (!this.owner.CheckPosition(cox, coy)) {
                this.owner.RemoveWidget(currentWidget);

                selectedWidget = false;
                // this.owner.showProperites();
                updateView();
            }

        }

    }

    @FXML
    private void MouseEntered(MouseEvent event) throws ParserConfigurationException, TransformerException, IOException {
        if (currentWidget != null) {
            // currentWidget.setSelected(false);
        }
        if (this.owner.isReadyToAdd() && this.owner.CheckPosition((int) event.getX(), (int) event.getY())) {
            this.owner.getWidgets().add(this.owner.getWidgetToAdd());
            this.currentWidget = this.owner.getWidgetToAdd();
            this.currentWidget.setCoX((int) event.getX() - this.owner.getWindowX());
            this.currentWidget.setCoY((int) event.getY() - this.owner.getWindowY());
            this.selectedWidget = false;
            updateView();
            this.owner.setReadyToAdd(false);
            this.owner.getProjectController().getMainController().getApp().getPrimaryStage().getScene()
                    .setCursor(Cursor.DEFAULT);
            // System.out.print("\nWywolano MouseEntered\n");
        }
    }

    @FXML
    private void MouseClicked(MouseEvent event) {
    }

    @FXML
    private void MouseMoved(MouseEvent event) {
        mouseX = (int) event.getX();
        mouseY = (int) event.getY();
    }

    /**
     * ************************Draw*******************************************************************
     */
    /**
     * *************HIGHLEVEL**********************************************
     */
    public void DrawWorkPlace() {
        DrawRectangle(0, 0, 2000, 2000, Color.DIMGRAY);
    }

    public void DrawRectangle(int cox, int coy, int sizex, int sizey, Color color) {
        gc.setFill(color);
        gc.fillRect(cox, coy, sizex, sizey);
    }

    public void DrawRectangleEmptyRound(int cox, int coy, int sizex, int sizey, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(1);
        gc.strokeRoundRect(cox, coy, sizex, sizey, 5, 5);
    }

    public void DrawRectangle(int cox, int coy, int sizex, int sizey, LinearGradient color) {
        gc.setFill(color);
        gc.fillRect(cox, coy, sizex, sizey);
    }

    public void updateView() throws ParserConfigurationException, TransformerException, IOException {

        DrawWorkPlace();
        this.owner.DrawProjectWindow(this);
        tempX = this.owner.getWindowX();
        tempY = this.owner.getWindowY();
        if (!this.owner.isSnapGrid()) {
            for (Widget d : this.owner.getWidgets()) {
                if (this.owner.CheckPosition(this.owner.getWindowX() + d.getCoX() + d.getPrefWidth(),
                        this.owner.getWindowY() + d.getCoY() + d.getPrefHeight())) {
                    d.Draw(this);
                }

                // System.out.print("Wywolano update view\n");
            }
        } else {
            for (Widget d : this.owner.getWidgets()) {
                if (this.owner.CheckPosition(this.owner.getWindowX() + d.getCoX() + d.getPrefWidth(),
                        this.owner.getWindowY() + d.getCoY() + d.getPrefHeight())) {
                    int cox = d.getCoX();
                    int gox = cox / this.owner.getGridSize();
                    cox = gox * this.owner.getGridSize();
                    d.setCoX(cox);

                    int coy = d.getCoY();
                    int goy = coy / this.owner.getGridSize();
                    coy = goy * this.owner.getGridSize();
                    d.setCoY(coy);

                    d.Draw(this);
                }

                // System.out.print("Wywolano update view\n");
            }
        }
        if (selectedWidget) {
            currentWidget.Draw(this);
        }
        this.owner.LoadRealPreview();

    }

    // Getter and setter
    public Canvas getDrawField() {
        return DrawField;
    }

    public void setDrawField(Canvas DrawField) {
        this.DrawField = DrawField;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public int getWindowX() {
        return windowX;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

    public int getTempX() {
        return tempX;
    }

    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public void setTempY(int tempY) {
        this.tempY = tempY;
    }

    public boolean isWindowMoving() {
        return windowMoving;
    }

    public void setWindowMoving(boolean windowMoving) {
        this.windowMoving = windowMoving;
    }

    public Project getOwner() {
        return owner;
    }

    public void setOwner(Project owner) {
        this.owner = owner;
    }

    public Widget getCurrentWidget() {
        return currentWidget;
    }

    public void setCurrentWidget(Widget currentWidget) {
        this.currentWidget = currentWidget;
    }

    public boolean isSelectedWidget() {
        return selectedWidget;
    }

    public void setSelectedWidget(boolean selectedWidget) {
        this.selectedWidget = selectedWidget;
    }

    public Area getWindow() {
        return window;
    }

    public void setWindow(Area window) {
        this.window = window;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(int distanceX) {
        this.distanceX = distanceX;
    }

    public int getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(int distanceY) {
        this.distanceY = distanceY;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public boolean isCornerchange() {
        return cornerchange;
    }

    public void setCornerchange(boolean cornerchange) {
        this.cornerchange = cornerchange;
    }

    public Corner getCorner() {
        return corner;
    }

    public void setCorner(Corner corner) {
        this.corner = corner;
    }

}
