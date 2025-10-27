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

    public void playGame() {
        Game currentGame = gameList.getCurrentGame();

        currentGame.Run();
        currentGame.playStory();
        gameList.enterGame();
    }
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

    public ArrayList<Game> getAllGames() {
        return games;
    }

    public void pickGame(String name){
        gameList.setCurrentGame(name);
        userList.getCurrentUser().loadGame();
    }

    public void loadProgress(){
        userList.getCurrentUser().getProgress().displayProgress();
    }

    public void playStory(){
        gameList.getCurrentGame().playStory();
    }

    public void enterGame(){
        gameList.enterGame();
    }

    public void pickUpClue(String clueID){
        userList.getCurrentUser().addClue(clueID);
        displayClue(clueID);
    }

    public void displayClue(String clueID){
        gameList.findClue(clueID).displayClue();
    }

    public void useHint(String hintID){
        userList.getCurrentUser().useHint(hintID);
        displayHint(hintID);
    }

    public void displayHint(String hintID){
        gameList.findHint(hintID).displayHint();
    }

    public void solvePuzzle(String puzzleID, String answer){
        gameList.findPuzzle(puzzleID).enterAnswer(answer);
        if(gameList.findPuzzle(puzzleID).isCompleted()){
            System.out.println("Puzzle completed");
            userList.getCurrentUser().getProgress().completePuzzle(puzzleID);
            if(gameList.getCurrentGame().getCurrentRoom().unlockNeighbors() && gameList.getCurrentGame().getCurrentRoom().isExit()){
                endGame();
            }

        }
       
    }

    public void solvePuzzle(String puzzleID, Direction[] answer){
        gameList.findPuzzle(puzzleID).enterAnswer(answer);
        if(gameList.findPuzzle(puzzleID).isCompleted()){
            System.out.println("Puzzle completed");
            userList.getCurrentUser().getProgress().completePuzzle(puzzleID);
            if(gameList.getCurrentGame().getCurrentRoom().unlockNeighbors() && gameList.getCurrentGame().getCurrentRoom().isExit()){
                endGame();
            }

        }
    }

    public void skipPuzzle(String puzzleID){
        gameList.findPuzzle(puzzleID).skipPuzzle();
        if(gameList.findPuzzle(puzzleID).isCompleted()){
            System.out.println("Puzzle completed");
            userList.getCurrentUser().getProgress().completePuzzle(puzzleID);
            userList.getCurrentUser().getProgress().addSkip(puzzleID);
            userList.getCurrentUser().useSkip();
            if(gameList.getCurrentGame().getCurrentRoom().unlockNeighbors() && gameList.getCurrentGame().getCurrentRoom().isExit()){
                endGame();
            }
        }
    }

    public void goNextRoom(String roomID){
        gameList.getCurrentGame().getCurrentRoom().unlockNeighbors();
        gameList.getCurrentGame().goNextRoom(roomID);
        System.out.println(gameList.getCurrentGame().getCurrentRoom().getName());
        gameList.getCurrentGame().getCurrentRoom().printRoom();
    }

    public void endGame(){
        System.out.println("You escaped");
        userList.saveUsers();
        userList.printAllUserScores();
    }


}