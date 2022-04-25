/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.LinkedList;

/**
 *
 * @author salif
 */
public class Case {
    //Values of the tiles
    public final static int MINE = -1;
    public final static int EMPTY = 0;
    //State of the tiles
    public final static int STATE_HIDDEN = 0;
    public final static int STATE_SHOWN = 1;
    public final static int STATE_FLAG = 2;
    
    private final int value;
    
    private int state;
    
    private final LinkedList<Case> voisins;
     
    /**
     * Constructor
     * @param value Value of the tile
     */
    public Case(int value){
        this.value = value;
        this.state = STATE_HIDDEN;
        voisins = new LinkedList<>();
    }
    
    /***
     * Return the value of the tile
     * @return value
     */
    public int getValue(){
        return this.value;
    }
    
    /***
     * Return the state of the tile
     * @return state
     */
    public int getState(){
        return this.state;
    }
    
    /***
     * Update the State of the tile
     * @param state 
     */
    public void setState(int state){
        this.state = state;
    }
    
    /***
     * Add a neighbour
     * @param neighbour 
     */
    public void addNeighbour(Case neighbour){
        voisins.add(neighbour);
    }
    
    /***
     * Reveal the Tile and his neighbour
     * @return number of revealed tiles
     */
    public int reveal(){
        
        int nbReveledTile = 0;
        
        if(this.state == Case.STATE_HIDDEN){
        
            this.state = Case.STATE_SHOWN;
            nbReveledTile++;

            if(this.value == EMPTY){
                for(Case voisin:voisins){
                    nbReveledTile += voisin.reveal();
                }
            } 
            if(this.value == MINE) {
                nbReveledTile = -1;
            }
        }        
        return nbReveledTile;
    }
}
