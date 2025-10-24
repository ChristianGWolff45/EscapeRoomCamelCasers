package com.model;

import java.util.ArrayList;

public class MultipleChoice extends Puzzle {

    /**
     * Constructor for MultipleChoice, utilizing superclass Puzzle
     * @param clues
     * @param hints
     * @param answer
     * @param completed
     */
    public MultipleChoice(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID) {
        super(clues, hints, answer, completed, puzzleID);
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
     * Prompt question for MultipleChoice
     */
    public String toString() {
        return "Multiple Choice:\nEnter the correct letter: ";
    }
}