/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public static String[] Listname=new String[5];         
    public static int[]    result=new int[5];

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

        primaryStage.setTitle("Client");
        //  primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
