package com.model;

public class Clue {
    private String id;
    private String description;

    public Clue (String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getClueID(){
        return id;
    }

    public void displayClue() {
        System.out.println(id + ": " + description);
    }
    
}
