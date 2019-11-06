/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.projects;

import app.controller.ProjectController;
import app.model.code.CodeGenerator;
import app.model.auxiliary.Corner;
import app.model.code.JavaSample;
import app.model.code.XmlViewer;
import app.model.widgets.Widget;
import app.model.widgets.basic.ButtonWidget;
import app.model.widgets.basic.CheckBoxWidget;
import app.model.widgets.basic.LabelWidget;
import app.model.widgets.basic.PasswordFieldWidget;
import app.model.widgets.complex.ProgressBarWidget;
import app.model.widgets.basic.RadioButtonWidget;
import app.model.widgets.basic.TextFieldWidget;

import app.view.preview.FXMLDrawPreviewController;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author janek
 */
public class Project {

    private ProjectController projectController;
    private CodeGenerator code;
    private XmlViewer xmlviewer;
    private JavaSample sample;
    private String xmlcode;
    private String name;
    private int sizeX;
    private int sizeY;
    private FXMLDrawPreviewController drawPreviewController;
    private ObservableList<Widget> Widgets = FXCollections.observableArrayList();
    private Widget CurrentWidget;
    private Widget copyWidget;
    private Tab preview;
    protected int SelectWidth = 10;
    protected boolean selected;

    private int windowX = 30; // Window position
    private int windowY = 30; // Window position
    private int windowHeight; // Window height
    private int windowWidth;

    private File file;
    private boolean widgetAddFlag = false;
    private Widget widgetToAdd;
    private boolean ReadyToAdd = false;
    private Stage RealPreview;
    private boolean RealPreviewFlag = false;
    private Parent root1;
    private FXMLLoader fxmlLoader;
    private int gridSize = 30;
    private boolean SnapGrid = false;
    private boolean grid = false;

    public void showProperites() throws IOException {

        this.projectController.getMainController().getMainWindowController().getProperties()
                .setContent(CurrentWidget.getProperties().getContent());
        //
        this.CurrentWidget.getWidgetPropertiesController().getName().setText(this.CurrentWidget.getName());
        this.CurrentWidget.getWidgetPropertiesController().getHeight()
                .setText(String.format("%d", this.CurrentWidget.getPrefHeight()));
        this.CurrentWidget.getWidgetPropertiesController().getWidth()
                .setText(String.format("%d", this.CurrentWidget.getPrefWidth()));
        this.CurrentWidget.getWidgetPropertiesController().getPosX()
                .setText(String.format("%d", this.CurrentWidget.getCoX()));
        this.CurrentWidget.getWidgetPropertiesController().getPosY()
                .setText(String.format("%d", this.CurrentWidget.getCoY()));
        this.CurrentWidget.getWidgetPropertiesController().getOnAction().setText(this.CurrentWidget.getActionClickId());
        this.CurrentWidget.getWidgetPropertiesController().getText().setText(this.CurrentWidget.getText());

    }

    public ObservableList<Widget> getWidgets() {
        return Widgets;
    }

    public void setWidgets(ObservableList<Widget> Widgets) {
        this.Widgets = Widgets;
    }

    public Tab getPreview() {
        return preview;
    }

    public void setPreview(Tab preview) {
        this.preview = preview;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Deprecated
    public Project(String name) {
        this.name = name;

    }

    public Project(String name, int sizeX, int sizeY) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.code = new CodeGenerator(this);
        this.sample = new JavaSample(this);
    }

    public void addWidget(Widget widget) {

    }

    public void SelectWidget(Widget widget) throws IOException, ParserConfigurationException, TransformerException {
        if (CurrentWidget != null) {
            CurrentWidget.setSelected(false);
        }
        boolean flag = false;
        for (Widget d : Widgets) {
            if (d.equals(widget)) {
                flag = true;
                break;
            }

        }
        if (flag) {
            CurrentWidget = widget;
            CurrentWidget.setSelected(true);
            drawPreviewController.setCurrentWidget(CurrentWidget);
            drawPreviewController.setSelectedWidget(true);
            // drawPreviewController.setSelectedWidget(true);
            this.projectController.getMainController().getMainWindowController().getWidgets().getSelectionModel()
                    .select(CurrentWidget);
            showProperites();
            drawPreviewController.updateView();

        } else {
            System.out.print("Undefined widget error ! \n");
        }

    }

    public void RemoveWidget(Widget widget) throws ParserConfigurationException, TransformerException, IOException {
        if (!Widgets.isEmpty()) {
            boolean flag = false;
            for (Widget d : Widgets) {
                if (d.equals(widget)) {
                    flag = true;
                    break;
                }

            }
            Widgets.remove(widget);
            drawPreviewController.setSelectedWidget(false);
            drawPreviewController.setCurrentWidget(null);
            drawPreviewController.updateView();

        }
    }

