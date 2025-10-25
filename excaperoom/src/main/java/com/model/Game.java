package com.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import com.speech.Speak;


public class Game {
    private User user;
    private Room startingRoom;
    //private Menu mainMenu;
    private final Duration timeAllowed = Duration.ofMinutes(30);
    private Instant startTime;
    private Certificate certificate;
    private HashMap<String, Hint> hintMap = new HashMap<>();
    private HashMap<String, Puzzle> puzzleMap = new HashMap<>();
    private HashMap<String, Clue> clueMap = new HashMap<>();
    private String name;
    private String story;


    
    public Game(String name, User user, Room startingRoom, HashMap<String, Hint> hintMap, HashMap<String, Puzzle> puzzleMap, HashMap<String, Clue> clueMap, String story){
        this.name = name;
        this.user = user;
        this.startingRoom = startingRoom;
        this.certificate = new Certificate();
        this.hintMap = hintMap;
        this.puzzleMap = puzzleMap;
        this.clueMap = clueMap;
        this.story = story;
    }

    public User getUser(){
        return user;
    }
    public Room getStartingRoom(){
        return startingRoom;
    }

    public Certificate getCertificate(){
        return certificate;
    }
    public String getName(){
        return this.name;
    }
    /**
     * 
     * @return time allowed minus time elapsed to get time left
     */
    public Duration getTimeLeft(){
        if (startTime == null) return timeAllowed;
        return timeAllowed.minus(getTimeElapsed());
    }
    /**
     * 
     * @return Duration of Time now minus start time
     */
    public Duration getTimeElapsed(){
        if (startTime == null) return timeAllowed;
        return Duration.between(startTime, Instant.now());
    }
    /**
     * 
     * @return a formatted version of time left MM:SS
     */
    public String timeLeftMMSS(){
        long totalSeconds = getTimeLeft().getSeconds();
        long minutes = totalSeconds/60;
        long seconds = totalSeconds%60;
        return String.format("%02d:%02d", minutes, seconds);

    }
    public void Run(){
        startTime = Instant.now();
    }
    public void loadGame(){
    }
    public void endGame(){
        certificate.setTimeTaken(getTimeElapsed());
    }
    
    public Puzzle getPuzzle(String puzzleID){
        return puzzleMap.get(puzzleID);
    }

    public Hint getHint(String hintID){
        return hintMap.get(hintID);
    }

    public Clue getClue(String clueID){
        return clueMap.get(clueID);
    }

    public int getPuzzleCount(){
        return puzzleMap.size();
    }

    public void playStory(){
        System.out.println(story);
        Speak.speak(story);
    }

}
