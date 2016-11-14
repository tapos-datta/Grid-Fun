/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class HelpController implements Initializable,ControlledScreen {

    /**
     * Initializes the controller class.
     */
      public static ScreenController helpScreen;
      String text="                                        Constraints of GridFun\n"+
              "1. It's a multiplayer game but not more than four players.\n"+
              "2. All players must be connected through a single network.\n\n"+
              "                                              HOW TO PLAY\n"+
              "1. Considering all player are connected. If one player press 'play' button,other\n"+
              " players will be going to playing mode.\n"+
              "2. A player can choose a cell whose 'state' is marked as green. On the other\n"+
              " hand rest of the players will await for turn that means green state because of\n"+
              " their current state being red.\n"+
              "3. There are few hidden bombs are placed in the grid. If one player choose the\n"+
              " it unfortunately, he will loss some points.\n"+
              "4. If all grid cells are colored,there will open a view with results consisting\n"+
              " of player names and corresponding acquired points.\n";
              
              
              
      
      @FXML
      public TextArea help;
      
      @FXML
      public void backAction(ActionEvent e){
          helpScreen.setScreen(Client.screen2ID);
      }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        help.setEditable(false);
        help.appendText(text);
    }    

    @Override
    public void setScreenParent(ScreenController screenPage) {
      helpScreen=screenPage;
    }
    
}
