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
    /**
     * prints out all games
     */
    public void printGames() {
        for(Game game : games){
            System.out.println(game.getName());
        }
    }

    public ArrayList<Game> getAllGames() {
        return games;
    }
    /**
     * 
     * @param name sets current game to game of the same name
     */
    public void pickGame(String name){
        gameList.setCurrentGame(name);
        userList.getCurrentUser().loadGame();
    }
    /**
     * loads user progress then displays it
     */
    public void loadProgress(){
        userList.getCurrentUser().getProgress().displayProgress();
    }
    /**
     * hear the plot to the game
     */
    public void playStory(){
        gameList.getCurrentGame().playStory();
    }
    /**
     * loads starting room
     */
    public void enterGame(){
        gameList.enterGame();
    }
    /**
     * 
     * @param clueID adds clue to user inventory
     * then displays it
     */
    public void pickUpClue(String clueID){
        userList.getCurrentUser().addClue(clueID);
        displayClue(clueID);
    }
    /**
     * 
     * @param clueID displays clue
     */

    public void displayClue(String clueID){
        gameList.findClue(clueID).displayClue();
    }

    public void hearHint(String hintID){
        gameList.findHint(hintID).hearHint();
    }
    /**
     * 
     * @param hintID uses hint for user
     * then displays it
     */
    public String useHint(String hintID){
        userList.getCurrentUser().useHint(hintID);
        return displayHint(hintID);
    }
    /**
     * 
     * @param hintID displays hint
     */
    public String displayHint(String hintID){
        return gameList.findHint(hintID).displayHint();
    }
    /**
     * @param puzzleID enter a puzzleID to solve
     * @param answer enter a string answer
     * checks if answer is answer for the puzzle
     * if so attempts to unlock neighboring doors
     * if last puzzle endsgame
     */
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
    /**
     * @param puzzleID enter a puzzleID to solve
     * @param answer enter an array of directions
     * checks if answer is answer for the puzzle
     * if so attempts to unlock neighboring doors
     * if last puzzle endsgame
     */

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
    /**
     * @param puzzleID enter a puzzleID to skip
     * skips puzzle for current user attempts to unlock neighboring doors
     * if last puzzle endsgame
     */
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
        DataWriter.saveCertificate();

    }

    public boolean puzzleUnlocked(String puzzleID){
        return gameList.findPuzzle(puzzleID).isCompleted();
    }

}