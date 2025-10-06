package com.model;

public class Test {
    public static void main(String[] args) {
        System.out.println("hi");
        testGame();
    }
    
    public static void testGame(){
        try {
            User Christian = new User("christian4", "Christian", "Wolff", "abc123");
            Room swearingen  = new Room();
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
