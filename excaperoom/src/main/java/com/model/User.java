package com.model;

import java.util.UUID;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private UUID id;
    private Progress progress;
    private Inventory inventory;
    private Orientation orientation;

    //for creating a user
    public User(String username, String firstName, String lastName, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.progress = new Progress();
        this.inventory = new Inventory();
    }

    //for loading pre-existing user
    public User(UUID id, String username, String firstName, String lastName, String password, Inventory inventory, Progress progress) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.progress = progress;
        this.inventory = inventory;
        this.progress = progress;
    }

    public boolean isMatch(String userName, String password) {
        return this.username.equals(userName) && this.password.equals(password);
    }

    public void loadGame(){
        for(String puzzleID : progress.getPuzzles()){
            GameList.getInstance().findPuzzle(puzzleID).complete();
        }
    }

    public String getUsername(){
        return username;
    }

     public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword(){
        return password;
    }

    public UUID getId(){
        return id;
    }

    public Orientation getOrientation(){
        return orientation;
    }

    public Progress getProgress(){
        return progress;
    }

    public void addClue(String clueID){
        inventory.addClue(clueID);
    }


    public Inventory getInventory(){
        return inventory;
    }

    public void useHint(String hintID){
        inventory.addHint(hintID);
        progress.addHint(hintID);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s %s)", username, firstName, lastName);
    }
}
