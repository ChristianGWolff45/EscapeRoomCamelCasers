package com.model;

import java.util.ArrayList;

public class Puzzle {
    private ArrayList<Clue> clues;
    private ArrayList<Hint> hints;
    private String answer;
    private boolean completed;

    /**
     * 
     * @param clues list of type clue associated with puzzle
     * @param hints list of type hints associated with puzzle
     * @param answer string answer stored as upercase
     */
    public Puzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer) {
        this.clues = clues;
        this.hints = hints;
        this.answer = answer.toUpperCase();
        this.completed = false;
    }

    /**
     * check if answer is correct, if so, mark puzzle as completed
     * @param answer
     */
    public void enterAnswer(String answer) {
        answer = answer.toUpperCase();
        if (this.answer.equals(answer)) {
            this.completed = true;
        }
    }

    /**
     * get state of puzzle
     * @return
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * use a skip and mark puzzle as completed
     */
    public void useSkip() {
        this.completed = true;
        // Certificate.useSkip();
    }

    public ArrayList<Hint> getHints() {
        return hints;
    }

    public ArrayList<Clue> getClues() {
        return clues;
    }

    /**
     * use a hint and display it (if available)
     */
    public void useHint() {
        if (hints.size() > 0) {
            Hint hint = hints.remove(0);
            // Certificate.useHint();
            hint.displayHint();
        } else {
            System.out.println("No more hints available.");
        }
    }
}
