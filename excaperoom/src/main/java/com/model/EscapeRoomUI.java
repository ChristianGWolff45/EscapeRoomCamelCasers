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
    }
}
