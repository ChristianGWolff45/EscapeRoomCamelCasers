package com.model;

import java.time.Duration;
import java.time.Instant;


public class Game {
    private User user;
    private Room startingRoom;
    //private Menu mainMenu;
    private final Duration timeAllowed = Duration.ofMinutes(30);
    private Instant startTime;
    private Certificate certificate;
    
    public Game(User user, Room startingRoom){
        this.user = user;
        this.startingRoom = startingRoom;
        this.certificate = new Certificate();
    }

    public User getUser(){
        return user;
    }
    public Room getStartingRoom(){
        return startingRoom;
    }

    public Certificate getCertificate(){
        return certificate;
    }
    /**
     * 
     * @return time allowed minus time elapsed to get time left
     */
    public Duration getTimeLeft(){
        if (startTime == null) return timeAllowed;
        return timeAllowed.minus(getTimeElapsed());
    }
    /**
     * 
     * @return Duration of Time now minus start time
     */
    public Duration getTimeElapsed(){
        if (startTime == null) return timeAllowed;
        return Duration.between(startTime, Instant.now());
    }
    /**
     * 
     * @return a formatted version of time left MM:SS
     */
    public String timeLeftMMSS(){
        long totalSeconds = getTimeLeft().getSeconds();
        long minutes = totalSeconds/60;
        long seconds = totalSeconds%60;
        return String.format("%02d:%02d", minutes, seconds);

    }
    public void Run(){
        startTime = Instant.now();
    }
    public void loadGame(){
    }
    public void endGame(){
        certificate.setTimeTaken(getTimeElapsed());
    }

}
