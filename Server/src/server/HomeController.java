/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Button run;
    @FXML
    public Button stop;
    @FXML
    public TextArea console;

    public static int playerNumber;
    static final int PORT = 1978;
    static final int Max = 100;
    public static String name[] = new String[5];
    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream din;
    ObjectOutputStream dout;
    public static int token;
    public static int[] nextPlayer = new int[5];
    public static List<EchoThread> clients = new ArrayList<EchoThread>();
    public static boolean noMorePlayer = false;

    @FXML
    public void runAction(ActionEvent e) {
        run.setDisable(true);
        stop.setDisable(false);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        console.appendText("Server is running... \n");

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                int i = 1;
                playerNumber = 0;
                while (i <= Max) {
                    try {

                        socket = serverSocket.accept();
                        
                        if(noMorePlayer == true){
                            socket.close();
                            break;
                        }

                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                console.appendText(socket + "\n");

                            }

                        });

                    } catch (IOException ex) {
                        System.out.println("I/O error: " + ex);
                    }
                    // new thread for a client
                    EchoThread client = new EchoThread(socket, i);
                    client.start();
                    clients.add(client);
                    i++;
                }
                

                return null;

            }

        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    @FXML
    public void stopAction(ActionEvent e) {

//        try {
            
            // not working
//            for (EchoThread t : clients) {
//                try {
//                    if (t.socket != null) {
//                        System.out.println(t.socket);
//                        t.socket.close();
//                        System.out.println(t.socket);
//                    }
//                } catch (Exception ex1) {
//                    
//                    //  Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//            }
//            try {
//                serverSocket.close();
//            } catch (IOException ex) {
//                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            din.close();
//            dout.close();
//            run.setDisable(false);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServerSocket serverSocket = null;
        Socket socket = null;
        console.setEditable(false);
        stop.setDisable(true);
    }

}
