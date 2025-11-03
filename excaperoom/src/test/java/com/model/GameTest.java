package com.model;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Room startRoom;
    private Room lockedNeighbor;
    private Room unlockedNeighbor;
    private HashMap<String, Puzzle> puzzleMap;
    private HashMap<String, Hint> hintMap;
    private HashMap<String, Clue> clueMap;
    private ArrayList<Room> roomList;
    private User user;

    @Before
    public void setUp() {
        // Prepare rooms: one locked neighbor and one unlocked neighbor
        lockedNeighbor = new Room("rLocked", "LockedRoom", new ArrayList<Puzzle>(), new ArrayList<Room>(), false, false);
        unlockedNeighbor = new Room("rUnlocked", "UnlockedRoom", new ArrayList<Puzzle>(), new ArrayList<Room>(), true, false);

        ArrayList<Room> neighbors = new ArrayList<Room>();
        neighbors.add(lockedNeighbor);
        neighbors.add(unlockedNeighbor);

        // Create a start room containing no puzzles but the neighbors list
        startRoom = new Room("rStart", "StartRoom", new ArrayList<Puzzle>(), neighbors, true, false);

        // Prepare maps for puzzles, hints, clues
        puzzleMap = new HashMap<String, Puzzle>();
        hintMap = new HashMap<String, Hint>();
        clueMap = new HashMap<String, Clue>();

        // Add one test puzzle / hint / clue to the maps
        TestPuzzle p = new TestPuzzle(new ArrayList<Clue>(), new ArrayList<Hint>(), "ANS", false, "p1");
        puzzleMap.put("p1", p);

        Hint h = new Hint("tip text", "h1", "p1");
        hintMap.put("h1", h);

        Clue c = new Clue("c1", "desc");
        clueMap.put("c1", c);

        // minimal room list (not required by Game logic in these tests)
        roomList = new ArrayList<Room>();
        roomList.add(startRoom);
        roomList.add(lockedNeighbor);
        roomList.add(unlockedNeighbor);

        // create a User object
        user = new User("parkermac11", "Parker", "McClure", "parkerisaprettycoolguy");

        // Construct the Game under test
        game = new Game("TestGame", user, startRoom, hintMap, puzzleMap, clueMap, "story text", roomList);
    }

    @Test
    public void getCurrentRoomInitiallyIsStartingRoom() {
        assertSame("Initially current room should be the start room", startRoom, game.getCurrentRoom());
    }

    @Test
    public void getPuzzleGetHintGetClueAndCountReturnCorrectItems() {
        // getPuzzle / getHint / getClue should return the objects we put in the maps
        Puzzle p = game.getPuzzle("p1");
        assertNotNull("Puzzle should be present", p);
        assertEquals("p1", p.getPuzzleID());

        Hint h = game.getHint("h1");
        assertNotNull("Hint should be present", h);
        assertEquals("h1", h.getHintID());
        assertEquals("p1", h.getPuzzleID());

        Clue c = game.getClue("c1");
        assertNotNull("Clue should be present", c);
        assertEquals("c1", c.getClueID());

        assertEquals("Puzzle count should equal map size", 1, game.getPuzzleCount());
    }

    @Test
    public void getTimeLeftWhenNotStartedReturnsFullAllowanceAndTimeLeftMMSSFormat() {
        // Before Run() startTime is null -> getTimeLeft() returns full allowance
        Duration left = game.getTimeLeft();
        assertNotNull(left);
        // Game constructor uses 30 minutes
        assertEquals(30 * 60, left.getSeconds());

        // formatted string should be "30:00"
        String mmss = game.timeLeftMMSS();
        assertEquals("30:00", mmss);
    }

    @Test
    public void runSetsStartTimeAndTimeLeftDecreasesOrEqualsAllowance() throws InterruptedException {
        // Run the game (sets startTime). After Run(), getTimeLeft() must be <= full allowance.
        game.Run();
        Duration left = game.getTimeLeft();
        assertNotNull(left);
        long leftSeconds = left.getSeconds();
        assertTrue("After Run time left should be less than or equal to full allowance", leftSeconds <= 30 * 60);
    }

    @Test
    public void getTimeElapsedWhenNotStartedReturnsAllowance() {
        // Per Game implementation, when startTime is null getTimeElapsed() returns timeAllowed
        Duration elapsed = game.getTimeElapsed();
        assertNotNull(elapsed);
        assertEquals(30 * 60, elapsed.getSeconds());
    }

    @Test
    public void endGameSetsCertificateTimeTakenSafely() {
        // ensure endGame does not throw and certificate exists afterwards
        game.Run();
        // small sleep to ensure elapsed non-zero (not required, but safe)
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        game.endGame(); // should call certificate.setTimeTaken(getTimeElapsed())
        assertNotNull("Game should have a certificate", game.getCertificate());
    }

    @Test
    public void goNextRoomUsesRoomNavigationAndUpdatesCurrentRoom() {
        // starting room has neighbors; moving to an unlocked neighbor should update currentRoom
        assertSame(startRoom, game.getCurrentRoom());
        game.goNextRoom("UnlockedRoom");
        assertSame("Game.currentRoom should change to the unlocked neighbor", unlockedNeighbor, game.getCurrentRoom());

        // trying to go to a locked neighbor should leave current room as-is
        // move back to startRoom first
        game = new Game("TestGame2", user, startRoom, hintMap, puzzleMap, clueMap, "story text", roomList);
        game.goNextRoom("LockedRoom");
        assertSame("Attempting to go to locked neighbor should not change current room", startRoom, game.getCurrentRoom());
    }

    // Simple concrete Puzzle subclass for tests
    static class TestPuzzle extends Puzzle {
        TestPuzzle(ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed, String puzzleID) {
            super(clues, hints, answer, completed, puzzleID);
        }
    }
}
