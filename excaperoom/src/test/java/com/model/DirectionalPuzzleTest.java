package com.model;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
public class DirectionalPuzzleTest {
    @Test
    public void testEnterAnswer_correctNormal(){
        Direction[] guess = {Direction.UP,Direction.UP,Direction.DOWN};
        String answer = "UPUPDOWN";
        Puzzle p = new DirectionalPuzzle(null, null, answer, false, "puzzleID");
        p.enterAnswer(guess);
        assertTrue("guess is correct puzzle should be set to completed", p.isCompleted());
    }

    @Test
    public void testEnterAnswer_incorrectNormal(){
        Direction[] guess = {Direction.UP,Direction.DOWN,Direction.DOWN};
        String answer = "UPUPDOWN";
        Puzzle p = new DirectionalPuzzle(null, null, answer, false, "puzzleID");
        p.enterAnswer(guess);
        assertTrue("guess is incorrect puzzle should not be set to completed", !p.isCompleted());
    }

    @Test
    public void testEnterAnswer_incorrectEmptyGuess(){
        Direction[] guess = new Direction[0];
        String answer = "UPUPDOWN";
        Puzzle p = new DirectionalPuzzle(null, null, answer, false, "puzzleID");
        p.enterAnswer(guess);
        assertTrue("guess is incorrect puzzle should not be set to completed", !p.isCompleted());
    }

    @Test
    public void testEnterAnswer_correctEmptyGuessEmptyAnswer(){
        Direction[] guess = new Direction[0];
        String answer = "";
        Puzzle p = new DirectionalPuzzle(null, null, answer, false, "puzzleID");
        p.enterAnswer(guess);
        assertTrue("guess is correct puzzle should not be set to completed", p.isCompleted());
    }

    @Test
    public void testEnterAnswer_correctGuess_LowerCaseAnswer(){
        Direction[] guess = {Direction.UP, Direction.UP, Direction.DOWN};
        String answer = "upupdown";
        Puzzle p = new DirectionalPuzzle(null, null, answer, false, "puzzleID");
        p.enterAnswer(guess);
        assertTrue("guess is correct puzzle should not be set to completed", p.isCompleted());
    }

}
