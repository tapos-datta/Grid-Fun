/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javafx.scene.control.Label;

/**
 *
 * @author Tapos
 */
public class Indexing {
    
    private int row=0;
    private int col=0;
    public Label label; 
    
    Indexing(Label l, int row,int col){
        this.label=l;
        this.row=row;
        this.col=col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public void setColumn(int col) {
        this.col = col;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
    
    
    
    
}
