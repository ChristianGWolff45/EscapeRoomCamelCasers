package com.model;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import org.junit.Test;







public class wordleTest {
    @Test
    public void testTesting(){
        assertTrue(true);
    }

    @Test 

     public void testAllGreen_exactMatch() {
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        String[][] result = wordle.guessAnswer("Apple");

        String[][] expected = {
            {"A", "green"},
            {"P", "green"},
            {"P", "green"},
            {"L", "green"},
            {"E", "green"}
        };

        assertTrue("Exact match should be all green", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAllRed_noCommonLetters() {
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        String[][] result = wordle.guessAnswer("ZZZZZ");

        String[][] expected = {
            {"Z", "red"},
            {"Z", "red"},
            {"Z", "red"},
            {"Z", "red"},
            {"Z", "red"}
        };

        assertTrue("Completely different guess should be all red", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testMixed_greenYellowRed() {
        // answer: A P P L E (0 1 2 3 4)
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        // guess "ALLEY" -> A(0) green, L(1) yellow (L at 3), L(2) red (only one L), E(3) yellow (E at 4), Y(4) red
        String[][] result = wordle.guessAnswer("Alley");

        String[][] expected = {
            {"A", "green"},
            {"L", "yellow"},
            {"L", "red"},
            {"E", "yellow"},
            {"Y", "red"}
        };

        assertTrue("ALLEY vs APPLE should produce mixed colors", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testCaseSensitivity() {
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        String[][] result = wordle.guessAnswer("aPPLE");

        String[][] expected = {
            {"A", "green"},
            {"P", "green"},
            {"P", "green"},
            {"L", "green"},
            {"E", "green"}
        };

        assertTrue("Same guess different cases should be all green", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testRepeatedLetters_complexCase() {
        // answer: A P P L E (0 1 2 3 4)
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        // guess "PAPPP"
        // Pass 1 (greens): index 2 (P) is green
        // Remaining P positions: {1}
        // Pass 2: index0 P -> yellow (consume pos1), index1 A -> yellow (A at 0), index3/4 P -> red (no P left)
        String[][] result = wordle.guessAnswer("PAPPP");

        String[][] expected = {
            {"P", "yellow"},
            {"A", "yellow"},
            {"P", "green"},
            {"P", "red"},
            {"P", "red"}
        };

        assertTrue("PAPPP vs APPLE should correctly handle repeated letters", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testShorterGuess_partial() {
        // Guess shorter than answer
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        // guess "App" -> first three positions should be green
        String[][] result = wordle.guessAnswer("App");

        String[][] expected = {
            {"A", "green"},
            {"P", "green"},
            {"P", "green"}
        };

        assertTrue("Shorter correct prefix should be green where positions match", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testLongerGuess_extraLetters() {
        // Guess longer than answer
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);
        // guess "AppleX" - last letter X is not in answer -> red
        String[][] result = wordle.guessAnswer("AppleX");

        String[][] expected = {
            {"A", "green"},
            {"P", "green"},
            {"P", "green"},
            {"L", "green"},
            {"E", "green"},
            {"X", "red"}
        };

        assertTrue("Longer guess should evaluate existing positions and mark extras as red/yellow appropriately", Arrays.deepEquals(expected, result));
    }

    @Test
    public void testNullOrEmptyGuess() {
        Puzzle wordle = new Wordle(null, null, "Apple", false, null);

        // null guess returns empty array per implementation
        String[][] nullResult = wordle.guessAnswer(null);
        assertTrue("Null guess should return empty 2D array (length 0)", nullResult.length == 0);

        // empty guess returns zero-length output
        String[][] emptyResult = wordle.guessAnswer("");
        assertTrue("Empty guess should return zero-length output", emptyResult.length == 0);
    }
}
