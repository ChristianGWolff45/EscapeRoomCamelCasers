package com.model;

public class Hint {
    private String tip;
    private String id;

    public Hint(String tip, String id) {
        this.tip = tip;
        this.id = id;
    }

    public void hearHint() {

    }

    public void displayHint() {
        System.out.println(id + ": " + tip);
    }
    
}
