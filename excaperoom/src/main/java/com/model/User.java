package com.model;

import java.util.UUID;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private UUID id;

    public User(String username, String firstName, String lastName, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(UUID id, String username, String firstName, String lastName, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public boolean isMatch(String userName, String password) {
        return true;
    }
}
