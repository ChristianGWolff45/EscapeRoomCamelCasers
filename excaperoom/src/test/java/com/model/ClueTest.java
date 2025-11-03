package com.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class ClueTest {

    private Clue normal;
    private Clue empty;
    private Clue whitespace;
    private Clue unicode;
    private Clue nulls;

    @Before
    public void setUp() {
        normal = new Clue("c1", "A helpful clue");
        empty = new Clue("", "");
        whitespace = new Clue("   ", "   ");
        unicode = new Clue("üñîçøde", "这是一个线索"); // unicode id/description
        nulls = new Clue(null, null); // allowed by current constructor
    }

    @Test
    public void getClueIDReturnsProvidedId() {
        assertEquals("c1", normal.getClueID());
    }

    @Test
    public void getClueIDReturnsEmptyStringWhenEmpty() {
        assertEquals("", empty.getClueID());
    }

    @Test
    public void getClueIDReturnsWhitespaceWhenWhitespace() {
        assertEquals("   ", whitespace.getClueID());
    }

    @Test
    public void getClueIDHandlesUnicode() {
        assertEquals("üñîçøde", unicode.getClueID());
    }

    @Test
    public void constructorAllowsNullsAndGetClueIDReturnsNull() {
        assertNull("Constructor allows null id", nulls.getClueID());
    }

    @Test
    public void displayClueDoesNotThrowForAnyCase() {
        // We don't assert on System.out contents; only ensure it doesn't throw.
        normal.displayClue();
        empty.displayClue();
        whitespace.displayClue();
        unicode.displayClue();
        nulls.displayClue();
    }

    @Test
    public void displayClueDoesNotModifyIdOrDescription() {
        String beforeId = normal.getClueID();
        normal.displayClue();
        assertEquals("displayClue should not change id", beforeId, normal.getClueID());
    }

    @Test
    public void multipleConsecutiveDisplayCallsAreSafe() {
        for (int i = 0; i < 5; i++) {
            normal.displayClue();
        }
        assertEquals("id should remain unchanged after multiple prints", "c1", normal.getClueID());
    }

    @Test
    public void hearClueCanBeOverriddenToAvoidTTSSideEffects() {
        // Create a subclass that overrides hearClue to set a flag instead of calling Speak.speak(...)
        class TestClueOverride extends Clue {
            boolean heard = false;
            TestClueOverride(String id, String desc) {
                super(id, desc);
            }
            @Override
            public void hearClue() {
                // instead of Speak.speak, just set flag
                heard = true;
            }
            boolean wasHeard() { return heard; }
        }

        TestClueOverride tc = new TestClueOverride("tx", "desc");
        assertFalse("flag false before call", tc.wasHeard());
        tc.hearClue(); // calls overridden no-op version
        assertTrue("overridden hearClue should be invoked and set flag", tc.wasHeard());
    }

    @Test
    public void longStringsHandledWithoutError() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) sb.append("x");
        Clue longClue = new Clue("longId", sb.toString());
        assertEquals("longId", longClue.getClueID());
        longClue.displayClue(); // ensure no exception for long description
    }
}

