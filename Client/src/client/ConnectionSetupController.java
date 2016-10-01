/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.CommunicateObject;
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

    InetAddress address = null;
    public static ObjectOutputStream dout;
    public static ObjectInputStream din;
    Socket s1 = null;
    public static int playerId;
    public static Button playRole = null;
    String line = null;
    BufferedReader br = null;
    BufferedReader is = null;
    PrintWriter os = null;
    CommunicateObject send = null;
    ScreenController myScreen = null;

    @FXML
    public void connectAction(ActionEvent e) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            s1 = new Socket(address, 1978); // You can use static final constant PORT_NUM
            dout = new ObjectOutputStream(s1.getOutputStream());     // data sending mood active 
            System.out.println("pailam nani ");

            new EchoThread(s1).start();

            playername.setDisable(false);
            enter.setDisable(false);
            createConnection.setDisable(true);

        } catch (Exception ex) {
            System.out.println("sdjfhskdjf");
        }

    }
    
//    @FXML
//    public void keyAction(ActionEvent e){
//       if(playername.getText().length()>13){
//          playername.setText(playername.getText().substring(0, 12));
//       }
//       else{
//           playername.setText(playername.getText());
//       }
//    }

    @FXML
    public void enterAction(ActionEvent e) {

        if (playername.equals("") == false) {
            try {
                System.out.println("etotuk paichi ");

                Object[] obj = new Object[4];
                obj[0] = 1;
                obj[1] = playername.getText();
                obj[2] = -1;

                dout.writeObject(obj);
                dout.flush();

                Thread.sleep(100);
                
                if (PlayerInfo.playerId != -1) {
                    myScreen.loadScreen(Client.screen2ID, Client.screen2file);
                    myScreen.setScreen(Client.screen2ID);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("please enter username");
        }

//        Task<Void> task1 = new Task<Void>() {
//
//            @Override
//            protected Void call() throws Exception {
//
//                System.out.println("ei porjonto aichi");
//
//                System.out.println("player " + playername.getText());
//               
//
//                return null;
//
//            }
//
//        };
//
//        Thread th1 = new Thread(task1);
//        th1.setDaemon(true);
//        th1.start();
    }

    void process() {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                System.out.println("thread running");
                while (true) {
                    try {
                        Object[] receive = (Object[]) din.readObject();

                        if ((int) receive[0] == 1) {

                            System.out.println("Player id : " + receive[1]);
                            System.out.println("Player name : " + receive[2]);

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ConnectionSetupController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ConnectionSetupController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });
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
            System.out.println("etotuk paichi thread numbering ");

            Object[] obj = new Object[4];
            obj[0] = 2;
            obj[1] = playerId;
            obj[2] = -1;

            dout.writeObject(obj);
            dout.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        myScreen = screenPage;
    }

}
