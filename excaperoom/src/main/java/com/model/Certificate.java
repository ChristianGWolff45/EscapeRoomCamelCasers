package com.model;
/**
 * @author Christian Wolff
 */
import java.time.Duration;


public class Certificate {
    private Duration timeTaken;
    private int hintsUsed;
    private int skipsUsed;
    public Certificate(){
        this.hintsUsed = 0;
        this.skipsUsed= 0;
    }
    public Duration getTimeTaken(){
        return this.timeTaken;
    }
    public void setTimeTaken(Duration timeElapsed){
        this.timeTaken = timeElapsed;
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
}
