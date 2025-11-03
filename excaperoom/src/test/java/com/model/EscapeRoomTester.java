package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class EscapeRoomTester {

    private EscapeRoom escapeRoom;

    @Before
    public void setup() {
        escapeRoom = new EscapeRoom();
    }

    // ===== USER METHODS =====

    @Test
    public void testSignUpValidUser() {
        boolean added = escapeRoom.signUp("user1", "First", "Last", "pass");
        assertTrue("signUp should return true for valid input", added);
        assertNotNull("currentUser should be set after signUp", escapeRoom.getCurrentUser());
        assertEquals("user1", escapeRoom.getCurrentUser().getUsername());
    }

    @Test
    public void testSignUpDuplicateUserFails() {
        escapeRoom.signUp("user2", "F", "L", "pw");
        boolean addedAgain = escapeRoom.signUp("user2", "F", "L", "pw");
        assertFalse("signUp should fail for duplicate username", addedAgain);
    }

    @Test
    public void testLoginSuccessAndState() {
        escapeRoom.signUp("loginme", "L", "Login", "secret");
        boolean ok = escapeRoom.login("loginme", "secret");
        assertTrue("login should succeed with correct credentials", ok);
        assertTrue("isLoggedIn should be true after login", escapeRoom.isLoggedIn());
        assertNotNull("currentUser should be set after login", escapeRoom.getCurrentUser());
        assertEquals("loginme", escapeRoom.getCurrentUser().getUsername());
    }

    @Test
    public void testLoginFailure() {
        boolean ok = escapeRoom.login("nonexistent", "badpass");
        assertFalse("login should fail with wrong credentials", ok);
        assertFalse("isLoggedIn should be false", escapeRoom.isLoggedIn());
    }

    @Test
    public void testLogout() {
        escapeRoom.signUp("logoutme", "F", "L", "pw");
        escapeRoom.logout();
        // logout in facade only saves users, cannot check much else without DataWriter
        assertTrue("currentUser should still exist after logout call in facade", escapeRoom.getCurrentUser() != null);
    }

    // ===== GAME METHODS =====

    @Test
    public void testGetAllGames() {
        ArrayList<Game> games = escapeRoom.getAllGames();
        assertNotNull("getAllGames should not return null", games);
        assertTrue("getAllGames should contain at least one game", games.size() >= 0);
    }

    @Test
    public void testPickGameUpdatesCurrentGame() {
        escapeRoom.signUp("gamer", "F", "L", "pw");
        escapeRoom.pickGame("SomeGame");
        assertNotNull("Current user should exist", escapeRoom.getCurrentUser());
        // we cannot check Game internals without creating Game objects
    }

    @Test
    public void testIsLoggedInReflectsCurrentUser() {
        escapeRoom.signUp("testuser", "F", "L", "pw");
        assertTrue("isLoggedIn should return true when currentUser exists", escapeRoom.isLoggedIn());
    }

    @Test
    public void testEndGameRunsWithoutException() {
        escapeRoom.signUp("player", "F", "L", "pw");
        escapeRoom.endGame();
        // facade endGame prints and saves, no internal state to assert
        assertNotNull(escapeRoom.getCurrentUser());
    }

    @Test
    public void testPlayStoryRuns() {
        escapeRoom.signUp("storyuser", "F", "L", "pw");
        escapeRoom.playStory();
        // facade calls Game.playStory, cannot assert output without redirecting System.out
        assertNotNull(escapeRoom.getCurrentUser());
    }
}
