package com.model;

import java.util.Scanner;

public class MainMenu {
    private String[] menuOptions = { "Create Account", "Login", "Quit" };
    private Scanner scanner;
    private EscapeRoom escapeRoom;

    public MainMenu(EscapeRoom escapeRoom) {
        scanner = new Scanner(System.in);
        this.escapeRoom = escapeRoom;
    }

    public void run() {
        while (true) {
            displayMainMenu();

            int userCommand = getUserCommand(menuOptions.length);
            if (userCommand == -1) {
                System.out.println("Not a valid command");
                continue;
            }

            switch (userCommand) {
                case (0):
                    createAccount();
                    break;
                case (1):
                    if (login()) {
                        return;
                    }
                    break;
                case (2):
                    if (escapeRoom.isLoggedIn()) {
                        escapeRoom.logout();
                        System.out.println("Exiting...");
                    } else {
                        System.out.println("Exiting...");
                    }
                    System.exit(0);
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------");
        System.out.println("***************Escape from Swearingen**************");
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

    private void createAccount() {
        String username = getField("Username");
        String firstName = getField("First Name");
        String lastName = getField("Last Name");
        String password = getField("Password");
        
        if (escapeRoom.signUp(username, firstName, lastName, password)) {
            System.out.println("Account created successfully");
        } else {
            System.out.println("Username already exists.");
        }
    }


    private boolean login() {
        String username = getField("Username");
        String password = getField("Password");

        if (escapeRoom.login(username, password)) {
            System.out.println("Succesfully logged in as " + escapeRoom.getCurrentUser().getUsername());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    private String getField(String prompt) {
        System.out.println(prompt + ": ");
        return scanner.nextLine();
    }

}
