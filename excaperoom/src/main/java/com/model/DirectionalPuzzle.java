package com.model;

import java.util.ArrayList;

public class DirectionalPuzzle extends Puzzle{
    /**
     * 
     * @param clues
     * @param hints
     * @param answer enter answer as a string up, left, right or down with no spaces
     * ex upupdowndown
     */
    public DirectionalPuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer) {
        super(clues, hints, answer);

    }


    /**
     * 
     * @param answer accepts an array of directional moves
     * turns them into a string and calls the method enter answer from super class
     */
    public void enterAnswer(direction[] answer) {
        // TODO Auto-generated method stub
        String stringified = "";
        for(direction input : answer){
            stringified += input;
        }
        enterAnswer(stringified);
    }
    
}
