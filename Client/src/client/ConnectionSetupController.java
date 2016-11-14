/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class ConnectionSetupController implements Initializable, ControlledScreen {

    @FXML
    public Button createConnection;
    @FXML
    public Button enter;
    @FXML
    public TextField playername;
    @FXML
    public Button play;
    @FXML 
    public TextField ipAddress;

    InetAddress address = null;
    public static ObjectOutputStream dout;
    public static ObjectInputStream din;
    public static Socket s1 = null;
    public static int playerId;
    public static Button playRole = null;
    String line = null;
    BufferedReader br = null;
    BufferedReader is = null;
    PrintWriter os = null;
    
    public static ScreenController communicateScreen = null;

    @FXML
    public void connectAction(ActionEvent e) {
        
        try {
            InetAddress address =InetAddress.getByName(ipAddress.getText());
            //System.out.println(InetAddress.getLocalHost().getHostAddress());
            s1 = new Socket(address, 44567); // You can use static final constant PORT_NUM
            dout = new ObjectOutputStream(s1.getOutputStream());     // data sending mood active 
            //System.out.println("pailam nani ");

            new EchoThread(s1).start();

            playername.setDisable(false);
            enter.setDisable(false);
            createConnection.setDisable(true);

        } catch (Exception ex) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Server is not found.");
            alert.setContentText("Run server first and give correct IP of server");
            alert.showAndWait();

        }

    }


    @FXML
    public void enterAction(ActionEvent e) {

        if (playername.getText().equals("") == false && playername.getText().length() <= 12) {
            try {
              //  System.out.println("etotuk paichi ");

                Object[] obj = new Object[4];
                obj[0] = 1;
                obj[1] = playername.getText();
                obj[2] = -1;

                dout.writeObject(obj);
                dout.flush();

                Thread.sleep(100);
                dout.reset();

                if (PlayerInfo.playerId != -1) {
                   communicateScreen.loadScreen(Client.screen2ID, Client.screen2file);
                    communicateScreen.setScreen(Client.screen2ID);
                }

            } catch (Exception ex) {
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("You are not connected with server.");

                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Username must have 1-12 Characters");
            alert.setContentText("Careful with the next step!");

            alert.showAndWait();

        }

    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        playername.setDisable(true);
        enter.setDisable(true);
        playRole = play;
    }

    @FXML
    public void playAction(ActionEvent e) {
        try {
//            System.out.println("etotuk paichi thread numbering ");

            Object[] obj = new Object[4];
            obj[0] = 2;
            obj[1] = playerId;
            obj[2] = -1;

            dout.writeObject(obj);
            dout.flush();
            dout.reset();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        communicateScreen = screenPage;
    }

}
