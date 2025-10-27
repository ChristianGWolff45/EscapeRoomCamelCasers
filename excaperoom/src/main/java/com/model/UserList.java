package com.model;

import java.util.ArrayList;

/**
 * Users = UserList
 * users = userList
 * <User> = <User>
 * userList = users
 */
public class UserList {
    private ArrayList<User> users;
    private static UserList userList;
    private User currentUser;

    UserList() {
        users = DataLoader.getUserList();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    /**
     * 
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     * @return true if user is created false if user already exists
     */
    public boolean addUser(String username, String firstName, String lastName, String password) {
        if (haveUser(username)) {
            System.out.println("\nAn account with that username already exists\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            System.out.println("\nYou have successfully created an account\n");
            User newUser = new User(username, firstName, lastName, password);
            users.add(newUser);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }
    /**
     * 
     * @param username
     * @return true if user exists
     */
    public boolean haveUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * @param username
     * @param password
     * @return true if username and password match
     */
    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                setCurrentUser(user);
                System.out.println("\nSuccesfully logged in as " + user.getUsername());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        System.out.println("\nInvalid Username or Password");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * saves user data to json
     */
    public void logout() {
        if (isLoggedIn()) {
            saveUsers();
            setCurrentUser(null);
            System.out.println("\nYou have been logged out");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nYou are not logged in");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 
     * @return true if any user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    /**
     * 
     * @param user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    /**
     * 
     * @return currentUSer
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }
    /**
     * writes the user to json
     */
    public void saveUsers() {
        DataWriter.saveUser();
    }
    /**
     * prints all users scores
     */
    public void printAllUserScores() {
    System.out.println("User Scores:");
    for (User user : users) {
        System.out.println(user.getUsername() + ": " + user.getScore());
    }
}

}
