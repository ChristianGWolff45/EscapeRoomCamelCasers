package com.model;

import java.util.Scanner;

public class EscapeRoomUI {
    private String[] mainMenuOptions = { "Play", "Create Account", "Login", "Logout", "Quit" };
    private Scanner scanner;
    private UserList userList;

    EscapeRoomUI() {
        scanner = new Scanner(System.in);
        userList = UserList.getInstance();
    }

    public void run() {

        while (true) {
            displayMainMenu();

            int userCommand = getUserCommand(mainMenuOptions.length);

            if (userCommand == -1) {
                System.out.println("Not a valid command");
                continue;
            }

            if (userCommand == mainMenuOptions.length - 1) {
                if (userList.isLoggedIn()) {
                    userList.logout();
                    System.out.println("\nYou have been logged out\n \nExiting...");
                }
                break;
            }

            switch (userCommand) {
                case (0):
                    playGame();
                    break;
                case (1):
                    createAccount();
                    break;
                case (2):
                    login();
                    break;
                case (3):
                    logout();
                    break;
            }
        }
        System.out.println("Exiting...");
    }

    private void displayMainMenu() {
        System.out.println("***************Escape from Swearingen**************");
        for (int i = 0; i < mainMenuOptions.length; i++) {
            System.out.println((i + 1) + ". " + mainMenuOptions[i]);
        }
        System.out.println("\n");
    }

    private int getUserCommand(int numCommands) {
        System.out.println("What would you like to do?");

        String input = scanner.nextLine();
        int command = Integer.parseInt(input) - 1;

        if (command >= 0 && command <= numCommands - 1)
            return command;
        return -1;
    }

    private void playGame() {
        // Call manu that leads to options to start a new game or load previous save
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.signUp("LRIVERS", "Leni", "Rivers", "password");

        escapeRoom.signUp("LRIVERS45", "Leni", "Rivers", "password");
        escapeRoom.login("LRIVERS45", "password");

        System.out.println("Pick a game to play");
        escapeRoom.printGames();

        if(!escapeRoom.pickGame("Escape from Swearingen")){
            System.out.println("error occured");
        }
        escapeRoom.playStory();


    }

    private void createAccount() {
        String username = getField("Username");
        String firstName = getField("First Name");
        String lastName = getField("Last Name");
        String password = getField("Password");
        userList.addUser(username, firstName, lastName, password);
    }

    private void login() {
        if (userList.isLoggedIn()) {
            System.out.println("\nYou are already logged in as " + userList.getCurrentUser().getUsername() + "\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        String username = getField("Username");
        String password = getField("Password");
        userList.loginUser(username, password);
    }

    private void logout() {
        userList.logout();
    }

    private String getField(String prompt) {
        System.out.println(prompt + ": ");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        EscapeRoomUI escapeRoomInterface = new EscapeRoomUI();
        escapeRoomInterface.run();
    }
}
