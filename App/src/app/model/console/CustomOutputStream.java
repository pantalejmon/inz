/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.console;

import java.io.IOException;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author janek
 */
public class CustomOutputStream extends OutputStream {

    private final TextArea textArea;

    public CustomOutputStream(TextArea textArea) {
        this.textArea = textArea;
    }

    public void appendText(String a) {
        Platform.runLater(() -> textArea.appendText(a));
    }

    @Override
    public void write(final int i) throws IOException {
        Platform.runLater(new Runnable() {
            public void run() {
                textArea.appendText(String.valueOf((char) i));
            }
        });
    }

}
