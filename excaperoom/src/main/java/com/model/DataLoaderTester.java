package com.model;

import java.util.ArrayList;

public class DataLoaderTester {

    public static void main(String[] args) {
        System.out.println("========== DATA LOADER TESTER ==========\n");
        
        testGetUserList();
        System.out.println("\n========================================\n");
        testGetGameList();
        
        System.out.println("\n========== TESTING COMPLETE ==========");
    }

    /**
     * Tests the getUserList() method from DataLoader
     */
    private static void testGetUserList() {
        System.out.println("Testing getUserList()...\n");
        
        try {
            ArrayList<User> users = DataLoader.getUserList();
            
            if (users == null) {
                System.out.println("ERROR: getUserList() returned null");
                return;
            }
            
            if (users.isEmpty()) {
                System.out.println("WARNING: No users loaded from file");
                return;
            }
            
            System.out.println("Successfully loaded " + users.size() + " user(s)\n");
            
            // Display each user's information
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                System.out.println("User #" + (i + 1) + ":");
                System.out.println("  ID: " + user.getId());
                System.out.println("  Username: " + user.getUsername());
                System.out.println("  First Name: " + user.getFirstName());
                System.out.println("  Last Name: " + user.getLastName());
                System.out.println("  Password: " + (user.getPassword() != null ? "****" : "null"));
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred while testing getUserList()");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getGameList() method from DataLoader
     */
    private static void testGetGameList() {
        System.out.println("Testing getGameList()...\n");
        
        try {
            ArrayList<Game> games = DataLoader.getGameList();
            
            if (games == null) {
                System.out.println("ERROR: getGameList() returned null");
                return;
            }
            
            if (games.isEmpty()) {
                System.out.println("WARNING: No games loaded from file");
                return;
            }
            
            System.out.println("Successfully loaded " + games.size() + " game(s)\n");
            
            // Display each game's information
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                System.out.println("Game #" + (i + 1) + ":");
                
                // User information
                User user = game.getUser();
                if (user != null) {
                    System.out.println("  User: " + user.getUsername() + 
                                     " (" + user.getFirstName() + " " + user.getLastName() + ")");
                } else {
                    System.out.println("  User: null");
                }
                
                // Starting room information
                Room startingRoom = game.getCurrentRoom();
                if (startingRoom != null) {
                    System.out.println("  Starting Room: " + startingRoom.getName());
                    System.out.println("  Room Unlocked: " + startingRoom.isUnlocked());
                    System.out.println("  Is Exit: " + startingRoom.isExit());
                    
                    // Puzzle information
                    ArrayList<Puzzle> puzzles = startingRoom.getPuzzles();
                    System.out.println("  Number of Puzzles: " + 
                                     (puzzles != null ? puzzles.size() : 0));
                    
                    if (puzzles != null && !puzzles.isEmpty()) {
                        for (int j = 0; j < puzzles.size(); j++) {
                            Puzzle puzzle = puzzles.get(j);
                            System.out.println("    Puzzle #" + (j + 1) + ":");
                            System.out.println("      Type: " + puzzle.getClass().getSimpleName());
                            System.out.println("      Completed: " + puzzle.isCompleted());
                            System.out.println("      Clues: " + 
                                             (puzzle.getClues() != null ? puzzle.getClues().size() : 0));
                            System.out.println("      Hints: " + 
                                             (puzzle.getHints() != null ? puzzle.getHints().size() : 0));
                        }
                    }
                } else {
                    System.out.println("  Starting Room: null");
                }
                
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred while testing getGameList()");
            e.printStackTrace();
        }
    }
}