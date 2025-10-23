package com.model;

import java.util.ArrayList;

public class PhonePuzzle extends Puzzle {

    /**
     * Constructor for PhonePuzzle, utilizing superclass Puzzle
     * @param clues
     * @param hints
     * @param answer
     * @param completed
     */
    public PhonePuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed) {
        super(clues, hints, answer, completed);
    }

    /**
     * Use puzzle's enterAnswer to check if guess is correct, then return appropriate response
     * @param guess
     * @return
     */
    public String enterGuess(String guess) {
        super.enterAnswer(guess);
        if (this.isCompleted()) {
            return "Correct!";
        } else {
            return "Incorrect, try again.";
        }
    }

    /**
     * Prompt question for PhonePuzzle
     */
    public String toString() {
        return "Phone Puzzle:\nEnter the correct phone number: ";
    }
}