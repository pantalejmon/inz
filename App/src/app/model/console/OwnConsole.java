/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.console;

import app.controller.MainController;
import java.io.PrintStream;

/**
 *
 * @author janek
 */
public class OwnConsole {
    private PrintStream standardOut;
    // public TextArea temp = new TextArea();
    private CustomOutputStream ostream;
    private MainController mainController;

    public OwnConsole(MainController mainController) {
        this.mainController = mainController;
        ostream = new CustomOutputStream(mainController.getMainWindowController().getConsole());
        standardOut = new PrintStream(ostream, true);
        System.setOut(standardOut);
    }

}
