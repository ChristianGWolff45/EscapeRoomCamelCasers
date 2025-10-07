package com.model;

import java.util.ArrayList;

public class Room {
    private String roomName;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<Room> nextRooms;
    private boolean unlocked;
    private boolean isExit;


    public Room(String roomName, ArrayList<Puzzle> puzzles, ArrayList<Room> nextRooms, boolean unlocked, boolean isExit) {
        this.roomName = roomName;
        this.puzzles = puzzles;
        this.nextRooms = nextRooms;
        this.unlocked = unlocked;
        this.isExit = isExit;
    }

    public String getName(){
        return roomName;
    }

    public ArrayList<Puzzle> getPuzzles(){
        return puzzles;
    }


    public Room goNextRoom(String s) {
        for(Room neighbor : nextRooms){
            if(neighbor.getName().equals(s)){
                if(neighbor.unlocked){
                    return neighbor;
                }
            }
        }
        return null;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock(String s) {
        for(Room neighbor : nextRooms){
            if(neighbor.getName().equals(s)){
                neighbor.unlocked = true;
            }
        }
    }

    public boolean isExit() {
        return isExit;
    }
}
