package com.model;

import java.time.Duration;


public class Certificate {
    private Duration timeTaken;
    private int hintsUsed;
    private int skipsUsed;
    public Certificate(){
        this.hintsUsed = 0;
        this.skipsUsed= 0;
        this.timeTaken = Duration.ZERO;
    }
    public Certificate(int timeTaken, int hintsUsed, int skipsUsed){
        this.hintsUsed = hintsUsed;
        this.skipsUsed= skipsUsed;
        this.timeTaken = (Duration.ofMillis((long) timeTaken));
    }
    public Duration getTimeTaken(){
        return this.timeTaken;
    }
    public int getTimeTakenInt(){
        return (int) timeTaken.getSeconds() * 1000;
    }
    public void setTimeTaken(Duration timeElapsed){
        this.timeTaken = timeElapsed;
    }
    public void addTime(int timeElapsed){
        this.timeTaken = (Duration.ofMillis((long) timeElapsed + (timeTaken.getSeconds() * 1000)));
    }
    public int getHintsUsed(){
        return hintsUsed;
    }
    public int getSkipsUsed(){
        return skipsUsed;
    }
    /**
     * adds one to hintsUsed
     */
    public void useHint(){
        hintsUsed++;
    }
    /**
     * adds one to skips used
     */
    public void useSkip(){
        skipsUsed++;
    }
    public String timeTakenMMSS(){
        long totalSeconds = timeTaken.getSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    public String toString(){
        return "Skips Used: " + skipsUsed + "\nHints Used: " + hintsUsed + "\nTime Taken: " + timeTakenMMSS();
    }
}
