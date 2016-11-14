/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.HomeController.homeScreen;
import static client.PlayingController.maxCol;
import static client.PlayingController.maxRow;
import static client.PlayingController.playingScreen;
import com.sun.media.jfxmedia.events.PlayerEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javax.imageio.ImageIO;

/**
 *
 * @author Tapos
 */
class EchoThread extends Thread {

    Socket s1;
    ObjectOutputStream dout;
    ObjectInputStream din;
    public static Object[] validResponse = null;
    List<Information> temp = new ArrayList<Information>();

    public EchoThread(Socket s1) {
        this.s1 = s1;

    }

    @Override
    public void run() {
        try {

            din = new ObjectInputStream(s1.getInputStream());

            Object[] response = null;
            int k = 1;
//            
            response = (Object[]) din.readObject();

            while (response != null) {

//                System.out.println("object found");
                validResponse = response;

                //get username and playerId from server
                if ((int) response[0] == 1) {
                    PlayerInfo.playerName = (String) response[1];
                    PlayerInfo.playerId = (int) response[2];
                    HomeController.palyerImagePath = Client.imagefile[PlayerInfo.playerId - 1];
                }
                // initial all gridcell responding to play
                if ((int) response[0] == 2) {

                    initialAll(response);
                }
                //stall other players until the turn is not comming
                if ((int) response[0] == 3 && (int) response[1] == 1) {

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            homeScreen.loadScreen(Client.screen3ID, Client.screen3file);
                            // playerName.setText(PlayerInfo.playerName);
                            homeScreen.setScreen(Client.screen3ID);
                            PlayingController.stateSatus.setStyle("-fx-background-color: red;");
                            PlayingController.turn = false;

                        }

                    });

                }
                //update the current state of grid all players 
                if ((int) response[0] == 4) {

                    updateInfo(response);

                }

                if ((int) response[0] == 5 || (int) response[0] == 10) {

                    int ind = 1;
                    for (int i = 0; response[ind] != null; i++) {
                        Client.Listname[i] = (String) response[ind++];
                        Client.result[i] = (int) response[ind++];
//                        System.out.println(Client.Listname[i] + "  " + Client.result[i]);

                    }
                    final int track = ind / 2;

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            if (playingScreen != null) {

                                playingScreen.loadScreen(Client.screen4ID, Client.screen4file);    //load the result screen
                                playingScreen.setScreen(Client.screen4ID);
                            } else if (homeScreen != null) {
                                homeScreen.loadScreen(Client.screen4ID, Client.screen4file);    //load the result screen
                                homeScreen.setScreen(Client.screen4ID);

                            }

                            ResultController.tempBoard.appendText("   Player Name    --------  points\n_____________________________________\n");

                            for (int i = 0; i < track; i++) {
                                String s = "" + (i + 1) + " : " + Client.Listname[i] + "------->(" + Client.result[i] + ")\n\n";
                                ResultController.tempBoard.appendText(s);
                            }

                        }

                    });
                }
                //disconnect client from server
                if ((int) response[0] == 6) {

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("The running server is closed");

                            alert.showAndWait();

                            if (PlayingController.playingScreen != null) {
                                playingScreen.setScreen(Client.screen2ID);
                            }
                        }

                    });
                    din.close();
                    dout.close();
                    ConnectionSetupController.dout.close();
                    ConnectionSetupController.din.close();
                    ConnectionSetupController.s1 = null;
                    response = null;
                    break;
                }

                if ((int) response[0] == 8) {

                    Object[] send = new Object[2];
                    send[0] = 5;                       //operation id=5 for show all player point 
                    ConnectionSetupController.dout.writeObject(send);
                    ConnectionSetupController.dout.flush();
                    ConnectionSetupController.dout.reset();
                }
                if ((int) response[0] == 9) {           //which player is left out of the game 
                    final String s = (String) response[1];
                    final int cnt = (int) response[2];

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            if (cnt > 1) {
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText(null);
                                alert.setContentText(s + " has left the game.");
                                alert.showAndWait();
                            } else if (cnt <= 1) {
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText("There are no more player to play.");
                                alert.setContentText("Are you want to quit?");

                                Optional<ButtonType> result = alert.showAndWait();

                                if (result.get() == ButtonType.OK) {
                                    PlayingController.playingScreen.setScreen(Client.screen2ID);
                                } else {
                                    // ... user chose CANCEL or closed the dialog
                                }
                            }

                        }

                    });

                }

                Thread.sleep(300);
                validResponse = null;

