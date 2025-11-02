package com.model;

import java.util.ArrayList;

public class MultipleChoice extends Puzzle {

    private ArrayList<String> options;
    /**
     * Constructor for MultipleChoice, utilizing superclass Puzzle
     * @param clues
     * @param hints
     * @param answer
     * @param completed
     */
    public MultipleChoice(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID, ArrayList<String> options) {
        super(clues, hints, answer, completed, puzzleID);
        this.options = options;
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
        String output = "Multiple Choice:\nEnter the correct letter:\n";
        for(int i = 0; i < options.size(); i++){
            output += (char)('A' + i) + ") " + options.get(i) + "   ";
        }
        return output;
    }
}