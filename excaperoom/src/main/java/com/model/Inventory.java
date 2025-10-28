package com.model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<String> cluesID;
    private ArrayList<String> hintsID;

    public Inventory(){
        cluesID = new ArrayList<>();
        hintsID = new ArrayList<>();
    }
    /**
     * 
     * @param clues arrayList of string CluesID
     * @param hints arrayList of string HintsID
     */
    public Inventory(ArrayList<String> clues, ArrayList<String> hints) {
        cluesID = clues;
        hintsID = hints;
    }
    /**
     *  
     * @param clueID add a new clueID
     */
    public void addClue(String clueID){
        cluesID.add(clueID);
    }
    /**
     * 
     * @param hintID add a new hint
     */
    public void addHint(String hintID){
        hintsID.add(hintID);
    }
    /**
     * 
     * @return clues
     */
    public ArrayList<String> getClueIDs(){
        return cluesID;
    }
    /**
     * 
     * @return hints
     */
    public ArrayList<String> getHintIDS(){
        return hintsID;
    }
    /**
     * 
     * @return ArrayList of clues
     */
    public ArrayList<Clue> getClues(){
        ArrayList<Clue> clues = new ArrayList<>();
        GameList gameList = GameList.getInstance();
        for(String clueID : cluesID){
            clues.add(gameList.findClue(clueID));
        }
        return clues;
    }
    /**
     * 
     * @return ArrayList of Hints
     */
    public ArrayList<Hint> getHints(){
        ArrayList<Hint> hints = new ArrayList<>();
        GameList gameList = GameList.getInstance();
        for(String hintID : hintsID){
            hints.add(gameList.findHint(hintID));
        }
        return hints;
    }
}
