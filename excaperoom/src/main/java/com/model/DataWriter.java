package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

    public static void saveUser() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getAllUsers();

        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i< users.size(); i++) {
            jsonUsers.add(getUserJSON(users.get(i)));
        }

        try (FileWriter file = new FileWriter(USER_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONObject getUserJSON(User user){
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getId().toString());
        userDetails.put(USER_USER_NAME, user.getUsername());
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_PASSWORD, user.getPassword());
        return userDetails;
    }

    public static void main(String[] args) {
        DataWriter.saveUser();
    }
}