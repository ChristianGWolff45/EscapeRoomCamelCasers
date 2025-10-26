package com.model;

import java.util.Scanner;

public class PlayMenu {
    private String[] menuOptions = { "New Game", "Load Game", "Back" };
    private Scanner scanner;
    private EscapeRoom escapeRoom;
    private String gameName;

    public PlayMenu(EscapeRoom escapeRoom, String gameName) {
        scanner = new Scanner(System.in);
        this.escapeRoom = escapeRoom;
        this.gameName = gameName;
    }

    public boolean run() {
        while (true) {
            displayMainMenu();

            int userCommand = getUserCommand(menuOptions.length);

            if (userCommand == -1) {
                System.out.println("Not a valid command");
                continue;
            }

            switch (userCommand) {
                case (0):
                    // PlayGame
                    break;
                case (1):
                    // LoadGame
                    break;
                case (2):
                    return true;
            }
        }
    }

    private void displayMainMenu() {
    String displayName = (gameName != null) ? gameName : "Unknown Game";
    System.out.println("***************" + displayName + "**************");
    for (int i = 0; i < menuOptions.length; i++) {
        System.out.println((i + 1) + ". " + menuOptions[i]);
    }
    System.out.println();
}

    private int getUserCommand(int numCommands) {
        System.out.println("What would you like to do?");

        String input = scanner.nextLine();
        int command = Integer.parseInt(input) - 1;

        if (command >= 0 && command <= numCommands - 1)
            return command;
        return -1;
    }

}
