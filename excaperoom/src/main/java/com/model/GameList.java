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

    public boolean setCurrentGame(String gameString) {
        for(Game game : games){
            if(game.getName().equals(gameString)){
                currentGame = game;
                return true;
            }
        }
        return false;
    }

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

    public Puzzle findPuzzle(String puzzleID){
        return currentGame.getPuzzle(puzzleID);
    }
    public Clue findClue(String clueID){
        return currentGame.getClue(clueID);
    }
    public Hint findHint(String hintID){
        return currentGame.getHint(hintID);
    }
    public int countPuzzles(){
        return currentGame.getPuzzleCount();
    }
}