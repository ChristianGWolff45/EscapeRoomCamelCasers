package com.model;

import java.util.Scanner;

public class GameSelectionMenu {
    private Scanner scanner;
    private EscapeRoom escapeRoom;

    public GameSelectionMenu(EscapeRoom escapeRoom) {
        scanner = new Scanner(System.in);
        this.escapeRoom = escapeRoom;
    }

    public String run() {
        while (true) {
            displayGames();
            System.out.println((escapeRoom.getAllGames().size() + 1) + ". Back");

            int choice = getUserCommand(escapeRoom.getAllGames().size() + 1);
            if (choice == -1) {
                System.out.println("Not a valid option. Try again.");
                continue;
            }

            if (choice == escapeRoom.getAllGames().size()) {
                 if (escapeRoom.isLoggedIn()) {
                        escapeRoom.logout();
                        System.out.println("Logging out and returning to main menu...");
                    }
                return null; 
            }
            
            String selectedGame = escapeRoom.getAllGames().get(choice).getName();
            escapeRoom.pickGame(selectedGame);
            System.out.println("You selected: " + selectedGame);
            return selectedGame;
        }
    }

    private void displayGames() {
        System.out.println("***************Select a Game**************");
        int index = 1;
        for (Game game : escapeRoom.getAllGames()) {
            System.out.println(index + ". " + game.getName());
            index++;
        }
        System.out.println();
    }

    private int getUserCommand(int numOptions) {
        System.out.print("Select a game: ");
        try {
            int command = Integer.parseInt(scanner.nextLine()) - 1;
            if (command >= 0 && command < numOptions)
                return command;
        } catch (NumberFormatException e) {

        }
        return -1;
    }
}
