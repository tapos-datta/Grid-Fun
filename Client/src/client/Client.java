/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Tapos
 */
public class Client extends Application {

    public static String screen1file = "ConnectionSetup.fxml";
    public static String screen1ID = "Connection";
    public static String screen2file = "Home.fxml";
    public static String screen2ID = "Home";
    public static String screen3file = "Playing.fxml";
    public static String screen3ID = "playing";
    public static String screen4file = "Result.fxml";
    public static String screen4ID = "result";
    public static String screen5file = "Help.fxml";
    public static String screen5ID = "help";
    public static Stage primary;
    public static String[] imagefile = new String[5];

    //storing final result and all player name
    public static String[] Listname = new String[5];
    public static int[] result = new int[5];

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;
        ScreenController mainContainer = new ScreenController();
        mainContainer.loadScreen(Client.screen1ID, Client.screen1file);
        // mainContainer.loadScreen(Client.screen2ID, Client.screen2file);

        //load playerimage file
        imagefile[0] = "ressources/f0f3f4.png";
        imagefile[1] = "ressources/cc33ff.png";
        imagefile[2] = "ressources/Rubine_Red.png";
        imagefile[3] = "ressources/00f000.png";
        imagefile[4] = "ressources/6699ff.png";

        mainContainer.setScreen(Client.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setMaxWidth(770);
        primaryStage.setHeight(645);
        Image icon = new Image(getClass().getResourceAsStream("G-icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setTitle("GridFun");
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent we) {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you want to leave now?");
                alert.setContentText("");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    
                    if (ConnectionSetupController.communicateScreen != null && ConnectionSetupController.s1 != null) {

                        try {
                            Object[] message = new Object[2];
                            message[0] = 7;      //closing signal
                            ConnectionSetupController.dout.writeObject(message);
                            ConnectionSetupController.dout.flush();

                            Thread.sleep(100);
                        } catch (IOException ex) {
                            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    
                    primaryStage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
                we.consume();    //stop this event
            }

        }
        );

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void leaveMessage() {

    }

}