    public void GeneratePreview() throws IOException, ParserConfigurationException, TransformerException {
        if (!RealPreviewFlag) {
            this.code.GenerateCode();

            FXMLLoader fxmlLoader = new FXMLLoader(this.file.toURL());
            root1 = (Parent) fxmlLoader.load();
            RealPreview = new Stage();

            RealPreview.setTitle("Preview " + this.name);
            RealPreview.setScene(new Scene(root1));
            RealPreview.setOnCloseRequest(event -> {
                RealPreviewFlag = false;
            });
            RealPreview.setAlwaysOnTop(true);
            // RealPreview.getScene().getStylesheets().add("app/bootstrap.css");
            RealPreview.show();
            RealPreviewFlag = true;
        }

    }

    public void LoadRealPreview()
            throws ParserConfigurationException, TransformerException, MalformedURLException, IOException {

        if (!Widgets.isEmpty()) {
            if (RealPreviewFlag) {
                this.code.GenerateCode();
                fxmlLoader = new FXMLLoader(this.file.toURL());
                root1 = (Parent) fxmlLoader.load();
                RealPreview.setScene(new Scene(root1));
                // RealPreview.getScene().getStylesheets().add("app/bootstrap.css");

            }
        }
    }

    public boolean CheckPosition(int x, int y) {
        if (x > this.getWindowX() && x < this.getWindowX() + this.getSizeX() && y > this.getWindowY()
                && y < this.getWindowY() + this.getSizeY()) {
            return true;
        } else {
            return false;
        }
    }

    public void DrawWindowHead(FXMLDrawPreviewController preview) {
        int i = 30;
        preview.DrawRectangle(0 + this.getWindowX(), 0 + this.getWindowY() - 20, this.getSizeX(), 20,
                Color.LIGHTSEAGREEN);

        preview.getGc().setStroke(Color.WHITE);
        preview.getGc().setLineWidth(3);
        preview.getGc().strokeLine(this.getWindowX() + this.getSizeX() - 6, this.getWindowY() - 20 + 6,
                this.getWindowX() + this.getSizeX() - 14, this.getWindowY() - 20 + 14);
        preview.getGc().strokeLine(this.getWindowX() + this.getSizeX() - 6, this.getWindowY() - 20 + 14,
                this.getWindowX() + this.getSizeX() - 14, this.getWindowY() - 20 + 6);
        preview.getGc().setFill(Color.WHITE);
        preview.getGc().setFont(new Font("Calibri", 20));
        preview.getGc().fillText(this.getName(), this.getWindowX() + 5, this.getWindowY() - 4);
    }

    public void DrawProjectWindow(FXMLDrawPreviewController preview) {

        if (selected) {
            drawSelection(preview);
        }
        preview.DrawRectangle(0 + this.getWindowX() - 1, 0 + this.getWindowY() - 1 - 20, this.getSizeX() + 2,
                this.getSizeY() + 2 + 20, Color.BLACK);
        DrawWindowHead(preview); // Rysowanie paska tytułu
        preview.DrawRectangle(0 + this.getWindowX(), 0 + this.getWindowY(), this.getSizeX(), this.getSizeY(),
                Color.WHITE);
        preview.getGc().setStroke(Color.BLACK);

        if (grid) {
            preview.getGc().setLineWidth(0.5);
            if (gridSize > 10) {
                preview.getGc().setLineDashes(3);
            }
            for (int i = 0; i <= this.sizeX / gridSize; i++) {
                preview.getGc().strokeLine(0 + getWindowX() + i * gridSize, 0 + this.getWindowY(),
                        getWindowX() + i * gridSize, this.getSizeY() + this.getWindowY());
            }

            for (int i = 0; i <= this.sizeY / gridSize; i++) {
                preview.getGc().strokeLine(0 + getWindowX(), 0 + this.getWindowY() + i * gridSize,
                        getWindowX() + this.getSizeX(), this.getWindowY() + i * gridSize);
            }
            preview.getGc().setLineDashes(0);
        }
    }

