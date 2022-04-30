package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Score implements Serializable {
    
    public static String scorePath = System.getProperty("./")+"Save.sav";
    public static ScoreSave [] bestScore;

    static{
        bestScore = new ScoreSave[3];
        for(int i = 0 ; i < bestScore.length; i++){
            bestScore[i] = new ScoreSave();
        }
    }
    
    private static class ScoreSave implements Serializable{
        public int score;
        public int time;
    }
    
    /**
     * Return the score of the game
     * @param game
     * @return int Score value
     */
    public static int getScore(Jeu game) {
        int score = 0;
        
        for(int i = 0; i < game.niveau.width; i++){
            for(int j = 0; j < game.niveau.height; j++) {
                
                Case t = game.getBaseGrid().getCase(i, j);
                
                switch (t.getState()) {
                    
                    case Case.STATE_FLAG :
                        if(t.getValue() == Case.MINE) {
                            score += 10;
                        } else {
                            score -= 5;
                        }
                        break;
                        
                    case Case.STATE_HIDDEN :
                        //do nothing
                        break;
                        
                    case Case.STATE_SHOWN :
                        if(t.getValue() == Case.MINE) {
                            //do nothing
                            //the player loose
                        } else {
                            score++;
                        }
                        break;
                        
                    default :
                }
            }
        }
        
        if(score > bestScore[game.niveau.id].score ){
            bestScore[game.niveau.id].score = score;
            bestScore[game.niveau.id].time = game.temps.getTime();
        } else if( score == bestScore[game.niveau.id].score  && game.temps.getTime() < bestScore[game.niveau.id].time )  {
            bestScore[game.niveau.id].time = game.temps.getTime();
        }    
        return score;
    }
    
    public static void save(){
        try {
            //clean file
            File f = new File(scorePath);
            if(f.exists()){
                f.delete();
            }
            FileOutputStream fout = new FileOutputStream(scorePath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(bestScore);
            fout.close();
        } catch (IOException ex) {
            System.out.println("Erreur lors de l'enregistrement des scores.");
        }
    }
    
    public static void load(){
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(scorePath);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Object obj = ois.readObject();
            if(obj instanceof ScoreSave[]){
                bestScore = (ScoreSave [])obj;
            }else{
                save();
            }           
            fin.close();
        } catch (FileNotFoundException ex) {
            //System.out.println("File not found");
        } catch (IOException ex) {
            //System.out.println("IO error");
        } catch (ClassNotFoundException ex) {
            //System.out.println("Class not found");
        }
    }
  
    public static int getBestScore(Niveau level){
        return bestScore[level.id].score;
    }
    
    public static int getBestTime(Niveau level){
        return bestScore[level.id].time;
    }
    
}
