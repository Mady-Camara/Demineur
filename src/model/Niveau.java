package model;

/**
 *
 * @author salif
 */
public class Niveau {
    public static Niveau FACILE = new Niveau("FACILE",0,9,9,10);
    public static Niveau INTERMEDIAIRE = new Niveau("INTERMEDIAIRE",1,14,14,40);
    public static Niveau EXPERT = new Niveau("EXPERT",2,16,30,99);
    
    public String name;
    public int id;
    public int height;
    public int width;
    public int nbMines;
    public int time;
    
    /**
     * Constuctor
     * @param name Name of the level
     * @param id Id of the level (to retreive score)
     * @param width Width of the level
     * @param height Height of the level
     * @param nbMines Number of mines of the level
     */
    public Niveau(String name,int id, int width, int height, int nbMines){
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.nbMines = nbMines;
    }
}
