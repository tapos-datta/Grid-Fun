/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.ConnectionSetupController.playerId;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class HomeController implements Initializable,ControlledScreen {

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
    
    public static  String palyerImagePath="";
    public static int[][] playingGrid =new int[22][20];
            
    
    public static ScreenController homeScreen=null;
    
    
    @FXML
    public void playAction(ActionEvent e){
        
          try {
            System.out.println("etotuk paichi thread numbering ");

            Object[] obj = new Object[3];
            obj[0] = 2;
            obj[1] = ConnectionSetupController.playerId;
            

            ConnectionSetupController.dout.writeObject(obj);
            ConnectionSetupController.dout.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
         System.out.println("PlayerName :" + PlayerInfo.playerName);
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("hellosdfhaksdjfhaksjdfhkasjhf");
        call();
       
    }    
    
    private void call(){
        playerName.setText(PlayerInfo.playerName);
    }
    
    @Override
    public void setScreenParent(ScreenController screenPage) {
          homeScreen=screenPage;
          
    }
    
}
