/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Tapos
 */
public class CommunicateObject implements Serializable{
     
    private static String name="";
    private static int playerId=-1;
    private static int operationId;
    private static int turn;

    public void setName(String n){
        this.name=n;
    }
    public String getName(){
        return this.name;
    }
    
    public void setPlayerId(int p){
        this.playerId=p;
    }
    
    public int getPlayerId(){
        return this.playerId;
    }
    
}
