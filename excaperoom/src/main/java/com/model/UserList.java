package com.model;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;
    private UserList userList;

    private UserList() {
        users = DataLoader.getUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void AddUser() {

    }

    private void userList() {

    }

    public UserList getInstance() {
        return this;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public void getUser(String username, String password) {
        
    }
}
