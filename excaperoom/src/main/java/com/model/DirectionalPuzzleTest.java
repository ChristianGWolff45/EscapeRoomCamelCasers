package com.model;

public class DirectionalPuzzleTest {
    public static void main(String[] args) {
        DirectionalPuzzle lock = new DirectionalPuzzle(null, null, ("updowndowndown"));
        direction[] guess = new direction[]{direction.UP, direction.DOWN, direction.DOWN, direction.DOWN};
        lock.enterAnswer(guess);
        System.out.println(lock.isCompleted());
    }
    
}
