package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class InventoryTest {
    private Inventory inventory;

    @Before
    public void setUp() {
        inventory = new Inventory();
    }

    @Test
    public void testConstructor_default_initializesEmptyLists() {
        assertEquals("Clues list should be empty", 0, inventory.getClueIDs().size());
        assertEquals("Hints list should be empty", 0, inventory.getHintIDS().size());
    }

    @Test
    public void testConstructor_withParameters_initializesWithData() {
        ArrayList<String> clues = new ArrayList<>();
        clues.add("clue1");
        clues.add("clue2");
        
        ArrayList<String> hints = new ArrayList<>();
        hints.add("hint1");
        hints.add("hint2");
        hints.add("hint3");
        
        Inventory inv = new Inventory(clues, hints);
        
        assertEquals("Clues list should have 2 items", 2, inv.getClueIDs().size());
        assertEquals("Hints list should have 3 items", 3, inv.getHintIDS().size());
        assertTrue("Clues should contain clue1", inv.getClueIDs().contains("clue1"));
        assertTrue("Hints should contain hint1", inv.getHintIDS().contains("hint1"));
    }

    @Test
    public void testAddClue_addsClueIDToList() {
        inventory.addClue("clue1");
        
        assertEquals("Clues list should have 1 item", 1, inventory.getClueIDs().size());
        assertTrue("Clues should contain clue1", inventory.getClueIDs().contains("clue1"));
    }

    @Test
    public void testAddClue_addMultipleClues() {
        inventory.addClue("clue1");
        inventory.addClue("clue2");
        inventory.addClue("clue3");
        
        assertEquals("Clues list should have 3 items", 3, inventory.getClueIDs().size());
        assertTrue("Clues should contain clue1", inventory.getClueIDs().contains("clue1"));
        assertTrue("Clues should contain clue2", inventory.getClueIDs().contains("clue2"));
        assertTrue("Clues should contain clue3", inventory.getClueIDs().contains("clue3"));
    }

    @Test
    public void testAddClue_addsDuplicateClues() {
        inventory.addClue("clue1");
        inventory.addClue("clue1");
        
        assertEquals("Clues list should have 2 items (duplicates allowed)", 2, inventory.getClueIDs().size());
    }

    @Test
    public void testAddHint_addsHintIDToList() {
        inventory.addHint("hint1");
        
        assertEquals("Hints list should have 1 item", 1, inventory.getHintIDS().size());
        assertTrue("Hints should contain hint1", inventory.getHintIDS().contains("hint1"));
    }

    @Test
    public void testAddHint_addMultipleHints() {
        inventory.addHint("hint1");
        inventory.addHint("hint2");
        inventory.addHint("hint3");
        
        assertEquals("Hints list should have 3 items", 3, inventory.getHintIDS().size());
        assertTrue("Hints should contain hint1", inventory.getHintIDS().contains("hint1"));
        assertTrue("Hints should contain hint2", inventory.getHintIDS().contains("hint2"));
        assertTrue("Hints should contain hint3", inventory.getHintIDS().contains("hint3"));
    }

    @Test
    public void testAddHint_addsDuplicateHints() {
        inventory.addHint("hint1");
        inventory.addHint("hint1");
        
        assertEquals("Hints list should have 2 items (duplicates allowed)", 2, inventory.getHintIDS().size());
    }

    @Test
    public void testGetClueIDs_returnsCorrectList() {
        inventory.addClue("clue1");
        inventory.addClue("clue2");
        
        ArrayList<String> clueIDs = inventory.getClueIDs();
        
        assertEquals("Should return 2 clue IDs", 2, clueIDs.size());
        assertEquals("First clue should be clue1", "clue1", clueIDs.get(0));
        assertEquals("Second clue should be clue2", "clue2", clueIDs.get(1));
    }

    @Test
    public void testGetHintIDS_returnsCorrectList() {
        inventory.addHint("hint1");
        inventory.addHint("hint2");
        inventory.addHint("hint3");
        
        ArrayList<String> hintIDs = inventory.getHintIDS();
        
        assertEquals("Should return 3 hint IDs", 3, hintIDs.size());
        assertEquals("First hint should be hint1", "hint1", hintIDs.get(0));
        assertEquals("Second hint should be hint2", "hint2", hintIDs.get(1));
        assertEquals("Third hint should be hint3", "hint3", hintIDs.get(2));
    }

    @Test
    public void testAddClueAndHint_bothListsIndependent() {
        inventory.addClue("clue1");
        inventory.addClue("clue2");
        inventory.addHint("hint1");
        
        assertEquals("Clues list should have 2 items", 2, inventory.getClueIDs().size());
        assertEquals("Hints list should have 1 item", 1, inventory.getHintIDS().size());
    }

    @Test
    public void testGetClueIDs_emptyInventory() {
        ArrayList<String> clueIDs = inventory.getClueIDs();
        
        assertTrue("Clues list should be empty", clueIDs.isEmpty());
        assertEquals("Clues list size should be 0", 0, clueIDs.size());
    }

    @Test
    public void testGetHintIDS_emptyInventory() {
        ArrayList<String> hintIDs = inventory.getHintIDS();
        
        assertTrue("Hints list should be empty", hintIDs.isEmpty());
        assertEquals("Hints list size should be 0", 0, hintIDs.size());
    }

    @Test
    public void testConstructorWithParameters_cluesAndHintsAreReferences() {
        ArrayList<String> clues = new ArrayList<>();
        clues.add("clue1");
        
        ArrayList<String> hints = new ArrayList<>();
        hints.add("hint1");
        
        Inventory inv = new Inventory(clues, hints);
        
        assertEquals("Clues should match", clues, inv.getClueIDs());
        assertEquals("Hints should match", hints, inv.getHintIDS());
    }

    @Test
    public void testAddClueAfterInitialization_worksCorrectly() {
        ArrayList<String> clues = new ArrayList<>();
        clues.add("clue1");
        
        ArrayList<String> hints = new ArrayList<>();
        
        Inventory inv = new Inventory(clues, hints);
        inv.addClue("clue2");
        
        assertEquals("Clues list should have 2 items", 2, inv.getClueIDs().size());
        assertTrue("Should contain clue1", inv.getClueIDs().contains("clue1"));
        assertTrue("Should contain clue2", inv.getClueIDs().contains("clue2"));
    }

    @Test
    public void testAddHintAfterInitialization_worksCorrectly() {
        ArrayList<String> clues = new ArrayList<>();
        ArrayList<String> hints = new ArrayList<>();
        hints.add("hint1");
        
        Inventory inv = new Inventory(clues, hints);
        inv.addHint("hint2");
        
        assertEquals("Hints list should have 2 items", 2, inv.getHintIDS().size());
        assertTrue("Should contain hint1", inv.getHintIDS().contains("hint1"));
        assertTrue("Should contain hint2", inv.getHintIDS().contains("hint2"));
    }

    @Test
    public void testInventoryState_afterMultipleOperations() {
        inventory.addClue("clue1");
        inventory.addClue("clue2");
        inventory.addHint("hint1");
        inventory.addHint("hint2");
        inventory.addHint("hint3");
        
        assertEquals("Clues should have 2 items", 2, inventory.getClueIDs().size());
        assertEquals("Hints should have 3 items", 3, inventory.getHintIDS().size());
        assertFalse("Clues list should not be empty", inventory.getClueIDs().isEmpty());
        assertFalse("Hints list should not be empty", inventory.getHintIDS().isEmpty());
    }
}