package com.model;

import java.util.ArrayList;

public class Room {
    private String roomName;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<Room> nextRooms;
    private boolean unlocked;
    private Orientation orientation;

    public Room(String roomName, ArrayList<Puzzle> puzzles, ArrayList<Room> nextRooms, boolean unlocked, Orientation orientation) {
        this.roomName = roomName;
        this.puzzles = puzzles;
        this.nextRooms = nextRooms;
        this.unlocked = unlocked;
        this.orientation = orientation;
    }

    public void goNextRoom(String s) {
        
    }

    public boolean isUnlocked() {
        return true;
    }

    public void unlock(String s) {

    }

    public void isLastRoom() {
        
    }
}
