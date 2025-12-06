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
    private Certificate certificate;
    private int score;

    // for creating a user
    /**
     * 
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     */
    public User(String username, String firstName, String lastName, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.progress = new Progress();
        this.inventory = new Inventory();
        this.certificate = new Certificate();
    }

    // for loading pre-existing user
    /**
     * 
     * @param id
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     * @param inventory
     * @param progress
     * @param certificate
     */
    public User(UUID id, String username, String firstName, String lastName, String password, Inventory inventory,
            Progress progress, Certificate certificate) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.progress = progress;
        this.inventory = inventory;
        this.progress = progress;
        this.certificate = certificate;
    }

    /**
     * 
     * @param userName
     * @param password
     * @return
     */
    public boolean isMatch(String userName, String password) {
        return this.username.equals(userName) && this.password.equals(password);
    }

    /**
     * loads game from save
     */
    public void loadGame() {
        for (String puzzleID : progress.getPuzzles()) {
            GameList.getInstance().findPuzzle(puzzleID).complete();
        }
    }

    /**
     * 
     * @return users username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @return String user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @return users last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return user ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * 
     * @return orientation of user
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * 
     * @return progress
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * 
     * @param clueID adds a clue to inventory
     */
    public void addClue(String clueID) {
        inventory.addClue(clueID);
    }

    /**
     * 
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * 
     * @param hintID adds hint to progress inventory and certificate
     */
    public void useHint(String hintID) {
        inventory.addHint(hintID);
        progress.addHint(hintID);
        certificate.useHint();
    }

    /**
     * adds a skip to certificate
     */
    public void useSkip() {
        certificate.useSkip();
    }

    /**
     * 
     * @return Certificate certificate
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * calculate score, starting from 1000 subtract seconds,
     * -100 for each hint used and -200 for each skip used
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCertificate(Certificate finalCertificate) {
        this.certificate = finalCertificate;
    }

    @Override
    public String toString() {
        return String.format("%s (%s %s)", username, firstName, lastName);
    }
}
