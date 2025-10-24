package com.model;

import java.util.ArrayList;

public class PhonePuzzle extends Puzzle {

    public PhonePuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID) {
        super(clues, hints, answer, completed, puzzleID);
    }

    public String enterGuess(String guess) {
        super.enterAnswer(guess);
        if (this.isCompleted()) {
            return "Correct!";
        } else {
            return "Incorrect, try again.";
        }
    }

    public String toString() {
        return "Phone Puzzle:\nEnter the correct phone number: ";
    }
}