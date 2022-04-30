package model;

import java.util.Observable;

/**
 *
 * @author salif
 */
public class Jeu extends Observable {
    private Grille baseGrid;
    public Niveau niveau;
    public Temps temps;
        
    private int minesLeft;
    private int hiddenTile;
    private boolean endOfGame;
    
    /**
     * Initial constructor
     * @param niveau 
     */
    public Jeu(Niveau niveau){
        this.niveau = niveau;
        this.temps = new Temps();
        init(niveau);
    }
    
    /**
     * Restart the game
     * @param niveau 
     */
    public void restart(Niveau niveau){
        this.niveau = niveau;
        this.temps.stop();//the timer is automaticly restartted at the next right click
        init(niveau); 
    }
    
    /**
     * Init the game
     * @param level 
     */
    private void init(Niveau niveau){
        this.baseGrid = new Grille(niveau.width, niveau.height, niveau.nbMines);
        this.minesLeft = niveau.nbMines;
        this.hiddenTile = niveau.width * niveau.height;
        this.endOfGame = false;
        
        Score.load();
    }
    
    /***
     * Update the state of the game after a right click on a tile.
     * @param posWidth
     * @param posHeight
     */
    public void rigthClick(int posWidth, int posHeight){      

        if(endOfGame){
            return;
        }

        Case clickedTile = baseGrid.getCase(posWidth, posHeight);
        if(clickedTile == null){
            //Tile not found do nothing
            return;
        }
        
        switch(clickedTile.getState()){
            case Case.STATE_SHOWN:
                //do nothing tile is already shown
                break;
            case Case.STATE_FLAG:
                clickedTile.setState(Case.STATE_HIDDEN);
                minesLeft++;
                hiddenTile++;
                break;
            case Case.STATE_HIDDEN:
                clickedTile.setState(Case.STATE_FLAG);
                minesLeft--;
                hiddenTile--;
                break;
            default:
                //state unknown, do nothing
        }
        setChanged();
        notifyObservers();
    }
    
    /***
     * Update the state of the tile after a right click
     * It also start the timer on the first click
     * @param posWidth
     * @param posHeight
     */
    public void leftClick(int posWidth, int posHeight){

        if(endOfGame){
            return;
        }

        Case clickedTile = baseGrid.getCase(posWidth, posHeight);
        if(clickedTile == null){
            //Tile not found do nothing
            return;
        }
        
        if(clickedTile.getState() != Case.STATE_HIDDEN){
            //The tile can be shown only if it is hidden
            return;
        }
                
        int nbReveledTile = clickedTile.reveal();
        
        if(nbReveledTile == -1) {
            endOfGame = true;
        } else {
            hiddenTile -= nbReveledTile;
        }
        
        
        if(endOfGame){
            //reveal all tiles
            for(int i = 0 ; i < niveau.width; i++){
                for(int j = 0 ; j < niveau.height; j++){
                    Case t = baseGrid.getCase(i, j);
                    if(t.getValue()!=Case.MINE){
                        continue;
                    }
                    if(t!=null){
                        t.reveal();
                    }
                }
            }
        }
        
        //update endOfGame
        if(hiddenTile == minesLeft){
            endOfGame = true;
        }
        
        this.temps.start();
        setChanged();
        notifyObservers();
        
        
    }
    
    /***
     * Return the actual game grid.
     * @return baseGrid
     */
    public Grille getBaseGrid() {
        return baseGrid;
    }
    
    /***
     * Return how many mines left
     * @return minesLeft
     */
    public int getMinesLeft() {
        return minesLeft;
    }
    
    /***
     * Check the end of the game and stop the timer if it is true
     * @return true if game is over. False else.
     */
    public boolean isEndOfGame() {
        if(endOfGame){
            temps.stop();
        }
        return endOfGame;
    }
    
    /***
     * Check if the player has won or lose.
     * @return true if the player has won. False else.
     */
    public boolean playerWin() {
        return isEndOfGame() && hiddenTile == minesLeft;
    }
}
