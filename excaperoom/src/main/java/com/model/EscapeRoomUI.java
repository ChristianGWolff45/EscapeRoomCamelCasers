package com.model;

public class EscapeRoomUI {
    public static void main(String[] args) {
        EscapeRoom escapeRoom = new EscapeRoom();

        while (true) {
            // Step 1: Main menu (login/signup)
            MainMenu mainMenu = new MainMenu(escapeRoom);
            mainMenu.run();

            // Step 2: Game selection menu
            GameSelectionMenu gameSelection = new GameSelectionMenu(escapeRoom);
            String selectedGame = gameSelection.run();

            if (selectedGame == null) {
                continue; // restart loop from MainMenu
            }

            // Step 3: Play menu with selected game
            PlayMenu playMenu = new PlayMenu(escapeRoom, selectedGame);
            boolean goBackToSelection = playMenu.run();

            while (goBackToSelection) {
                selectedGame = gameSelection.run();

                if (selectedGame == null) {
                    break;
                }
                playMenu = new PlayMenu(escapeRoom, selectedGame);
                goBackToSelection = playMenu.run();
            }
        }
        playGame();
    }
    private void playGame() {
        // Call manu that leads to options to start a new game or load previous save
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.signUp("LRIVERS", "Leni", "Rivers", "password");

        escapeRoom.signUp("LRIVERS45", "Leni", "Rivers", "password");
        escapeRoom.login("LRIVERS45", "password");

        System.out.println("Pick a game to play");
        escapeRoom.printGames();

        escapeRoom.pickGame("Escape from Swearingen");
        escapeRoom.playStory();

        escapeRoom.enterGame();

        escapeRoom.pickUpClue("wordlePuzzle_Clue");
        escapeRoom.pickUpClue("multipleChoice_Clue1");
        escapeRoom.pickUpClue("multipleChoice_Clue2");

        escapeRoom.useHint("directionalPuzzle_Hint1");

        escapeRoom.useHint("wordlePuzzle_Hint");

        escapeRoom.solvePuzzle("wordlePuzzle", "CRANE");
        escapeRoom.solvePuzzle("directionalPuzzle", new Direction[] { Direction.RIGHT, Direction.DOWN, Direction.UP, Direction.LEFT });
        escapeRoom.skipPuzzle("multiplechoice");

        escapeRoom.logout();

        escapeRoom.login("LRIVERS45", "password");
        escapeRoom.pickGame("Escape from Swearingen");
        escapeRoom.loadProgress();
        escapeRoom.enterGame();

        escapeRoom.goNextRoom("East Hallway");
        escapeRoom.useHint("directionalPuzzle_Hint2");
        escapeRoom.skipPuzzle("multiplechoice2");


    }

    

}
