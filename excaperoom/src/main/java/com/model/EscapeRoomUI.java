package com.model;

public class EscapeRoomUI {

    public static void main(String[] args) {

        EscapeRoom escapeRoom = new EscapeRoom();

        while (true) {
            MainMenu mainMenu = new MainMenu(escapeRoom);
            mainMenu.run();

            PlayMenu playMenu = new PlayMenu(escapeRoom);
            playMenu.run();
        }
    }
}
