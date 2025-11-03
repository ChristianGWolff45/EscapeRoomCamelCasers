package com.model;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 * @author Christian Wolff
 */
public class PhonePuzzleTest {
    @Test
    public void testEnterGuess_wrongGuess(){
        PhonePuzzle p = new PhonePuzzle(null, null, "answer", false, "puzzleID");
        String isCorrect = p.enterGuess("wrong");
        assertTrue("isCorrect should evaluate to Incorrect, try again", isCorrect.equals("Incorrect, try again."));
    }

    @Test
    public void testEnterGuess_writeGuess(){
        PhonePuzzle p = new PhonePuzzle(null, null, "answer", false, "puzzleID");
        String isCorrect = p.enterGuess("answer");
        assertTrue("isCorrect should evaluate to Correct", isCorrect.equals("Correct!"));
    }

    @Test
    public void testEnterGuess_differentCases(){
        PhonePuzzle p = new PhonePuzzle(null, null, "aNsweR", false, "puzzleID");
        String isCorrect = p.enterGuess("AnSWEr");
        assertTrue("isCorrect should evaluate to Correct", isCorrect.equals("Correct!"));
    }

    @Test
    public void testPuzzleCompleted_CorrectGuess(){
        PhonePuzzle p = new PhonePuzzle(null, null, "aNsweR", false, "puzzleID");
        p.enterGuess("AnSWEr");
        assertTrue("puzzle should be completed", p.isCompleted());
    }
    @Test
    public void testPuzzleCompleted_wrongGuess(){
        PhonePuzzle p = new PhonePuzzle(null, null, "aNsweR", false, "puzzleID");
        p.enterGuess("wrong");
        assertTrue("puzzle should not be completed", !p.isCompleted());
    }

}
