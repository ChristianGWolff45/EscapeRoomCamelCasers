package com.model;

public class EscapeRoom {
    private UserList userList;
    private User currentUser;

    public EscapeRoom() {
        userList = UserList.getInstance();
    }

    public boolean signUp(String username, String firstName, String lastName, String password) {
        boolean added = userList.addUser(username, firstName, lastName, password);
        if (added) {
            userList.saveUsers();
        }
        return added;
    }

    public boolean login(String username, String password) {
       User user = userList.getUser(username,password);
       if (user == null){
        return false;
       }
       currentUser = user;
       return true;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void logout() {
        userList.saveUsers();
    }
}
