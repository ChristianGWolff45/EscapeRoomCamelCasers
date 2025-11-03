package com.model;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author Christian Wolff
 */
public class PuzzleTest {



    /**
     * Helper to create a concrete Puzzle instance (anonymous subclass) for testing.
     */
    private Puzzle makePuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID) {
        return new Puzzle(clues, hints, answer, completed, puzzleID) {
            @Override
            public String[][] guessAnswer(String answer) {
                return null;
            }

            @Override
            public void enterAnswer(Direction[] answer) {
                // no-op for tests
            }
        };
    }

    @Test
    public void testEnterAnswerString_caseInsesenitive() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        Puzzle p = makePuzzle(clues, hints, "sEcReT", false, "p1");
        p.enterAnswer("secret");
        // enter with different case should still mark completed (constructor uppercases stored answer)
        assertTrue("entering correct answer ignoring case should mark completed", (p.isCompleted()));
    }

    @Test
    public void testEnterAnswerString_emptyString() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        Puzzle p = makePuzzle(clues, hints, "secret", false, "p1");
        p.enterAnswer("");
        // enter with different case should still mark completed (constructor uppercases stored answer)
        assertTrue("enter an empty string should not complete puzzle", (!p.isCompleted()));
    }

    @Test
    public void testEnterAnswerString_wrongAnswer() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        Puzzle p = makePuzzle(clues, hints, "secret", false, "p1");
        p.enterAnswer("notasecret");
        // enter with different case should still mark completed (constructor uppercases stored answer)
        assertTrue("wrong answer should not complete puzzle", (!p.isCompleted()));
    }



    @Test
    public void testSkip_useSkip_puzzleSolved() {
        Puzzle p1 = makePuzzle(new ArrayList<>(), new ArrayList<>(), "X", false, "p3");
        p1.skipPuzzle();
        assertTrue("skipPuzzle should mark completed", p1.isCompleted());
    }

    @Test
    public void testComplete_useComplete_puzzleSolved() {
        Puzzle p1 = makePuzzle(new ArrayList<>(), new ArrayList<>(), "X", false, "p4");
        p1.complete();
        assertTrue("complete() should mark completed", p1.isCompleted());
    }

    @Test
    public void testGetID_normal() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();

        Puzzle p = makePuzzle(clues, hints, "ABC", false, "myPuzzleID");

        assertTrue("getPuzzleID should return the ID", "myPuzzleID".equals(p.getPuzzleID()));
    }

    @Test
    public void testGetID_noPuzzleID() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();

        Puzzle p = makePuzzle(clues, hints, "ABC", false, "");

        assertTrue("getPuzzleID should return the ID", "".equals(p.getPuzzleID()));
    }

    @Test
    public void testGetHints_HintsNull() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();

        Puzzle p = makePuzzle(clues, hints, "ABC", false, "myPuzzleID");
        assertEquals("puzzle hints should be null", hints, p.getHints());
    }

    @Test
    public void testGetHints_Hint_NullHint() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        Hint hint = new Hint("tip", "id", "puzzleID");
        hints.add(hint);
        hints.add(null);
        
        Puzzle p = makePuzzle(clues, hints, "ABC", false, "myPuzzleID");
        assertEquals("puzzle hints should contain one hint and one null", hints, p.getHints());
    }

    @Test
    public void testgetClues_nullClues() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();

        Puzzle p = makePuzzle(clues, hints, "ABC", false, "myPuzzleID");
        assertEquals("puzzle clues should be null", clues, p.getClues());
    }

    @Test
    public void testgetClues_Clue_NullClue() {
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        Clue clue = new Clue("tip", "id");
        clues.add(clue);
        clues.add(null);

        Puzzle p = makePuzzle(clues, hints, "ABC", false, "myPuzzleID");
        assertEquals("puzzle clues should contain one clue and one null", clues, p.getClues());
    }


    @Test
    public void testUseHint_removesHint() {
        ArrayList<Hint> hints = new ArrayList<>();
        hints.add(new Hint("h1", "first hint", "p7"));
        hints.add(new Hint("h2", "second hint", "p7"));

        Puzzle p = makePuzzle(new ArrayList<>(), hints, "A", false, "p7");
 
        int before = p.getHints().size();
        p.useHint(); // should remove first hint from list (and call displayHint)
        int after = p.getHints().size();

        assertTrue("useHint should remove one hint from the list", after == before - 1);
    }

    @Test
    public void testUseHint_noHints() {
        ArrayList<Hint> hints = new ArrayList<>();
        hints.add(new Hint("h1", "first hint", "p7"));

        Puzzle p = makePuzzle(new ArrayList<>(), hints, "A", false, "p7");
 
        p.useHint(); 
        p.useHint();
        p.useHint();

        assertEquals("get hints should return an empty array list of hints", p.getHints(), new ArrayList<Hint>());
    }

}
