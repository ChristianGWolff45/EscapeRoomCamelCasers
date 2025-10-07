package com.model;

import java.util.UUID;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private UUID id;
    private Orientation orientation;

    //for creating a user
    public User(String username, String firstName, String lastName, String password, Orientation orientation) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.orientation = orientation;
    }

    //for loading pre-existing user
    public User(UUID id, String username, String firstName, String lastName, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public boolean isMatch(String userName, String password) {
        return this.username.equals(userName) && this.password.equals(password);
    }

    public String getUsername(){
        return username;
    }

     public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword(){
        return password;
    }

    public UUID getId(){
        return id;
    }

    public Orientation getOrientation(){
        return orientation;
    }
}
