package com.model;

import com.speech.Speak;

public class Clue {
    private String id;
    private String description;

    /**
     * Constructor for Clue
     * @param id
     * @param description
     */
    public Clue (String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getClueID(){
        return id;
    }

    /**
     * Use TTS to speak clue description
     */
    public void hearClue() {
        Speak.speak(description);
    }

    /**
     * Display clue description
     */
    public void displayClue() {
        System.out.println(id + ": " + description);
    }

    public String getDescription(){
        return description;
    }
    
}
