package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> getUserList(){
        ArrayList<User> userList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray peopleJSON = (JSONArray) jsonObject.get("users");

            for (int i=0; i < peopleJSON.size(); i++){
                JSONObject personJSON = (JSONObject)peopleJSON.get(i);
                UUID id = UUID.fromString((String)personJSON.get(USER_ID));
                String username = (String)personJSON.get(USER_USER_NAME);
                String firstName = (String)personJSON.get(USER_FIRST_NAME);
                String lastName = (String)personJSON.get(USER_LAST_NAME);
                String password = (String)personJSON.get(USER_PASSWORD);

                userList.add(new User(id, username, firstName, lastName, password));
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static ArrayList<Room> getRoomList() {
        ArrayList<Room> roomList = new ArrayList<>();

            try {
                FileReader reader = new FileReader(GAME_FILE_NAME);
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                JSONArray roomsJSON = (JSONArray) jsonObject.get("rooms");

                for (int i = 0; i < roomsJSON.size(); i++) {
                    JSONObject roomJSON = (JSONObject)roomsJSON.get(i);
                    String id  = (String)roomJSON.get(ROOM_ID);
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return roomList;
    }
    
}
