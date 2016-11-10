/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class ResultController implements Initializable,ControlledScreen {

    /**
     * Initializes the controller class.
     */
    public static ScreenController resultScreen;
    
    public static TextArea tempBoard;
    
    @FXML
    TextArea resultBoard;
    @FXML
    Label resultLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tempBoard=resultBoard;
        resultBoard.setEditable(false);
        
    } 
    
    

    @Override
    public void setScreenParent(ScreenController screenPage) {
        resultScreen = screenPage;
    }
    
}
