package com.model;

public class EscapeRoom {
    private UserList userList;
    private GameList gameList;

    public EscapeRoom() {
        userList = UserList.getInstance();
        gameList = GameList.getInstance();
        userList.setCurrentUser(null);
        gameList.setCurrentGame(null);
    }

    public boolean signUp(String username, String firstName, String lastName, String password) {
        boolean added = userList.addUser(username, firstName, lastName, password);
        return added;
    }

    public boolean login(String username, String password) {
       boolean loggedIn = userList.loginUser(username, password);
       return loggedIn;
    }

    public boolean isLoggedIn(){
        return userList.getCurrentUser() != null;
    }

    public User getCurrentUser() {
        return userList.getCurrentUser();
    }

    public void logout() {
        userList.saveUsers();
    }

    public void startGame(Room room) {
        gameList.setCurrentGame(gameList.newGame(userList.getCurrentUser(), room));
    }

    public void endCurrentGame() {
        gameList.endCurrentGame();
    }

    public void loadGame() {

    }

}
