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

    private UserList() {
        users = DataLoader.getUserList();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(String username, String firstName, String lastName, String password) {
        if (haveUser(username))
            return false;
        boolean added = users.add(new User(username, firstName, lastName, password));
        if (!added)
            return false;
        saveUsers();
        return true;
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public boolean haveUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean logInUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public void saveUsers() {
        DataWriter.saveUser();
    }
}
