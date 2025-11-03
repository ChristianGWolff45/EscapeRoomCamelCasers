package com.model;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit4 tests for com.model.Room using only ArrayList (no Collections helpers).
 */
public class RoomTest {

    private Room room;
    private Room neighborLocked;
    private Room neighborUnlocked;

    @Before
    public void setUp() {
        // create a completed puzzle using real Clue/Hint objects
        ArrayList<Clue> clues = new ArrayList<>();
        ArrayList<Hint> hints = new ArrayList<>();
        TestPuzzle completedPuzzle = new TestPuzzle(clues, hints, "ANSWER", false, "p1");
        completedPuzzle.complete(); // ensure puzzle marked completed for tests that need it

        ArrayList<Puzzle> puzzles = new ArrayList<>();
        puzzles.add(completedPuzzle);

        ArrayList<Room> nextRooms = new ArrayList<>();
        neighborLocked = new Room("r2", "LockedNeighbor", new ArrayList<>(), new ArrayList<>(), false, false);
        neighborUnlocked = new Room("r3", "UnlockedNeighbor", new ArrayList<>(), new ArrayList<>(), true, false);

        nextRooms.add(neighborLocked);
        nextRooms.add(neighborUnlocked);

        room = new Room("r1", "StartRoom", puzzles, nextRooms, true, false);
    }

    @Test
    public void getNameAndGetRoomNameReturnRoomName() {
        assertEquals("StartRoom", room.getName());
        assertEquals("StartRoom", room.getRoomName());
    }

    @Test
    public void addNextRoomAppendsNewNeighborAndNavigationRespectsLock() {
        // add locked neighbor
        Room newRoomLocked = new Room("r4", "NewRoom", new ArrayList<>(), new ArrayList<>(), false, false);
        room.addNextRoom(newRoomLocked);

        // attempt to go to locked neighbor - should return current room
        Room result = room.goNextRoom("NewRoom");
        assertSame("New locked neighbor should not be entered", room, result);

        // add unlocked neighbor and navigate to it
        Room newRoomUnlocked = new Room("r5", "NewRoomUnlocked", new ArrayList<>(), new ArrayList<>(), true, false);
        room.addNextRoom(newRoomUnlocked);

        Room moved = room.goNextRoom("NewRoomUnlocked");
        assertSame("Should navigate to unlocked neighbor by name", newRoomUnlocked, moved);
    }

    @Test
    public void goNextRoomReturnsUnlockedNeighborOnly() {
        Room nextUnlocked = room.goNextRoom("UnlockedNeighbor");
        assertSame("Unlocked neighbor should be returned", neighborUnlocked, nextUnlocked);

        Room nextLocked = room.goNextRoom("LockedNeighbor");
        assertSame("Locked neighbor should not be entered and should return current room", room, nextLocked);
    }

    @Test
    public void goNextRoomReturnsThisWhenNameNotFound() {
        Room next = room.goNextRoom("NoSuchRoom");
        assertSame("Unknown neighbor name should return current room", room, next);
    }

    @Test
    public void isUnlockedReflectsConstructorValue() {
        assertTrue("Room constructed unlocked should report unlocked", room.isUnlocked());
        assertFalse("Neighbor constructed locked should report locked", neighborLocked.isUnlocked());
        assertTrue("Neighbor constructed unlocked should report unlocked", neighborUnlocked.isUnlocked());
    }

    @Test
    public void unlockNeighborsFailsIfAnyPuzzleIncomplete() {
        // create puzzles: one completed, one incomplete
        ArrayList<Clue> c = new ArrayList<>();
        ArrayList<Hint> h = new ArrayList<>();
        TestPuzzle done = new TestPuzzle(c, h, "YES", false, "done");
        done.complete();
        TestPuzzle notDone = new TestPuzzle(c, h, "NO", false, "notdone");

        ArrayList<Puzzle> mixed = new ArrayList<>();
        mixed.add(done);
        mixed.add(notDone);

        ArrayList<Room> neighborList = new ArrayList<>();
        neighborList.add(neighborLocked);

        Room r = new Room("rA", "MixedRoom", mixed, neighborList, true, false);

        boolean result = r.unlockNeighbors();
        assertFalse("unlockNeighbors should return false if any puzzle is incomplete", result);
        assertFalse("Neighbor should remain locked when unlockNeighbors fails", neighborLocked.isUnlocked());
    }

    @Test
    public void unlockNeighborsUnlocksAllNeighborsWhenAllPuzzlesCompleted() {
        ArrayList<Clue> c = new ArrayList<>();
        ArrayList<Hint> h = new ArrayList<>();
        TestPuzzle doneA = new TestPuzzle(c, h, "X", false, "pA");
        doneA.complete();

        ArrayList<Puzzle> allDone = new ArrayList<>();
        allDone.add(doneA);

        ArrayList<Room> neighborList = new ArrayList<>();
        neighborList.add(neighborLocked);

        Room r = new Room("rB", "DoneRoom", allDone, neighborList, true, false);

        assertFalse("Neighbor initially locked", neighborLocked.isUnlocked());
        boolean result = r.unlockNeighbors();
        assertTrue("unlockNeighbors should return true when every puzzle is completed", result);
        assertTrue("Neighbor should be unlocked after unlockNeighbors", neighborLocked.isUnlocked());
    }

    @Test
    public void isExitReflectsConstructorValue() {
        Room exitRoom = new Room("exit", "ExitRoom", new ArrayList<>(), new ArrayList<>(), true, true);
        assertTrue("Room constructed as exit should return true for isExit()", exitRoom.isExit());
        assertFalse("Normal room should not be exit", room.isExit());
    }

    // ------------------ Helper concrete class ------------------

    /**
     * Minimal concrete Puzzle subclass for use in Room tests.
     * Uses the Puzzle constructor from your code.
     */
    static class TestPuzzle extends Puzzle {
        TestPuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID) {
            super(clues, hints, answer, completed, puzzleID);
        }
    }
}