package com.model;

import java.util.ArrayList;

public class MultipleChoice extends Puzzle {

    public MultipleChoice(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed) {
        super(clues, hints, answer, completed);
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
        return "Multiple Choice:\nEnter the correct letter: ";
    }
}