
package com.model;

import java.util.ArrayList;

public class Progress{
    ArrayList<Puzzle> skippedPuzzles;
    ArrayList<Hint> hintsUsed;
    ArrayList<Puzzle> puzzlesSolved;
    int puzzlesCompleted;
    int totalPuzzles;

    public Progress(ArrayList<String> skipPuzzleID, ArrayList<String> usedHintsID, ArrayList<String> puzzleSolvedID){
        for(String puzzleID : skipPuzzleID){
            skippedPuzzles.add(GameList.findPuzzle(puzzleID));
        }
        for(String hintID : usedHintsID){
            hintsUsed.add(GameList.findHint(hintID));
        }
        for(String puzzleID : puzzleSolvedID){
            puzzlesSolved.add(GameList.findPuzzle(puzzleID));
        }
        this.puzzlesCompleted = puzzleSolvedID.size();
        totalPuzzles = GameList.countPuzzles();
    } 

    public int displayProgressBar(){
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