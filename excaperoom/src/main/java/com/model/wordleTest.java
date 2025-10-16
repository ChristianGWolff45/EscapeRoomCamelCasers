package com.model;

public class wordleTest {
    public static void main(String[] args) {
        Wordle test = new Wordle(null, null, "Peach");
        String[][] response = test.guessAnswer("apple");

        for(int i = 0; i < response.length; i++){
            System.out.println(response[i][0] + " ," + response[i][1]);
        }
    }

    
}
