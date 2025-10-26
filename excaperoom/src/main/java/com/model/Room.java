package com.model;

import java.util.ArrayList;

public class Room {
    private String roomId;
    private String roomName;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<Room> nextRooms;
    private boolean unlocked;
    private boolean isExit;

    public Room(String roomId, String roomName, ArrayList<Puzzle> puzzles, ArrayList<Room> nextRooms, boolean unlocked,
        boolean isExit) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.puzzles = puzzles;
        this.nextRooms = nextRooms;
        this.unlocked = unlocked;
        this.isExit = isExit;
    }

    public String getName() {
        return roomName;
    }

    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void addNextRoom(Room room) {
        this.nextRooms.add(room);
    }

    public Room goNextRoom(String s) {
        for (Room neighbor : nextRooms) {
            if (neighbor.getName().equals(s)) {
                if (neighbor.unlocked) {
                    return neighbor;
                }
            }
        }
        return null;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public boolean unlockNeighbors() {
        System.out.println("unlocking rooms");
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

    public boolean isExit() {
        return isExit;
    }

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
