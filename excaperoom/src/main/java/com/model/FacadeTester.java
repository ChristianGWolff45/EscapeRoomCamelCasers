package com.model;

import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class FacadeTester {

    public void displayUserList() {
        ArrayList<User> users = UserList.getInstance().getAllUsers();

        System.out.println("\nUser List");

        for(int i=0; i < users.size(); i++){
            System.out.println((i+1) + ". " + users.get(i));
        }
    }

    public boolean testLoginLogout() {
        try {
            EscapeRoom room = new EscapeRoom();
            UserList ul = UserList.getInstance();

            String username = "facade_test_user";
            String password = "testPass123";

            if (ul.haveUser(username)) {
                username = username + System.currentTimeMillis();
            }

            boolean signed = room.signUp(username, "Facade", "Tester", password);
            if (!signed) {
                System.out.println("[testLoginLogout] Sign-up failed");
                return false;
            }

            boolean loggedIn = room.login(username, password);
            if (!loggedIn) {
                System.out.println("[testLoginLogout] Login returned false");
                return false;
            }

            User current = room.getCurrentUser();
            if (current == null) {
                System.out.println("[testLoginLogout] getCurrentUser returned null after login");
                return false;
            }
            if (!username.equals(current.getUsername())) {
                System.out.println("[testLoginLogout] current user mismatch: expected=" + username + " got=" + current.getUsername());
                return false;
            }

            room.logout();

            Path userTemp = Path.of("json", "users_temp.json");
            if (!Files.exists(userTemp)) {
                System.out.println("[testLoginLogout] users_temp.json not found after logout");
                return false;
            }
            String contents = Files.readString(userTemp, StandardCharsets.UTF_8);
            if (!contents.contains(username)) {
                System.out.println("[testLoginLogout] users_temp.json does not contain the username");
                return false;
            }

            System.out.println("[testLoginLogout] PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("[testLoginLogout] EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        testLoginLogout();

        displayUserList();
    }

    public static void main(String[] args) {
        new FacadeTester().run();
    }
}
