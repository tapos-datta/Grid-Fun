/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.ConnectionSetupController.playerId;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class HomeController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Button play;
    @FXML
    public Button help;
    @FXML
    public Button exit;
    @FXML
    public Label playerName;

    public static String palyerImagePath = "";
    public static int[][] playingGrid = new int[22][20];

    public static ScreenController homeScreen = null;

    @FXML
    public void playAction(ActionEvent e) {
        if(PlayingController.playingScreen==null){

        try {
//            System.out.println("etotuk paichi thread numbering ");

            Object[] obj = new Object[3];
            obj[0] = 2;
            obj[1] = ConnectionSetupController.playerId;

            ConnectionSetupController.dout.writeObject(obj);
            ConnectionSetupController.dout.flush();
             ConnectionSetupController.dout.reset();

        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You are not connected with server");

            alert.showAndWait();
        }

//        System.out.println("PlayerName :" + PlayerInfo.playerName);
        }
        else{
            homeScreen.setScreen(Client.screen3ID);
        }
    }

    @FXML
    public void helpAction(ActionEvent e) {

        homeScreen.loadScreen(Client.screen5ID, Client.screen5file);    //load the result screen
        homeScreen.setScreen(Client.screen5ID);

    }

    @FXML
    public void exitAction(ActionEvent e) {

        try {
            Object[] send = new Object[3];
            send[0] = 10;                       //operation id=5 for show all player point
            
            ConnectionSetupController.dout.writeObject(send);
            ConnectionSetupController.dout.flush();
             ConnectionSetupController.dout.reset();
        } catch (IOException ex) {
           // Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        System.out.println("hellosdfhaksdjfhaksjdfhkasjhf");
        call();

    }

    private void call() {
        playerName.setText(PlayerInfo.playerName);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        homeScreen = screenPage;

    }

}