//                System.out.println("Server Response : " + response[0] + "  " + response[1] + " " + response[2]);

                response = (Object[]) din.readObject();
            }

        } catch (IOException ex) {
            // Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            // Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            // Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
    }

    public void initialAll(Object[] response) {

        if ((boolean) response[1] == true) {

            for (int i = 0; i < 21; i++) {
                for (int j = 0; j < 19; j++) {
                    HomeController.playingGrid[i][j] = 0;
                }
            }

            Random rand = new Random();
            int boom = rand.nextInt((15 - 5) + 1) + 5;

            for (int i = 0; i < boom; i++) {
                int x = rand.nextInt((21 - 0) + 1) + 0;
                int y = rand.nextInt((18 - 0) + 1) + 0;
//                System.out.println("X= " + x + "Y = " + y);
                HomeController.playingGrid[x][y] = 10;                //boom indicated
            }

            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    homeScreen.loadScreen(Client.screen3ID, Client.screen3file);
                    // playerName.setText(PlayerInfo.playerName);
                    homeScreen.setScreen(Client.screen3ID);
                    PlayingController.stateSatus.setStyle("-fx-background-color: green;");
                    PlayingController.turn = true;
                }

            });

        } else {

            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("There are no players to play");
                    alert.setContentText("one more player must be connected");
                    alert.showAndWait();

                }

            });
        }
    }

    public void updateInfo(Object[] response) {

        PlayingController.totalGridColored = (int) response[3];   //define how much grids have colored
        int index = 4;         //update from other client
        for (int i = 0; i < PlayingController.maxRow; i++) {
            for (int j = 0; j < PlayingController.maxCol; j++) {
                HomeController.playingGrid[i][j] = (int) response[index++];

            }

        }
      

       //
        temp.clear();
        for (int i = 0; i < PlayingController.maxRow; i++) {

            Image[] image = new Image[20];
            for (int j = 0; j < PlayingController.maxCol; j++) {

                try {
                    if (HomeController.playingGrid[i][j] == 0 || HomeController.playingGrid[i][j] == 10) {
                        image[j] = null;
                    } else if (HomeController.playingGrid[i][j] == 9) {
                        BufferedImage im = ImageIO.read(getClass().getClassLoader().getResource("ressources/blackcolor.png"));
                        image[j] = SwingFXUtils.toFXImage(im, null);

                    } else {
                        BufferedImage im = ImageIO.read(getClass().getClassLoader().getResource(Client.imagefile[HomeController.playingGrid[i][j]]));
                        image[j] = SwingFXUtils.toFXImage(im, null);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PlayingController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            Information info = new Information(image);
            temp.add(info);
        }

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                if ((int) response[1] == PlayerInfo.playerId) {
                    PlayingController.stateSatus.setStyle("-fx-background-color: green;");
                    PlayingController.turn = true;
                }
                try {
                    
                    PlayingController.data.removeAll(PlayingController.list);
                    PlayingController.tempTable.getItems().clear();
                    PlayingController.list = temp;
                    //PlayingController.data = FXCollections.observableArrayList(PlayingController.list); // create the data
                    //PlayingController.tempTable.setItems(PlayingController.data);
                    
                    PlayingController.tempTable.getItems().addAll(PlayingController.list);

                } catch (Exception e) {
                    System.out.println("hello word my ");
                }
            }

    
        });
        
    }
    

}
