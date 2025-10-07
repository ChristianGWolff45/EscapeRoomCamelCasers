package com.model;

import java.util.Scanner;

public class EscapeRoomUI {
    private static final String WELCOME_TEXT = "Welcome to our game";
    private String[] mainMenuOptions = {"Test", "Create Account", "Login", "Logout"};
    private Scanner scanner;
    private EscapeRoom escapeRoom;
    private Test test;

    EscapeRoomUI(){
        scanner = new Scanner(System.in);
        escapeRoom = new EscapeRoom();
    }

    public void run(){
        System.out.println(WELCOME_TEXT);

        while(true){
            displayMainMenu();

            int userCommand = getUserCommand(mainMenuOptions.length);
            
            if (userCommand == -1) {
                System.out.println("Not a valid command");
                continue;
            }

            if(userCommand == mainMenuOptions.length -1){
                escapeRoom.logout();
                break;
            }

            switch(userCommand){
                case (0):
                    Test.testGame();
                    break;
                case (1):
                    createAccount();
                    break;
                case (2):
                    login();
                    break;
            }
        }
        System.out.println("Good bye...");
    }

    private void displayMainMenu(){
        System.out.println("***************Escape from Swearingen**************");
        for (int i=0; i < mainMenuOptions.length; i++){
            System.out.println((i+1) + ". " + mainMenuOptions[i]);
        }
        System.out.println("\n");
    }

    private int getUserCommand(int numCommands){
        System.out.println("What would you like to do?");

        String input = scanner.nextLine();
        int command = Integer.parseInt(input) - 1;

        if (command >= 0 && command <= numCommands -1) return command;
        return -1;
    }

    private void testGame(){
        System.out.println("Testing...\n");
        Test.testGame();
    }
    
    private void createAccount(){
        String username = getField("Username");
        String firstName = getField("First Name");
        String lastName = getField("Last Name");
        String password = getField("Password");

        if(escapeRoom.signUp(username, firstName, lastName, password)){
            System.out.println("\nYou have successfully created an account\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nAn account with that username already exists\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(){
        String username = getField("Username");
        String password = getField("Password");

        if (escapeRoom.login(username, password)) {
            User currentUser = escapeRoom.getCurrentUser();
            System.out.println("\nSuccesfuly logged in as " + currentUser.getUsername() + "\nWelcome " + currentUser.getFirstName() + " " + currentUser.getLastName() + "\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nInvalid Username or Password\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getField(String prompt){
        System.out.println(prompt + ": ");
        return scanner.nextLine();
    }
    
    public static void main(String [] args) {
        EscapeRoomUI escapeRoomInterface = new EscapeRoomUI();
        escapeRoomInterface.run();
    }
}

