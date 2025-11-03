
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
    /**
     * 
     * @param skipPuzzleID arrayList string of all puzzles skiped
     * @param usedHintsID   arrayList string of all hints used
     * @param puzzleSolvedID arrayList string of all puzzlesSolved
     */
    public Progress(ArrayList<String> skipPuzzleID, ArrayList<String> usedHintsID, ArrayList<String> puzzleSolvedID){
        this.skippedPuzzles = (skipPuzzleID);
        this.hintsUsed = (usedHintsID);
        this.puzzlesSolved = (puzzleSolvedID);
        this.puzzlesCompleted = puzzleSolvedID.size();
    } 
    /**
     * 
     * @param hintID adds a new hint
     */
    public void addHint(String hintID){
        hintsUsed.add(hintID);
    }
    /**
     * 
     * @param skipID adds a new skip
     */
    public void addSkip(String skipID){
        skippedPuzzles.add(skipID);
    }
    /**
     * 
     * @param puzzleID adds a new solved puzzle
     */
    public void completePuzzle(String puzzleID){
        puzzlesSolved.add(puzzleID);
        puzzlesCompleted += 1;
    }
    /**
     * 
     * @return list of skiped puzzle ids
     */
    public ArrayList<String> getSkips(){
        return skippedPuzzles;
    }
    /**
     * 
     * @return list of all puzzles
     */
    public ArrayList<String> getPuzzles(){
        return puzzlesSolved;
    }
    /**
     * 
     * @return list of all hints used
     */
    public ArrayList<String> getHints(){
        return hintsUsed;
    }
    /**
     * prints progress bar
     * prints num puzzles completed
     * which puzzles were completed
     * which puzzles were skiped
     * and what hints were used
     */
    public void displayProgress(){
        int percent = displayProgressBar();
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

    /**
     * 
     * @return int percent completed
     */
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