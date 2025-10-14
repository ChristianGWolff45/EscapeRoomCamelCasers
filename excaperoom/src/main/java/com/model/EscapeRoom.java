package com.model;

public class EscapeRoom {
    private UserList userList;
    private User currentUser;

    public EscapeRoom() {
        userList = UserList.getInstance();
    }

    public boolean signUp(String username, String firstName, String lastName, String password) {
        return userList.addUser(username, firstName, lastName, password, Orientation.FORWARD);
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
