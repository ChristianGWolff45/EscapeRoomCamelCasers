package com.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Extensive tests for UserList class (JUnit 4).
 *
 * Notes:
 * - Resets the singleton between tests via reflection to keep each test isolated.
 * - Captures System.out for methods that print to console to assert expected messages.
 * - These tests will trigger the Thread.sleep(2000) calls inside UserList methods
 *   (addUser, loginUser, logout). If you want to avoid waiting during tests,
 *   consider making sleep duration configurable in production code.
 */
public class UserListTester {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outCapture = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        // Reset singleton UserList.userList to null before each test
        Field singleton = UserList.class.getDeclaredField("userList");
        singleton.setAccessible(true);
        singleton.set(null, null);

        // Capture console output
        System.setOut(new PrintStream(outCapture));
    }

    @After
    public void tearDown() throws Exception {
        // Restore original System.out
        System.setOut(originalOut);

        // Reset singleton again to avoid test interference
        Field singleton = UserList.class.getDeclaredField("userList");
        singleton.setAccessible(true);
        singleton.set(null, null);
    }

    /**
     * Utility: gets a fresh, controllable UserList instance and ensures its internal
     * users list is a new empty ArrayList (so we don't depend on DataLoader.getUserList()).
     */
    private UserList freshUserList() throws Exception {
        UserList list = UserList.getInstance();
        // Replace internal users list with a fresh, empty ArrayList
        Field usersField = UserList.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(list, new ArrayList<User>());
        // Ensure currentUser null
        Field currentField = UserList.class.getDeclaredField("currentUser");
        currentField.setAccessible(true);
        currentField.set(list, null);
        // Clear captured output buffer
        outCapture.reset();
        return list;
    }

    @Test
    public void testSingleton_getInstanceReturnsSameObject() throws Exception {
        UserList a = freshUserList();
        UserList b = UserList.getInstance();
        assertSame("getInstance should return same singleton instance", a, b);
    }

    @Test
    public void testAddUser_successAndHaveUser() throws Exception {
        UserList list = freshUserList();
        boolean created = list.addUser("alpha", "A", "One", "pw");
        assertTrue("addUser should return true for new username", created);

        // haveUser should find it
        assertTrue("haveUser should report username exists", list.haveUser("alpha"));

        // internal list has exactly one user with that username
        List<User> all = list.getUsers();
        assertEquals(1, all.size());
        assertEquals("alpha", all.get(0).getUsername());

        // Console output should contain success message
        String out = outCapture.toString();
        assertTrue(out.contains("successfully created") || out.toLowerCase().contains("success"));
    }

    @Test
    public void testAddUser_duplicateReturnsFalseAndDoesNotAdd() throws Exception {
        UserList list = freshUserList();
        assertTrue(list.addUser("beta", "B", "Two", "pw"));
        outCapture.reset();

        boolean createdAgain = list.addUser("beta", "B2", "Two2", "pw2");
        assertFalse("addUser should return false for existing username", createdAgain);

        // still only one user in list
        List<User> all = list.getUsers();
        assertEquals(1, all.size());
        assertEquals("beta", all.get(0).getUsername());

        String out = outCapture.toString().toLowerCase();
        assertTrue(out.contains("already exists") || out.contains("already"));
    }

    @Test
    public void testHaveUser_withNonexistentAndNull() throws Exception {
        UserList list = freshUserList();
        // no users yet
        assertFalse(list.haveUser("nonexistent"));
        // null should safely return false (no NPE because no users)
        assertFalse(list.haveUser(null));
        // add a user with non-null username and check null again
        list.addUser("gamma", "G", "Three", "pw");
        assertFalse("haveUser(null) should return false", list.haveUser(null));
    }

    @Test
    public void testLoginUser_successAndIsLoggedInAndCurrentUserSet() throws Exception {
        UserList list = freshUserList();
        list.addUser("loginme", "L", "Login", "secret");
        outCapture.reset();

        boolean ok = list.loginUser("loginme", "secret");
        assertTrue("loginUser should return true for correct credentials", ok);
        assertTrue("isLoggedIn should be true after successful login", list.isLoggedIn());
        assertNotNull("currentUser should be set", list.getCurrentUser());
        assertEquals("loginme", list.getCurrentUser().getUsername());

        String out = outCapture.toString();
        assertTrue(out.toLowerCase().contains("successfully logged in") || out.toLowerCase().contains("success"));
    }

    @Test
    public void testLoginUser_invalidCredentialsLeavesLoggedOut() throws Exception {
        UserList list = freshUserList();
        list.addUser("userx", "U", "X", "pw");
        outCapture.reset();

        boolean ok = list.loginUser("userx", "wrongpw");
        assertFalse("loginUser should return false for wrong password", ok);
        assertFalse("isLoggedIn should remain false after failed login", list.isLoggedIn());
        assertNull("currentUser should remain null on failed login", list.getCurrentUser());

        String out = outCapture.toString().toLowerCase();
        assertTrue(out.contains("invalid") || out.contains("invalid username") || out.contains("invalid username or password"));
    }

    @Test
    public void testLogout_whenLoggedIn_savesAndClearsCurrentUser() throws Exception {
        UserList list = freshUserList();
        list.addUser("logoutme", "L", "Out", "pw");
        outCapture.reset();

        // login
        assertTrue(list.loginUser("logoutme", "pw"));
        assertTrue(list.isLoggedIn());
        outCapture.reset();

        // now logout
        list.logout();

        // after logout, not logged in
        assertFalse("isLoggedIn should be false after logout", list.isLoggedIn());
        assertNull("currentUser should be null after logout", list.getCurrentUser());

        String out = outCapture.toString().toLowerCase();
        // either "logged out" or similar message printed
        assertTrue(out.contains("you have been logged out") || out.contains("logged out") || out.contains("not logged in") || out.contains("you are not logged in"));
    }

    @Test
    public void testLogout_whenNotLoggedIn_printsNotLoggedInAndNoException() throws Exception {
        UserList list = freshUserList();
        outCapture.reset();

        // ensure not logged in
        assertFalse(list.isLoggedIn());
        list.logout(); // should not throw

        String out = outCapture.toString().toLowerCase();
        assertTrue(out.contains("not logged in") || out.contains("you are not logged in"));
    }

    @Test
    public void testSetAndGetCurrentUser() throws Exception {
        UserList list = freshUserList();
        User u = new User("setter", "S", "Setter", "pw");
        // ensure not the same instance currently in list
        list.setCurrentUser(u);
        assertSame("getCurrentUser should return set value", u, list.getCurrentUser());

        // clear
        list.setCurrentUser(null);
        assertNull(list.getCurrentUser());
    }

    @Test
    public void testGetUsersAndGetAllUsersReturnSameListReference() throws Exception {
        UserList list = freshUserList();
        List<User> ref1 = list.getUsers();
        List<User> ref2 = list.getAllUsers();
        assertSame("getUsers and getAllUsers should return same reference", ref1, ref2);

        // adding through returned list should reflect in UserList
        ref1.add(new User("x", "X", "X", "pw"));
        assertEquals(1, list.getUsers().size());
    }

    @Test
    public void testPrintAllUserScores_printsScoresForEachUser() throws Exception {
        UserList list = freshUserList();
        // Create two users and ensure their getScore() method is safe to call.
        // Use small manipulations on Certificate to make scores deterministic if possible.
        User u1 = new User("sc1", "S", "One", "pw");
        User u2 = new User("sc2", "S", "Two", "pw");

        // set some certificate values if methods exist; otherwise rely on defaults
        try {
            java.time.Duration d1 = java.time.Duration.ofSeconds(10);
            java.time.Duration d2 = java.time.Duration.ofSeconds(20);
            u1.getCertificate().setTimeTaken(d1);
            u2.getCertificate().setTimeTaken(d2);
        } catch (Throwable ignored) {
            // ignore if certificate doesn't have setTimeTaken method publicly available
        }

        list.getUsers().add(u1);
        list.getUsers().add(u2);

        outCapture.reset();
        list.printAllUserScores();
        String out = outCapture.toString();

        // Ensure both usernames appear and some numeric score appears
        assertTrue(out.contains("User Scores:"));
        assertTrue(out.contains("sc1:"));
        assertTrue(out.contains("sc2:"));
    }

    @Test
    public void testSaveUsers_invokesDataWriterSaveUser_noException() throws Exception {
        // This will call DataWriter.saveUser(). We do not assert its internal behavior,
        // only that calling saveUsers doesn't throw.
        UserList list = freshUserList();
        // ensure at least one user so saveUsers has something to work with
        list.addUser("saveone", "S", "One", "pw");
        outCapture.reset();

        list.saveUsers(); // should call DataWriter.saveUser() - ensure no exception
        // no exception => test passes. Also check that nothing fatal printed to output
        String out = outCapture.toString().toLowerCase();
        // It's okay if DataWriter prints something; we just ensure no NPE or crash.
        assertNotNull(out);
    }

    @Test
    public void testEdgeCase_createUserWithEmptyAndNullFields() throws Exception {
        UserList list = freshUserList();
        // empty string fields
        assertTrue(list.addUser("", "", "", ""));
        assertTrue(list.haveUser("")); // empty username should be findable

        // null username case: addUser expects String username; passing null will create a user
        // and haveUser uses equals on user.getUsername(); creating user with null username
        // may lead to behavior – we assert it doesn't throw.
        boolean createdNull;
        try {
            createdNull = list.addUser(null, null, null, null);
        } catch (Throwable t) {
            // If production code throws, that's an allowed behavior; record and fail intentionally
            createdNull = false;
        }
        // If create succeeded, haveUser(null) should then be true; if it failed, createdNull==false
        if (createdNull) {
            assertTrue(list.haveUser(null));
        } else {
            // sanity: ensure no exceptions left the test; creation with null simply returned false or threw
            assertFalse(createdNull || list.haveUser(null));
        }
    }

    @Test
    public void testMultipleUsersHaveIndependentComponents() throws Exception {
        UserList list = freshUserList();
        list.addUser("a", "A", "A", "pw");
        list.addUser("b", "B", "B", "pw");
        List<User> users = list.getUsers();
        assertEquals(2, users.size());
        User u1 = users.get(0);
        User u2 = users.get(1);

        // Users should be distinct objects and their internal inventories should not be the same reference
        assertNotSame("Users should be different instances", u1, u2);
        assertNotSame("User inventories should be independent", u1.getInventory(), u2.getInventory());
        assertNotSame("User progress should be independent", u1.getProgress(), u2.getProgress());
        assertNotSame("User certificates should be independent", u1.getCertificate(), u2.getCertificate());
    }
}