    public void drawSelection(FXMLDrawPreviewController preview) {
        int coX = windowX;
        int coY = windowY - 20;
        int prefWidth = sizeX;
        int prefHeight = sizeY + 20;
        preview.getGc().setStroke(Color.LIGHTGREEN);
        preview.getGc().setLineWidth(SelectWidth / 2);
        preview.getGc().strokeRect(coX - SelectWidth / 4, coY - SelectWidth / 4, prefWidth + 2 * SelectWidth / 4,
                prefHeight + 2 * SelectWidth / 4);
        // Lewy górny
        preview.DrawRectangle(coX - SelectWidth, coY - SelectWidth, SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(coX - SelectWidth, coY - SelectWidth, SelectWidth, SelectWidth, Color.BLACK);
        // Lewy dolny
        preview.DrawRectangle(coX - SelectWidth, coY + prefHeight, SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(coX - SelectWidth, coY + prefHeight, SelectWidth, SelectWidth, Color.BLACK);
        // Prawy górny
        preview.DrawRectangle(coX + prefWidth, coY - SelectWidth, SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(coX + prefWidth, coY - SelectWidth, SelectWidth, SelectWidth, Color.BLACK);
        // Prawy dolny
        preview.DrawRectangle(coX + prefWidth, coY + prefHeight, SelectWidth, SelectWidth, Color.WHITE);
        preview.DrawRectangleEmptyRound(coX + prefWidth, coY + prefHeight, SelectWidth, SelectWidth, Color.BLACK);

    }

    public Corner CheckCorner(int x, int y) {
        int coX = windowX;
        int coY = windowY - 20;
        int prefWidth = sizeX;
        int prefHeight = sizeY + 20;

        if (x > coX - SelectWidth && x < coX && y > coY - SelectWidth && y < coY) {

            return Corner.LG;
        } else if (x > coX - SelectWidth && x < coX + prefWidth && y > coY + prefHeight
                && y < coY + prefHeight + SelectWidth) {
            return Corner.LD;
        } else if (x > coX + prefWidth && x < coX + prefWidth + SelectWidth && y > coY - SelectWidth && y < coY) {
            return Corner.PG;
        } else if (x > coX + prefWidth && x < coX + prefWidth + SelectWidth && y > coY + prefHeight
                && y < coY + prefHeight + SelectWidth) {
            return Corner.PD;
        } else {
            return null;
        }

    }

    public void copyWidget(Widget copy) throws CloneNotSupportedException, IOException {
        if (copy != null) {
            copyWidget = copy.getCopy(copy);
        }
    }

    public void pasteWidget(int x, int y) throws CloneNotSupportedException, IOException {
        if (copyWidget != null) {
            copyWidget.setCoX(x);
            copyWidget.setCoY(y);
            Widgets.add(copyWidget);
            copyWidget.setName("copy" + Widgets.size());
            copyWidget.setText("copy" + Widgets.size());
            copyWidget.setSelected(false);
            copyWidget = copyWidget.getCopy(copyWidget);
        }
    }

    public void WidgetFactory(String type, int cx, int cy, int sx, int sy, String name, String text)
            throws IOException {
        Widget t;
        switch (type) {
        case "Button":
            t = new ButtonWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "CheckBox":
            t = new CheckBoxWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "Label":
            t = new LabelWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "PasswordField":
            t = new PasswordFieldWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "ProgressBar":
            t = new ProgressBarWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "ProgressIndicator":

            break;

        case "RadioButton":
            t = new RadioButtonWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;

        case "Slider":

            break;
        case "TextField":
            t = new TextFieldWidget(this, name);
            t.setCoX(cx);
            t.setCoY(cy);
            t.setPrefWidth(sx);
            t.setPrefHeight(sy);
            this.Widgets.add(t);
            break;
        default:
            break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public FXMLDrawPreviewController getDrawPreviewController() {
        return drawPreviewController;
    }

    public void setDrawPreviewController(FXMLDrawPreviewController drawPreviewController) {
        this.drawPreviewController = drawPreviewController;
    }

    public boolean isWidgetAddFlag() {
        return widgetAddFlag;
    }

    public void setWidgetAddFlag(boolean widgetAddFlag) {
        this.widgetAddFlag = widgetAddFlag;
    }

    public Widget getWidgetToAdd() {
        return widgetToAdd;
    }

    public void setWidgetToAdd(Widget widgetToAdd) {
        this.widgetToAdd = widgetToAdd;
    }

    public boolean isReadyToAdd() {
        return ReadyToAdd;
    }

    public void setReadyToAdd(boolean ReadyToAdd) {
        this.ReadyToAdd = ReadyToAdd;
    }

    public ProjectController getProjectController() {
        return projectController;
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }

    public CodeGenerator getCode() {
        return code;
    }

    public void setCode(CodeGenerator code) {
        this.code = code;
    }

    public Widget getCurrentWidget() {
        return CurrentWidget;
    }

    public void setCurrentWidget(Widget CurrentWidget) {
        this.CurrentWidget = CurrentWidget;
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

    public Stage getRealPreview() {
        return RealPreview;
    }

    public void setRealPreview(Stage RealPreview) {
        this.RealPreview = RealPreview;
    }

    public boolean isRealPreviewFlag() {
        return RealPreviewFlag;
    }

    public void setRealPreviewFlag(boolean RealPreviewFlag) {
        this.RealPreviewFlag = RealPreviewFlag;
    }

    public Parent getRoot1() {
        return root1;
    }

    public void setRoot1(Parent root1) {
        this.root1 = root1;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public boolean isGrid() {
        return grid;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public XmlViewer getXmlviewer() {
        return xmlviewer;
    }

    public void setXmlviewer(XmlViewer xmlviewer) {
        this.xmlviewer = xmlviewer;
    }

    public String getXmlcode() {
        return xmlcode;
    }

    public void setXmlcode(String xmlcode) {
        this.xmlcode = xmlcode;
    }

    public JavaSample getSample() {
        return sample;
    }

    public void setSample(JavaSample sample) {
        this.sample = sample;
    }

    public Widget getCopyWidget() {
        return copyWidget;
    }

    public void setCopyWidget(Widget copyWidget) {
        this.copyWidget = copyWidget;
    }

    public boolean isSnapGrid() {
        return SnapGrid;
    }

    public void setSnapGrid(boolean SnapGrid) {
        this.SnapGrid = SnapGrid;
    }

}
