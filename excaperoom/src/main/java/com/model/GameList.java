package com.model;

import java.util.ArrayList;

public class GameList {
    private static ArrayList<Game> games;
    private static GameList instance;
    private Game currentGame;

    private GameList() {
        games = DataLoader.getGameList();
    }

    public static GameList getInstance() {
        if (instance == null) {
            instance = new GameList();
        }
        return instance;
    }

    public ArrayList<Game> getAllGames() {
        return games;
    }
    /**
     * 
     * @param gameString
     * @return
     */
    public boolean setCurrentGame(String gameString) {
        for(Game game : games){
            if(game.getName().equals(gameString)){
                currentGame = game;
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * @return game object currentGame
     */
    public Game getCurrentGame() {
        return currentGame;
    }
    public void enterGame(){
        currentGame.getCurrentRoom().printRoom();
    }

    public void endCurrentGame() {
        if (currentGame != null ){
            currentGame.endGame();
            currentGame = null;
        }
    }

    public void addGame(Game game) {
        if (game != null) games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }
    /**
     * 
     * @param puzzleID string puzzleID
     * @return object puzzle matching puzzleID
     */
    public Puzzle findPuzzle(String puzzleID){
        return currentGame.getPuzzle(puzzleID);
    }
    /**
     * 
     * @param clueID string clueID
     * @return clue matching clueID
     */
    public Clue findClue(String clueID){
        return currentGame.getClue(clueID);
    }
    /**
     * 
     * @param hintID string hint id
     * @return hint object matching hintID
     */
    public Hint findHint(String hintID){
        return currentGame.getHint(hintID);
    }
    /**
     * 
     * @return number of puzzles
     */
    public int countPuzzles(){
        return currentGame.getPuzzleCount();
    }
}