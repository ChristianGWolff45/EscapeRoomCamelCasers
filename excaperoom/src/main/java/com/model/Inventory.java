package com.model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Clue> clues;
    private ArrayList<Hint> hints;

    public Inventory(ArrayList<Clue> clues, ArrayList<Hint> hints) {
        this.clues = clues;
        this.hints = hints;
    }

    public ArrayList<Clue> getClues(){
        return clues;
    }

    public ArrayList<Hint> getHints(){
        return hints;
    }
}
