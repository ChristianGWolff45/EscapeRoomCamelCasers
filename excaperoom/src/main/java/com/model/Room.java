package com.model;

import java.util.ArrayList;

public class Room {
    private String roomId;
    private String roomName;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<Room> nextRooms;
    private boolean unlocked;
    private boolean isExit;

    /**
     * 
     * @param roomId
     * @param roomName
     * @param puzzles
     * @param nextRooms
     * @param unlocked
     * @param isExit
     */
    public Room(String roomId, String roomName, ArrayList<Puzzle> puzzles, ArrayList<Room> nextRooms, boolean unlocked,
        boolean isExit) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.puzzles = puzzles;
        this.nextRooms = nextRooms;
        this.unlocked = unlocked;
        this.isExit = isExit;
    }
    /**
     * 
     * @return string name of room
     */
    public String getName() {
        return roomName;
    }
    /**
     * 
     * @return arrayList of type puzzle in the room
     */
    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }
    /**
     * 
     * @param room adds a new room obj to neighbors
     */
    public void addNextRoom(Room room) {
        this.nextRooms.add(room);
    }
    /**
     * 
     * @return string roomName
     */
    public String getRoomName() {
        return roomName;
    }
    /**
     * 
     * @param s name of room trying to enter
     * @return neighbor if neighbor s is unlocked
     */
    public Room goNextRoom(String s) {
        for (Room neighbor : nextRooms) {
            if (neighbor.getName().equals(s)) {
                if (neighbor.unlocked) {
                    return neighbor;
                }
            }
        }
        return this;
    }
    /**
     * 
     * @return boolean true if room is unlocked
     */
    public boolean isUnlocked() {
        return unlocked;
    }
    /**
     * 
     * @return true if neighbors unlocked
     */
    public boolean unlockNeighbors() {
        for(Puzzle puzzle : puzzles){
            if(!puzzle.isCompleted()){
                return false;
            }
        }
        for (Room neighbor : nextRooms) {
            neighbor.unlocked = true;
            System.out.println(neighbor.getName() + " is unlocked");
        }
        return true;
    }
    /**
     * 
     * @return true if room is exit
     */
    public boolean isExit() {
        return isExit;
    }
    /**
     * prints out string of room including puzzles clues hints
     */
    public void printRoom(){
        System.out.println(roomName);
        for(Puzzle puzzle : puzzles){
            System.out.println(puzzle.getID());
            for(Clue clue : puzzle.getClues()){
                System.out.println(clue.getClueID());
            }
            for(Hint hint : puzzle.getHints()){
                System.out.println(hint.getHintID());
            }

        }
        for(Room room : nextRooms){
            System.out.println(room.getName());
            System.out.println(room.isUnlocked() ? "unlocked" : "locked");
        }

    }
}
