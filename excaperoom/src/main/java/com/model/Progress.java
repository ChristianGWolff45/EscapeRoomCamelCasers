
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
        puzzlesCompleted = 0;
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
        puzzlesCompleted += 1;
    }

    public ArrayList<String> getSkips(){
        return skippedPuzzles;
    }

    public ArrayList<String> getPuzzles(){
        return puzzlesSolved;
    }

    public ArrayList<String> getHints(){
        return hintsUsed;
    }

    public void displayProgress(){
        int percent  = displayProgressBar();
        System.out.println("You have solved " + puzzlesCompleted + " out of " + totalPuzzles + " puzzles!" + " You are " + percent + "% of the way done!");
        System.out.print("\nPuzzles Solved: ");
        for(String puzzle : puzzlesSolved){
            System.out.print(puzzle + ", ");
        }
        System.out.print("\nYou have skipped the following puzzles: ");
        for(String puzzle : skippedPuzzles){
            System.out.print(puzzle + ", ");
        }
        System.out.print("\nYou have used the following hints: ");
        for(String hint : hintsUsed){
            System.out.print(hint + " on the puzzle ");
            System.out.print(GameList.getInstance().findHint(hint).getPuzzleID() + ", ");
        }
    }


    public int displayProgressBar(){
        GameList gameList = GameList.getInstance();
        totalPuzzles = gameList.countPuzzles();
        int percent = (int) ((int) 100 *  ((double)puzzlesCompleted/(double)totalPuzzles));
        for(int i = 0; i < 100; i++){
            System.out.print("_");
        }
        System.out.println();
        for(int i = 0; i < percent; i++){
            System.out.print("=");
        }
        System.out.print(percent + "%");
        System.out.println();
        for(int i = 0; i < 100; i++){
            System.out.print("_");
        }
        System.out.println();
        return percent;
    }


}