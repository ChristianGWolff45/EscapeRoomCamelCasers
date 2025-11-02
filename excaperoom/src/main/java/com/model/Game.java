package com.model;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import com.speech.Speak;


public class Game {
    private User user;
    private Room currentRoom;
    //private Menu mainMenu;
    private final Duration timeAllowed = Duration.ofMinutes(30);
    private Instant startTime;
    private Certificate certificate;
    private HashMap<String, Hint> hintMap = new HashMap<>();
    private HashMap<String, Puzzle> puzzleMap = new HashMap<>();
    private HashMap<String, Clue> clueMap = new HashMap<>();
    private String name;
    private String story;
    private ArrayList<Room> roomList = new ArrayList<>();

    /**
     * 
     * @param name
     * @param user
     * @param startingRoom
     * @param hintMap
     * @param puzzleMap
     * @param clueMap
     * @param story
     * @param roomList
     */
    
    public Game(String name, User user, Room startingRoom, HashMap<String, Hint> hintMap, HashMap<String, Puzzle> puzzleMap, HashMap<String, Clue> clueMap, String story, ArrayList<Room> roomList){
        this.name = name;
        this.user = user;
        this.currentRoom = startingRoom;
        this.certificate = new Certificate();
        this.hintMap = hintMap;
        this.puzzleMap = puzzleMap;
        this.clueMap = clueMap;
        this.story = story;
        this.roomList = roomList;
    }

    public User getUser(){
        return user;
    }
    /**
     * 
     * @return current room of game
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }
    /**
     * 
     * @return certificate associated with game
     */
    public Certificate getCertificate(){
        return certificate;
    }
    /**
     * 
     * @return name of room
     */
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
    /**
     * 
     * starts the timer
     */
    public void Run(){
        startTime = Instant.now();
    }
    public void loadGame(){
    }
    /**
     * setsTimeTaken using timeElapsed function
     */
    public void endGame(){
        certificate.setTimeTaken(getTimeElapsed());
    }
    /**
     * 
     * @param puzzleID 
     * @return a puzzle using puzzleID
     */
    public Puzzle getPuzzle(String puzzleID){
        return puzzleMap.get(puzzleID);
    }
    /**
     * 
     * @param hintID string hint
     * @return finds and returns hint using hintID
     */
    public Hint getHint(String hintID){
        return hintMap.get(hintID);
    }
    /**
     * 
     * @param clueID String id for clue
     * @return a specific clue
     */
    public Clue getClue(String clueID){
        return clueMap.get(clueID);
    }
    /**
     * 
     * @return number of puzzles in game
     */
    public int getPuzzleCount(){
        return puzzleMap.size();
    }
    /**
     * prints out and reads story
     */
    public void playStory(){
        System.out.println(story);
        Speak.speak(story);
    }
    /**
     * 
     * @param roomID sets current room to roomID if it is unlocked
     */
    public void goNextRoom(String roomID){
        currentRoom = currentRoom.goNextRoom(roomID);
    }

   

}
