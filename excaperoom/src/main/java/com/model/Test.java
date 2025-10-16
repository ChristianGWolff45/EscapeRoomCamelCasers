package com.model;

public class Test {
    public static void testGame(){
        try {
            User Christian = new User("christian4", "Christian", "Wolff", "abc123" );
            Room swearingen = new Room(null, null, null, false, false);
            Game escapeRoom = new Game(Christian, swearingen);
            escapeRoom.Run();
            Thread.sleep(2000);
            escapeRoom.endGame();
            Certificate myCertificate = escapeRoom.getCertificate();
            System.out.println(myCertificate.toString());
        } catch (InterruptedException ex) {
        }
    }
}
