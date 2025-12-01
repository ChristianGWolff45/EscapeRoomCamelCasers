package com.model;

import com.speech.Speak;

public class Hint {
    private String tip;
    private String id;
    private String puzzleID;

    /**
     * Constructor for Hint
     * @param tip
     * @param id
     */
    public Hint(String tip, String id, String puzzleID) {
        this.tip = tip;
        this.id = id;
        this.puzzleID = puzzleID;
    }

    public String getHintID(){
        return id;
    }
    /**
     * hear puzzle description
     */
    public void hearHint() {
        Speak.speak(tip);
    }
    /**
     * 
     * @return puzzleID
     */
    public String getPuzzleID() {
        return puzzleID;
    }

    /**
     * Display hint tip
     */
    public String displayHint() {
        return (id + ": " + tip);
    }

    public String getDescription() {
        return tip;
    }
    
}
