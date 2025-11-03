package com.model;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Focused JUnit 4 tests for verifying correct creation and initialization
 * of User objects.
 */
public class UserTester {

    private User user;

    @Before
    public void setUp() {
        user = new User("jdoe", "John", "Doe", "secret");
    }

    /*** ---- BASIC CREATION TESTS ---- ***/

    @Test
    public void testNewUserNotNull() {
        assertNotNull("User object should be created", user);
    }

    @Test
    public void testUserFieldsInitializedCorrectly() {
        assertEquals("jdoe", user.getUsername());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("secret", user.getPassword());
    }

    @Test
    public void testNonNullComponentsInitialized() {
        assertNotNull("Progress should be initialized", user.getProgress());
        assertNotNull("Inventory should be initialized", user.getInventory());
        assertNotNull("Certificate should be initialized", user.getCertificate());
    }

    @Test
    public void testOrientationDefaultsToNull() {
        assertNull("Orientation should start as null", user.getOrientation());
    }

    @Test
    public void testComponentsAreIndependentInstances() {
        User another = new User("user2", "Jane", "Roe", "pwd");
        assertNotSame("Each user should have its own Inventory",
                user.getInventory(), another.getInventory());
        assertNotSame("Each user should have its own Progress",
                user.getProgress(), another.getProgress());
        assertNotSame("Each user should have its own Certificate",
                user.getCertificate(), another.getCertificate());
    }

    /*** ---- SECOND CONSTRUCTOR TESTS ---- ***/

    @Test
    public void testPreExistingUserConstructorUsesGivenValues() {
        UUID id = UUID.randomUUID();
        Inventory inv = new Inventory();
        Progress prog = new Progress();
        Certificate cert = new Certificate();

        User loaded = new User(id, "asmith", "Alice", "Smith", "pass123", inv, prog, cert);

        assertEquals("Username should match constructor input", "asmith", loaded.getUsername());
        assertEquals("First name should match constructor input", "Alice", loaded.getFirstName());
        assertEquals("Last name should match constructor input", "Smith", loaded.getLastName());
        assertEquals("Password should match constructor input", "pass123", loaded.getPassword());
        assertEquals("UUID should be preserved", id, loaded.getId());
        assertSame("Inventory instance should be reused", inv, loaded.getInventory());
        assertSame("Progress instance should be reused", prog, loaded.getProgress());
        assertSame("Certificate instance should be reused", cert, loaded.getCertificate());
    }

    /*** ---- EDGE CASE INPUT TESTS ---- ***/

    @Test
    public void testUserCreationWithEmptyStrings() {
        User emptyUser = new User("", "", "", "");
        assertEquals("", emptyUser.getUsername());
        assertEquals("", emptyUser.getFirstName());
        assertEquals("", emptyUser.getLastName());
        assertEquals("", emptyUser.getPassword());
        assertNotNull("Inventory should still be created", emptyUser.getInventory());
        assertNotNull("Progress should still be created", emptyUser.getProgress());
        assertNotNull("Certificate should still be created", emptyUser.getCertificate());
    }

    @Test
    public void testUserCreationWithNullValues() {
        // Constructor does not guard against nulls, but should still not throw
        User nullUser = new User(null, null, null, null);
        assertNull("Username may be null", nullUser.getUsername());
        assertNull("First name may be null", nullUser.getFirstName());
        assertNull("Last name may be null", nullUser.getLastName());
        assertNull("Password may be null", nullUser.getPassword());
        assertNotNull("Inventory should still be initialized", nullUser.getInventory());
        assertNotNull("Progress should still be initialized", nullUser.getProgress());
        assertNotNull("Certificate should still be initialized", nullUser.getCertificate());
    }

    /*** ---- IMMUTABILITY & STABILITY TESTS ---- ***/

    @Test
    public void testUUIDRemainsConstantAfterCreation() {
        UUID idBefore = user.getId();
        UUID idAfter = user.getId();
        assertEquals("UUID should not change", idBefore, idAfter);
    }

    @Test
    public void testToStringContainsExpectedFields() {
        String output = user.toString();
        assertTrue("Username should appear in toString", output.contains("jdoe"));
        assertTrue("First name should appear in toString", output.contains("John"));
        assertTrue("Last name should appear in toString", output.contains("Doe"));
    }
}
