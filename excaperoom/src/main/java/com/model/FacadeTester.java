package com.model;

public class FacadeTester {
    public void userMethodsTest() {
        EscapeRoom escapeRoom = new EscapeRoom();
        // Test user sign-up
        String username = "FacadeTestUser";
        String firstName = "Test_FirstName";
        String lastName = "Test_LastName";
        String password = "Test_Password";

        boolean signUpSuccess = escapeRoom.signUp(username, firstName, lastName, password);
        System.out.println("Sign-up successful: " + signUpSuccess);

        // Test user login
        boolean loginSuccess = escapeRoom.login(username, password);
        System.out.println("Login successful: " + loginSuccess);

        // Verify current user
        User currentUser = escapeRoom.getCurrentUser();
        System.out.println("Current user: " + (currentUser != null ? currentUser.getUsername() : "None"));

        // Logout
        escapeRoom.logout();
        System.out.println("User logged out.");
    }

    public void run() {
        userMethodsTest();
    }

    public static void main(String[] args) {
        new FacadeTester().run();
    }
}
