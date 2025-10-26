
package com.model;

import java.util.ArrayList;

public class Progress{
    ArrayList<String> skippedPuzzles;
    ArrayList<String> hintsUsed;
    ArrayList<String> puzzlesSolved;
    int puzzlesCompleted;
    int totalPuzzles;

    public Progress(){
        this.skippedPuzzles = new ArrayList<>();
        this.hintsUsed = new ArrayList<>();
        this.puzzlesSolved = new ArrayList<>();

    }
    public Progress(ArrayList<String> skipPuzzleID, ArrayList<String> usedHintsID, ArrayList<String> puzzleSolvedID){
        this.skippedPuzzles = (skipPuzzleID);
        this.hintsUsed = (usedHintsID);
        this.puzzlesSolved = (puzzleSolvedID);
        this.puzzlesCompleted = puzzleSolvedID.size();
    } 

    public void addHint(String hintID){
        hintsUsed.add(hintID);
    }

    public void addSkip(String skipID){
        skippedPuzzles.add(skipID);
    }

    public void completePuzzle(String puzzleID){
        puzzlesSolved.add(puzzleID);
    }

    public int displayProgressBar(){
        GameList gameList = GameList.getInstance();
        totalPuzzles = gameList.countPuzzles();
        int percent = 100 * (puzzlesCompleted/totalPuzzles);
        for(int i = 0; i < 100; i++){
            System.out.print("_");
        }
        System.out.println();
        for(int i = 0; i < percent; i++){
            System.out.print("=");
        }
        System.out.println();
        for(int i = 0; i < 100; i++){
            System.out.print("_");
        }
        System.out.println();
        return percent;
    }


}