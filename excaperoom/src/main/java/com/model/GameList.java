package com.model;

import java.util.ArrayList;

public class GameList {
    private ArrayList<Game> games;
    private static GameList instance;
    private Game currentGame;

    private GameList() {
        games = new ArrayList<>();
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

    public Game newGame(User user, Room startingRoom) {
        Game game = new Game(user, startingRoom);
        games.add(game);
        return game;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getCurrentGame() {
        return currentGame;
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
}
