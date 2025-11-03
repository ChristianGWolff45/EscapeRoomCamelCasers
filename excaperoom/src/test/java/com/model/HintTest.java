package com.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HintTest {

    private Hint normal;
    private Hint empty;
    private Hint whitespace;
    private Hint unicode;
    private Hint nulls;

    @Before
    public void setUp() {
        normal = new Hint("A helpful tip", "h1", "p1");
        empty = new Hint("", "", "");
        whitespace = new Hint("   ", "   ", "   ");
        unicode = new Hint("提示内容", "üñî", "谜题ID");
        nulls = new Hint(null, null, null); // allowed by current constructor
    }

    @Test
    public void getHintIDReturnsProvidedId() {
        assertEquals("h1", normal.getHintID());
    }

    @Test
    public void getHintIDReturnsEmptyStringWhenEmpty() {
        assertEquals("", empty.getHintID());
    }

    @Test
    public void getHintIDReturnsWhitespaceWhenWhitespace() {
        assertEquals("   ", whitespace.getHintID());
    }

    @Test
    public void getHintIDHandlesUnicode() {
        assertEquals("üñî", unicode.getHintID());
    }

    @Test
    public void constructorAllowsNullsAndGetHintIDReturnsNull() {
        assertNull("Constructor allows null id", nulls.getHintID());
        assertNull("Constructor allows null puzzleID", nulls.getPuzzleID());
    }

    @Test
    public void getPuzzleIDReturnsProvidedPuzzleID() {
        assertEquals("p1", normal.getPuzzleID());
    }

    @Test
    public void displayHintDoesNotThrowForAnyCase() {
        // We don't assert on System.out contents; only ensure it doesn't throw.
        normal.displayHint();
        empty.displayHint();
        whitespace.displayHint();
        unicode.displayHint();
        nulls.displayHint();
    }

    @Test
    public void displayHintDoesNotModifyFields() {
        String beforeId = normal.getHintID();
        String beforePuzzle = normal.getPuzzleID();
        normal.displayHint();
        assertEquals("displayHint should not change id", beforeId, normal.getHintID());
        assertEquals("displayHint should not change puzzleID", beforePuzzle, normal.getPuzzleID());
    }

    @Test
    public void multipleConsecutiveDisplayCallsAreSafe() {
        for (int i = 0; i < 5; i++) {
            normal.displayHint();
        }
        assertEquals("id should remain unchanged after multiple prints", "h1", normal.getHintID());
    }

    @Test
    public void hearHintCanBeOverriddenToAvoidTTSSideEffects() {
        // Create a subclass that overrides hearHint to set a flag instead of calling Speak.speak(...)
        class TestHintOverride extends Hint {
            boolean heard = false;
            TestHintOverride(String tip, String id, String puzzleID) {
                super(tip, id, puzzleID);
            }
            @Override
            public void hearHint() {
                // instead of Speak.speak, just set flag
                heard = true;
            }
            boolean wasHeard() { return heard; }
        }

        TestHintOverride th = new TestHintOverride("tip", "tx", "pX");
        assertFalse("flag false before call", th.wasHeard());
        th.hearHint(); // calls overridden no-op version
        assertTrue("overridden hearHint should be invoked and set flag", th.wasHeard());
    }

    @Test
    public void longStringsHandledWithoutError() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) sb.append("y");
        Hint longHint = new Hint(sb.toString(), "longH", "longP");
        assertEquals("longH", longHint.getHintID());
        longHint.displayHint(); // ensure no exception for long tip
    }
}
