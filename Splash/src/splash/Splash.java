/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splash;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author janek
 */
public class Splash extends Preloader {

    ProgressBar bar;
    Stage stage;

    boolean noLoadingProgress = true;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        ImageView image = new ImageView("/Splash.png");
        BorderPane p = new BorderPane();
        p.setCenter(image);
        p.setBottom(bar);
        return new Scene(p, 600, 400);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createPreloaderScene());
        stage.getIcons().add(new Image("icon.png"));
        stage.getIcons().add(new Image(Splash.class.getResourceAsStream("/Splash.png")));
        stage.show();

    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        // application loading progress is rescaled to be first 50%
        // Even if there is nothing to load 0% and 100% events can be
        // delivered
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
            bar.setProgress(pn.getProgress() / 2);
            if (pn.getProgress() > 0) {
                noLoadingProgress = false;
            }
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        // ignore, hide after application signals it is ready
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
            // expect application to send us progress notifications
            // with progress ranging from 0 to 1.0
            double v = ((ProgressNotification) pn).getProgress();
            if (!noLoadingProgress) {
                // if we were receiving loading progress notifications
                // then progress is already at 50%.
                // Rescale application progress to start from 50%
                v = 0.5 + v / 2;
            }
            bar.setProgress(v);
        } else if (pn instanceof StateChangeNotification) {
            // hide after get any state update from application
            stage.hide();
        }
    }

}
