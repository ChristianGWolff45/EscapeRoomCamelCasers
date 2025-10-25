package com.model;

import java.util.ArrayList;

public class EscapeRoom {
    private UserList userList;
    private GameList gameList;
    private ArrayList<Game> games;

    public EscapeRoom() {
        userList = UserList.getInstance();
        gameList = GameList.getInstance();
        userList.setCurrentUser(null);
        gameList.setCurrentGame(null);
        games = gameList.getAllGames();

    }

    // User Methods
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

    // Game Methods
    public void startGame(Room room) {
   //     gameList.setCurrentGame(gameList.newGame(userList.getCurrentUser(), room));
    }

    public void endCurrentGame() {
        gameList.endCurrentGame();
    }

    public void loadGame() {
        
    }

    public void printGames() {
        for(Game game : games){
            System.out.println(game.getName());
        }
    }

    public boolean pickGame(String name){
        return gameList.setCurrentGame(name);
    }

    public void playStory(){
        gameList.getCurrentGame.playStory();
    }

}
