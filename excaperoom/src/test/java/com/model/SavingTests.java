package com.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.Database;
import model.Library;
import model.User;


public class SavingTests {
    @Test
    public void testSaveUsers() {
        Library library = Library.getInstance();
        library.createAccount("bfrench", "Ben", "French", 16, "803-747-8899");
        Database.saveUsers();
        ArrayList<User> users = Database.getUsers();
        boolean found = false;

        for(User user : users){
            if(user.getFirstName().equalsIgnoreCase("Ben")){
                found = true;
            }
        }

        assertTrue(found);
    }
}