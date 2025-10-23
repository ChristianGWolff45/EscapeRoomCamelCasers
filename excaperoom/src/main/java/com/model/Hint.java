package com.model;

import com.speech.Speak;

public class Hint {
    private String tip;
    private String id;

    /**
     * Constructor for Hint
     * @param tip
     * @param id
     */
    public Hint(String tip, String id) {
        this.tip = tip;
        this.id = id;
    }

    /**
     * Use TTS to speak hint tip
     */
    public void hearHint() {
        Speak.speak(tip);
    }

    /**
     * Display hint tip
     */
    public void displayHint() {
        System.out.println(id + ": " + tip);
    }
    
}
