package com.model;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
public class MultipleChoicePuzzleTest {
    @Test
    public void testConstructor_normalOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("a");
        options.add("b");
        options.add("c");
        options.add("d");
    
        MultipleChoice p = new MultipleChoice(null, null, "answer", false, "puzzleID", options);
        String output = p.toString();
        String outputExpected = "Multiple Choice:\n" + //
                        "Enter the correct letter:\n" + //
                        "A) a   B) b   C) c   D) d   ";
        assertTrue("options should set output to outputExpected", output.equals(outputExpected));
    }

    @Test
    public void testConstructor_nullOptions(){
        
        MultipleChoice p = new MultipleChoice(null, null, "answer", false, "puzzleID", null);
        String output = p.toString();
        String outputExpected = "Multiple Choice:\n" + //
                        "Enter the correct letter:\n" + //
                        "A)    B)    C)    D)    ";
        assertTrue("options should set output to outputExpected", output.equals(outputExpected));
    }

    @Test
    public void testConstructor_normalOptions_nullOption(){
        ArrayList<String> options = new ArrayList<>();
        options.add("a");
        options.add(null);
        options.add("c");
        options.add("d");
    
        MultipleChoice p = new MultipleChoice(null, null, "answer", false, "puzzleID", options);
        String output = p.toString();
        String outputExpected = "Multiple Choice:\n" + //
                        "Enter the correct letter:\n" + //
                        "A) a   B)    C) c   D) d   ";
        assertTrue("options should set output to outputExpected", output.equals(outputExpected));
    }

    @Test
    public void testEnterGuess_wrongGuess(){
        MultipleChoice p = new MultipleChoice(null, null, "answer", false, "puzzleID", null);
        String isCorrect = p.enterGuess("wrong");
        assertTrue("isCorrect should evaluate to Incorrect, try again", isCorrect.equals("Incorrect, try again."));
    }

    @Test
    public void testEnterGuess_writeGuess(){
        MultipleChoice p = new MultipleChoice(null, null, "answer", false, "puzzleID", null);
        String isCorrect = p.enterGuess("answer");
        assertTrue("isCorrect should evaluate to Correct", isCorrect.equals("Correct!"));
    }

    @Test
    public void testEnterGuess_differentCases(){
        MultipleChoice p = new MultipleChoice(null, null, "aNsweR", false, "puzzleID", null);
        String isCorrect = p.enterGuess("AnSWEr");
        assertTrue("isCorrect should evaluate to Correct", isCorrect.equals("Correct!"));
    }

    @Test
    public void testPuzzleCompleted_CorrectGuess(){
        MultipleChoice p = new MultipleChoice(null, null, "aNsweR", false, "puzzleID", null);
        p.enterGuess("AnSWEr");
        assertTrue("puzzle should be completed", p.isCompleted());
    }
    @Test
    public void testPuzzleCompleted_wrongGuess(){
        MultipleChoice p = new MultipleChoice(null, null, "aNsweR", false, "puzzleID", null);
        p.enterGuess("wrong");
        assertTrue("puzzle should not be completed", !p.isCompleted());
    }
}
