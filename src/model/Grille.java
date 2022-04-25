package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Mady
 */
public class Grille {
    private final int height;
    private final int width;
    private final int nbMines;
    
    private Case[][] grid;
    
    /**
     * Constructor
     * @param width Width of the grid
     * @param height Height of the grid
     * @param nbMines Number of mine on the grid
     */
    public Grille(int width, int height, int nbMines){
        this.height = height;
        this.width = width;
        if(nbMines > height * width){
            this.nbMines = height * width;
        }else if (nbMines < 1){
            this.nbMines = 1;
        }else{
            this.nbMines = nbMines;
        }
        initGrid();
    }
    
    /***
     * Init the game grid using a random generator
     */
    private void initGrid(){
        
        Case [][] newGrid = new Case[width][height];

        //Set mines position
        ArrayList<Integer> freeSpace = new ArrayList<>();
        for(int i = 0 ; i < this.width * this.height; i++){
            freeSpace.add(i);
        }
        
        Random r = new Random();
        for(int i = 0 ; i < this.nbMines; i++){
            int mineIndex = r.nextInt(freeSpace.size());
            int minePos = freeSpace.get(mineIndex);
            freeSpace.remove(mineIndex);
            newGrid[minePos / this.height][minePos % this.height] = new Case(Case.MINE);
        }
        
        //Set values of the tile
        for(int i = 0 ; i < this.width; i++){
            for(int j = 0 ; j < this.height; j++){
                
                if(newGrid[i][j] != null && newGrid[i][j].getValue() == Case.MINE){
                    //If the tile is a mine or is already defined, do nothing
                    continue;
                }
                
                LinkedList<Case> caseVoisine = new LinkedList<>();
                
                int nbrMines = 0;
                //upper left
                if(!(i-1<0 || j-1<0)){
                    if(newGrid[i-1][j-1]!= null && newGrid[i-1][j-1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //upper center
                if(!(j-1<0)){
                    if(newGrid[i][j-1]!= null && newGrid[i][j-1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //upper right
                if(!(i+1>= this.width || j-1<0)){
                    if(newGrid[i+1][j-1]!= null && newGrid[i+1][j-1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //middle left
                if(!(i-1<0)){
                    if(newGrid[i-1][j]!= null && newGrid[i-1][j].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //middle right
                if(!(i+1>=this.width)){
                    if(newGrid[i+1][j]!= null && newGrid[i+1][j].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //bottom left
                if(!(i-1<0 || j+1>=this.height)){
                    if(newGrid[i-1][j+1]!= null && newGrid[i-1][j+1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //bottom center
                if(!(j+1>=this.height)){
                    if(newGrid[i][j+1]!= null && newGrid[i][j+1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                //bottom right
                if(!(i+1>=this.width || j+1>=this.height)){
                    if(newGrid[i+1][j+1]!= null && newGrid[i+1][j+1].getValue()==Case.MINE){
                        nbrMines ++;
                    }
                }
                
                newGrid[i][j] = new Case(nbrMines);
            }
        }
        
        //create a neigbourg list
        for(int i = 0 ; i < this.width; i++){
            for(int j = 0 ; j < this.height; j++){
                
                if(newGrid[i][j] != null && newGrid[i][j].getValue() == Case.MINE){
                    //If the Tile is a mone or already defined, do nothing
                    continue;
                }
                
                LinkedList<Case> caseVoisine = new LinkedList<>();
                
                //upper left
                if(!(i-1<0 || j-1<0)){
                    caseVoisine.add(newGrid[i-1][j-1]);
                }
                //upper center
                if(!(j-1<0)){
                    caseVoisine.add(newGrid[i][j-1]);
                }
                //upper right
                if(!(i+1>= this.width || j-1<0)){
                    caseVoisine.add(newGrid[i+1][j-1]);
                }
                //middle left
                if(!(i-1<0)){
                    caseVoisine.add(newGrid[i-1][j]);
                }
                //middle right
                if(!(i+1>=this.width)){
                    caseVoisine.add(newGrid[i+1][j]);
                }
                //bottom left
                if(!(i-1<0 || j+1>=this.height)){
                    caseVoisine.add(newGrid[i-1][j+1]);
                }
                //bottom center
                if(!(j+1>=this.height)){
                    caseVoisine.add(newGrid[i][j+1]);
                }
                //bottom right
                if(!(i+1>=this.width || j+1>=this.height)){
                    caseVoisine.add(newGrid[i+1][j+1]);
                }
                
                for(Case voisin: caseVoisine){
                    newGrid[i][j].addNeighbour(voisin);
                }
            }
        }
        
        this.grid = newGrid;
    }
    
    /***
     * Return (if exists) the tile selected. 
     * @param posWidth Width position of the tile
     * @param posHeight Height position of the Tile
     * @return Tile selected or null if tile doesn't exists
     */
    public Case getCase(int posWidth, int posHeight){
        if(posWidth >= this.grid.length){
            return null;
        }
        if(posWidth < 0){
            return null;
        }
        
        if(posHeight >= this.grid[0].length){
            return null;
        }
        if(posHeight < 0){
            return null;
        }
        return this.grid[posWidth][posHeight];
    }
}
