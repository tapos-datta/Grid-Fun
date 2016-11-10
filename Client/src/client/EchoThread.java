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
import common.CommunicateObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
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

                System.out.println("object found");
                validResponse = response;

                if ((int) response[0] == 1) {
                    PlayerInfo.playerName = (String) response[1];
                    PlayerInfo.playerId = (int) response[2];
                    HomeController.palyerImagePath = Client.imagefile[PlayerInfo.playerId - 1];
                }

                if ((int) response[0] == 2) {

                    initialAll(response);
                }

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

                if ((int) response[0] == 4) {

                    updateInfo(response);

                }
                
                if((int) response[0]==5){
                    
                   int ind=1;
                    for(int i=0;response[ind]!=null;i++){
                        Client.Listname[i]=(String) response[ind++];
                        Client.result[i]=(int) response[ind++];
                        System.out.println(Client.Listname[i] + "  "+ Client.result[i]);
                        
                    }
                   final int track = ind/2; 
                    
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            
                             playingScreen.loadScreen(Client.screen4ID, Client.screen4file);    //load the result screen
                              playingScreen.setScreen(Client.screen4ID);
                            
                           
                            ResultController.tempBoard.appendText("   Player Name    --------  points\n_____________________________________\n");
                            
                            for(int i=0;i<track;i++)
                            {
                                String s=""+(i+1)+" : "+Client.Listname[i]+"------->("+Client.result[i]+")\n\n";
                                ResultController.tempBoard.appendText(s);
                            }

                        }

                    });
                    
                    
                    
                }

                Thread.sleep(200);
                validResponse = null;

                System.out.println("Server Response : " + response[0] + "  " + response[1] + " " + response[2]);
                
                if(ConnectionSetupController.s1==null){
                    din.close();
                    dout.close();
                    response=null;
                }

                response = (Object[]) din.readObject();
            }

        } catch (IOException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
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
                System.out.println("X= " + x + "Y = " + y);
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

            //   popup window for two player needed
        }
    }

    public void updateInfo(Object[] response) {
        
        PlayingController.totalGridColored=(int) response[3];   //define how much grids have colored
        int index = 4;         //update from other client
        for (int i = 0; i < PlayingController.maxRow; i++) {
            for (int j = 0; j < PlayingController.maxCol; j++) {
                HomeController.playingGrid[i][j] = (int) response[index++];

            }

        }

        List<Information> temp = new ArrayList<Information>();

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

                PlayingController.list = temp;
                PlayingController.data = FXCollections.observableArrayList(PlayingController.list); // create the data
                PlayingController.tempTable.setItems(PlayingController.data);

            }

        });
    }

}
