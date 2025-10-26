package com.model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<String> cluesID;
    private ArrayList<String> hintsID;

    public Inventory(){
        cluesID = new ArrayList<>();
        hintsID = new ArrayList<>();
    }

    public Inventory(ArrayList<String> clues, ArrayList<String> hints) {
        cluesID = clues;
        hintsID = hints;
    }

    public void addClue(String clueID){
        cluesID.add(clueID);
    }

    public void addHint(String hintID){
        hintsID.add(hintID);
    }
    public ArrayList<String> getClueIDs(){
        return cluesID;
    }
    public ArrayList<String> getHintIDS(){
        return hintsID;
    }

    public ArrayList<Clue> getClues(){
        ArrayList<Clue> clues = new ArrayList<>();
        GameList gameList = GameList.getInstance();
        for(String clueID : cluesID){
            clues.add(gameList.findClue(clueID));
        }
        return clues;
    }

    public ArrayList<Hint> getHints(){
        ArrayList<Hint> hints = new ArrayList<>();
        GameList gameList = GameList.getInstance();
        for(String hintID : hintsID){
            hints.add(gameList.findHint(hintID));
        }
        return hints;
    }
}
