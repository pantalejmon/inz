/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.App;
import app.model.console.OwnConsole;
import app.view.mainwindow.FXMLMainWindowController;
import app.view.newproject.FXMLNewProjectWindowController;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author janek
 */
public class MainController {
    private final App app;
    private ConfigurationController configurationController;
    private FXMLMainWindowController mainWindowController;
    private FXMLNewProjectWindowController newProjectController;
    private ProjectController projectController;
    private OwnConsole console;

    public MainController(App app)
            throws ParserConfigurationException, SAXException, IOException, TransformerException {
        this.app = app;
        configurationController = new ConfigurationController();
        this.projectController = new ProjectController(this);

    }

    public FXMLMainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(FXMLMainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public App getApp() {
        return app;
    }

    public ProjectController getProjectController() {
        return projectController;
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }

    public FXMLNewProjectWindowController getNewProjectController() {
        return newProjectController;
    }

    public void setNewProjectController(FXMLNewProjectWindowController newProjectController) {
        this.newProjectController = newProjectController;
    }

    public void ConsoleStart() {
        console = new OwnConsole(this);
    }

    public ConfigurationController getConfigurationController() {
        return configurationController;
    }

    public void setConfigurationController(ConfigurationController configurationController) {
        this.configurationController = configurationController;
    }

    public OwnConsole getConsole() {
        return console;
    }

    public void setConsole(OwnConsole console) {
        this.console = console;
    }

}
