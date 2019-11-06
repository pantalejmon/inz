/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.model.projects.Project;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author janek
 */
public class ProjectController {

    private final ObservableList<Project> Projects = FXCollections.observableArrayList();
    private Project currentProject;
    private MainController mainController;

    public void NewProject(String projekt) {
        Projects.add(new Project(projekt));
    }

    public void NewProject(String projekt, int sizeX, int sizeY, File file)
            throws IOException, ParserConfigurationException, TransformerException {
        Project proj = new Project(projekt, sizeX, sizeY);
        currentProject = proj;
        this.mainController.getMainWindowController().UpdateWidgetList();
        Tab tab;
        FXMLLoader loader = new FXMLLoader(
                mainController.getApp().getClass().getResource("view/preview/FXMLDrawPreview.fxml"));
        tab = new Tab(projekt, loader.load());
        proj.setFile(file);
        proj.setDrawPreviewController(loader.getController());
        proj.getDrawPreviewController().setOwner(proj);
        this.mainController.getMainWindowController().getMainTabPanel().getTabs().add(tab);
        this.mainController.getMainWindowController().getMainTabPanel().getSelectionModel().select(tab);
        proj.getDrawPreviewController().init();
        proj.getDrawPreviewController().updateView();
        proj.setPreview(tab);
        proj.setProjectController(this);
        Projects.add(proj);

    }

    public ProjectController(MainController mainController) {
        this.mainController = mainController;

    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ObservableList<Project> getProjects() {
        return Projects;
    }

    public void LoadProject(File file)
            throws ParserConfigurationException, SAXException, IOException, TransformerException {
        if (file.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.print("Wczytanie projektu: " + file.getName() + "\n");
            System.out.print("Root element: ");
            System.out.println(doc.getDocumentElement().getNodeName());
            // Sprawdzenie czy mamy do czynienia z kompatybilnym projektem
            if (doc.getDocumentElement().getNodeName().equals("AnchorPane")
                    || doc.getDocumentElement().getNodeName().equals("Pane")) {
                Element root = doc.getDocumentElement();
                double dx = Double.parseDouble(root.getAttribute("prefWidth"));
                double dy = Double.parseDouble(root.getAttribute("prefHeight"));
                int x = (int) dx;
                int y = (int) dy;
                NewProject(file.getName(), x, y, file);
                System.out.print("Dlugosc listy root: " + root.getChildNodes().getLength() + "\n");
                Node children = root.getChildNodes().item(1);

                System.out.print("Nazwa children: " + children.getNodeName() + "\n");
                if (children.getNodeName().equals("children")) {
                    String type, name, text;
                    int cx, cy, sx, sy;

                    double d;
                    System.out.print("Jestem w children: " + "\n");
                    NodeList widgetList = children.getChildNodes();
                    Element t;
                    for (int i = 0; i < widgetList.getLength(); i++) {
                        if (widgetList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            t = (Element) widgetList.item(i);
                            type = t.getNodeName();
                            dx = Double.parseDouble(t.getAttribute("prefWidth"));
                            dy = Double.parseDouble(t.getAttribute("prefHeight"));
                            sx = (int) dx;
                            sy = (int) dy;
                            dx = Double.parseDouble(t.getAttribute("layoutX"));
                            dy = Double.parseDouble(t.getAttribute("layoutY"));
                            cx = (int) dx;
                            cy = (int) dy;

                            text = t.getAttribute("text");
                            name = t.getAttribute("fx:id");
                            if (name.isEmpty()) {
                                name = type + i;
                            }
                            if (text.isEmpty()) {
                                text = type + i;
                            }
                            currentProject.WidgetFactory(type, cx, cy, sx, sy, name, text);
                            currentProject.getDrawPreviewController().updateView();
                        }
                    }
                } else {
                    ShowError("FXML problem", "Wrong FXML: " + children.getNodeName(), "No children node...");
                }

            } else {
                ShowError("Load project problem",
                        "Wrong main panel\nYour panel is: " + doc.getDocumentElement().getNodeName(),
                        "GUI FX allow AnchorPane and Pane only");
            }

        } else {
            System.out.print("Projekt nie istnieje " + file.getName() + "\n");
        }
    }

    public void OpenProject() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose project path");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Project", "*.fxml"));
        LoadProject(fileChooser.showOpenDialog(new Stage()));
    }

    public void ShowError(String title, String text, String description) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.setContentText(description);
        alert.showAndWait();
    }
}
