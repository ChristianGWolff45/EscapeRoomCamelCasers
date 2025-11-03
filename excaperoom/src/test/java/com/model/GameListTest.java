package com.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class GameListTest {

    @Test
    public void getAllGamesReturnsNonNullList() {
        GameList gl = GameList.getInstance();
        ArrayList<Game> all = gl.getAllGames();
        assertNotNull("getAllGames should return a non-null list", all);
    }

    @Test
    public void addGameAndRemoveGameModifyGameList() {
        GameList gl = GameList.getInstance();
        int before = gl.getAllGames().size();

        // local minimal TestGame
        class LocalTestGame extends Game {
            LocalTestGame(String name) {
                super(name, null, null, null, null, null, "s", new ArrayList<Room>());
            }
        }

        LocalTestGame g = new LocalTestGame("TEST_ADD_REMOVE_" + System.nanoTime());
        gl.addGame(g);
        try {
            assertEquals(before + 1, gl.getAllGames().size());
            // adding null should be ignored
            gl.addGame(null);
            assertEquals(before + 1, gl.getAllGames().size());
        } finally {
            // cleanup
            gl.removeGame(g);
        }
        assertEquals(before, gl.getAllGames().size());
    }

    @Test
    public void setCurrentGameFindsGameByNameAndGetCurrentGameReturnsIt() {
        GameList gl = GameList.getInstance();

        // local TestGame
        class LocalTestGame extends Game {
            LocalTestGame(String name) {
                super(name, null, null, null, null, null, "s", new ArrayList<Room>());
            }
        }

        String uniqueName = "TEST_SET_CURRENT_" + System.nanoTime();
        LocalTestGame g = new LocalTestGame(uniqueName);
        gl.addGame(g);
        try {
            boolean found = gl.setCurrentGame(uniqueName);
            assertTrue("setCurrentGame should find the added game", found);
            assertNotNull("getCurrentGame should return the set game", gl.getCurrentGame());
            assertEquals(uniqueName, gl.getCurrentGame().getName());
        } finally {
            // cleanup: clear currentGame and remove added game
            gl.endCurrentGame(); // safe even if currentGame was not this one
            gl.removeGame(g);
        }
    }

    @Test
    public void setCurrentGameReturnsFalseIfNotFound() {
        GameList gl = GameList.getInstance();
        String missing = "GAME_NAME_UNLIKELY_TO_EXIST_" + System.nanoTime();
        boolean found = gl.setCurrentGame(missing);
        assertFalse("setCurrentGame should return false for a name not present", found);
    }

    @Test
    public void enterGameInvokesRoomPrintRoomOnCurrentGame() {
        GameList gl = GameList.getInstance();

        // Local Room that flags when printRoom() is called
        class FlagRoom extends Room {
            boolean wasPrinted = false;
            FlagRoom(String id, String name) {
                super(id, name, new ArrayList<Puzzle>(), new ArrayList<Room>(), true, false);
            }
            @Override
            public void printRoom() {
                wasPrinted = true;
            }
        }

        // Local TestGame that returns our FlagRoom from getCurrentRoom()
        class LocalGameForPrint extends Game {
            private final FlagRoom room;
            LocalGameForPrint(String name, FlagRoom room) {
                super(name, null, room, null, null, null, "s", new ArrayList<Room>());
                this.room = room;
            }

            @Override
            public Room getCurrentRoom() {
                return room;
            }
        }

        FlagRoom fr = new FlagRoom("rFlag", "FlagRoom");
        LocalGameForPrint tg = new LocalGameForPrint("TEST_PRINT_" + System.nanoTime(), fr);
        gl.addGame(tg);
        try {
            assertFalse("should not be printed before enterGame()", fr.wasPrinted);
            assertTrue("setting current game should succeed", gl.setCurrentGame(tg.getName()));
            gl.enterGame(); // should call tg.getCurrentRoom().printRoom()
            assertTrue("enterGame should have invoked printRoom on the room", fr.wasPrinted);
        } finally {
            gl.endCurrentGame();
            gl.removeGame(tg);
        }
    }

    @Test
    public void endCurrentGameCallsEndAndClearsCurrentGame() {
        GameList gl = GameList.getInstance();

        // local TestGame that records endGame invocation
        class LocalGameForEnd extends Game {
            boolean ended = false;
            LocalGameForEnd(String name) {
                super(name, null, null, null, null, null, "s", new ArrayList<Room>());
            }
            @Override
            public void endGame() {
                ended = true;
            }
        }

        LocalGameForEnd tg = new LocalGameForEnd("TEST_END_" + System.nanoTime());
        gl.addGame(tg);
        try {
            assertTrue("should set current to local test game", gl.setCurrentGame(tg.getName()));
            assertNotNull(gl.getCurrentGame());
            assertFalse("endGame not yet called", tg.ended);
            gl.endCurrentGame();
            assertTrue("endCurrentGame should call endGame on current game", tg.ended);
            assertNull("current game should be cleared after endCurrentGame", gl.getCurrentGame());
        } finally {
            // make sure removed even if something failed
            gl.removeGame(tg);
        }
    }

    @Test
    public void findPuzzleFindClueFindHintAndCountPuzzlesDelegateToCurrentGame() {
        GameList gl = GameList.getInstance();

        // Local game that returns predictable objects for delegation
        class LocalGameDelegate extends Game {
            LocalGameDelegate(String name) {
                super(name, null, null, null, null, null, "s", new ArrayList<Room>());
            }
            @Override
            public Puzzle getPuzzle(String puzzleID) {
                return new Puzzle(new ArrayList<Clue>(), new ArrayList<Hint>(), "a", false, puzzleID) {};
            }
            @Override
            public Clue getClue(String clueID) {
                return new Clue(clueID, "desc");
            }
            @Override
            public Hint getHint(String hintID) {
                return new Hint("tip", hintID, "pId");
            }
            @Override
            public int getPuzzleCount() {
                return 123;
            }
        }

        LocalGameDelegate td = new LocalGameDelegate("TEST_DELEGATE_" + System.nanoTime());
        gl.addGame(td);
        try {
            assertTrue("should set current to delegate game", gl.setCurrentGame(td.getName()));
            Puzzle p = gl.findPuzzle("pX");
            Clue c = gl.findClue("cX");
            Hint h = gl.findHint("hX");
            int cnt = gl.countPuzzles();

            assertEquals("pX", p.getPuzzleID());
            assertEquals("cX", c.getClueID());
            assertEquals("hX", h.getHintID());
            assertEquals(123, cnt);
        } finally {
            gl.endCurrentGame();
            gl.removeGame(td);
        }
    }
}
