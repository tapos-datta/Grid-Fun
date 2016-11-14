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
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tapos
 */
public class EchoThread extends Thread {

    protected int playerId;
    protected String playerName;

    protected Socket socket = null;
    ObjectInputStream din = null;
    ObjectOutputStream dout = null;
    private Object[] response;
    public int score = 0;

    public EchoThread(Socket clientSocket, int playerId) {
        this.socket = clientSocket;
        this.playerId = playerId;

    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void run() {
        

        try {
            din = new ObjectInputStream(socket.getInputStream());
            dout = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            return;
        }

        // CommunicateObject receive=new CommunicateObject();
        int flag = 0;
        int k = 1;
        while (k == 1 && din != null) {

       
            try {
                Object[] receive = (Object[]) din.readObject();

                if ((int) receive[0] == 2) {

                    response = new Object[3];
                    response[0] = 2;

                    if (HomeController.playerNumber > 1) {
                        WaitforTurn((int) receive[1]);
                        response[1] = true;

                        for (int i = 1; i < HomeController.playerNumber; i++) {
                            HomeController.nextPlayer[i] = i + 1;
                        }

                        HomeController.nextPlayer[HomeController.playerNumber] = 1;

                    } else {
                        response[1] = false;
                    }
                    dout.writeObject(response);
                    dout.flush();
                    dout.reset();

                }

                if ((int) receive[0] == 1) {
                    HomeController.name[playerId] = (String) receive[1];
                    this.playerName = (String) receive[1];
                    receive[2] = (int) playerId;
                    HomeController.playerNumber++;
                    flag = 1;
                    dout.writeObject(receive);
                    dout.flush();
                    dout.reset();
                }

                //updateMessage
                if ((int) receive[0] == 4) {
                    updateProcess(receive);

                }

                if ((int) receive[0] == 5 || (int) receive[0] == 10) {
                    //final point show
                    showResult(receive);
                }

                if ((int) receive[0] == 7) {

                    HomeController.alive[playerId] = false;
                    response = new Object[3];
                    response[0] = 9;                       //identify which player left
                    response[1] = playerName;
                    int cnt = 0;
                    for (int p = 1; p < 5; p++) {
                        if (HomeController.alive[p] == true) {
                            cnt++;
                        }
                    }
                    if (cnt >= 1) {
                        response[2] = cnt;
                    }

                    for (EchoThread t : HomeController.clients) {
                        if (t.playerId != playerId) {
                            t.updateMessage(response);
                        }
                    }

                }

//                    System.out.println(receive[0]+ " " +receive[1] + "  "+ receive[2]);
//                 dout.reset();
            } catch (IOException ex) {
                k = 0;
                Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public static void WaitforTurn(int k) {

        for (EchoThread t : HomeController.clients) {
            if (t.playerId != k) {
                t.sendStallSignal();
            }
        }

    }

    private void sendStallSignal() {
        try {
            HomeController.noMorePlayer = true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            Object[] ob = new Object[3];
            ob[0] = 3;   //stall operation
            ob[1] = 1; //stall signal 
            ob[2] = -1;

            dout.writeObject(ob);
            dout.flush();
            dout.reset();

        } catch (IOException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMessage(Object[] response) {

        try {
            if (dout != null) {
                dout.writeObject(response);
                dout.flush();
                dout.reset();
            }
        } catch (IOException ex) {
            //
        }

    }

    private void updateProcess(Object[] receive) {
        int player = (int) receive[1];
        int nextPlayer = HomeController.nextPlayer[(int) receive[1]];

        for (int i = 0; i < 5; i++) {
            if (HomeController.alive[nextPlayer] == false) {
                nextPlayer = HomeController.nextPlayer[nextPlayer];
                continue;
            } else {

                break;
            }
        }

        if (nextPlayer == (int) receive[1]) {

            response = new Object[2];
            response[0] = 8;          //only one player found
            updateMessage(response);

        } else {

            score = (int) receive[2];

            response = new Object[425];

            response = receive;
            response[1] = nextPlayer;

            for (EchoThread t : HomeController.clients) {
                if (t.playerId != player) {

                    t.updateMessage(response);

                }
            }
        }

    }

    void showResult(Object[] receive) {

        String[] nameList = new String[5];
        int[] result = new int[5];
        int k = 0;
        for (EchoThread t : HomeController.clients) {
            nameList[k] = t.playerName;
            result[k] = t.score;
            t.score = 0;                            //clear all player point
            k++;
        }

        //sort result list with maximum point
        for (int i = 0; i < k - 1; i++) {
            for (int j = 1; j < k; j++) {

                if (result[i] < result[j]) {

                    int temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;

                    String tempName = nameList[i];
                    nameList[i] = nameList[j];
                    nameList[j] = tempName;
                }

            }
        }

        response = new Object[10];
        response[0] = 5;              //response for showing result

        for (int i = 0, ind = 1; i < k; i++) {
            response[ind++] = nameList[i];
            response[ind++] = result[i];
        }

        if ((int) receive[0] == 10) {
            updateMessage(response);
            
        } else {

            for (EchoThread t : HomeController.clients) {
                t.updateMessage(response);
            }
        }

    }

    public void stopMonitoring() {

        try {
            din.close();
            dout.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
