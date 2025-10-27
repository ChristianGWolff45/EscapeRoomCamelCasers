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
        JSONObject output = new JSONObject();
        output.put(USER_LIST, "userList");
        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i< users.size(); i++) {
            jsonUsers.add(getUserJSON(users.get(i)));
        }
        output.put(USER_USERS, jsonUsers);

        try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
            file.write(output.toJSONString());
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
        JSONObject userInventory = new JSONObject();
        JSONArray userClues = new JSONArray();
        JSONArray userHints = new JSONArray();

        for(String clueID : user.getInventory().getClueIDs()){
            userClues.add(clueID);
        }

        for(String hintID : user.getInventory().getHintIDS()){
            userHints.add(hintID);
        }

        userInventory.put(USER_INVENTORY_CLUES, userClues);
        userInventory.put(USER_INVENTORY_HINTS, userHints);

        userDetails.put(USER_INVENTORY, userInventory);

        JSONObject userProgress = new JSONObject();
        JSONArray userProgressSkips = new JSONArray();
        JSONArray userProgressHints = new JSONArray();
        JSONArray userProgressPuzzles = new JSONArray();

        for(String userSkip : user.getProgress().getSkips()){
            userProgressSkips.add(userSkip);
        }
        for(String userHint : user.getProgress().getHints()){
            userProgressHints.add(userHint);
        }
        for(String userPuzzle : user.getProgress().getPuzzles()){
            userProgressPuzzles.add(userPuzzle);
        }

        userProgress.put(USER_PROGRESS_SKIPS, userProgressSkips);
        userProgress.put(USER_PROGRESS_HINTS, userProgressHints);
        userProgress.put(USER_PROGRESS_PUZZLES, userProgressPuzzles);

        userDetails.put(USER_PROGRESS, userProgress);

        JSONObject userCertificate = new JSONObject();
        userCertificate.put(USER_CERTIFICATE_TIME_TAKEN, user.getCertificate().getTimeTakenInt());
        userCertificate.put(USER_CERTIFICATE_HINTS_USED, user.getCertificate().getHintsUsed());
        userCertificate.put(USER_CERTIFICATE_SKIPS_USED, user.getCertificate().getSkipsUsed());


        userDetails.put(USER_CERTIFICATE, userCertificate);

        return userDetails;
    }

    public static void saveCertificate(){
        UserList userList = UserList.getInstance();
        User user = userList.getCurrentUser();
        JSONObject output = new JSONObject();
        output.put(GAME, GameList.getInstance().getCurrentGame().getName());
        
        JSONObject certificate = new JSONObject();
        certificate.put(USER_CERTIFICATE_TIME_TAKEN, user.getCertificate().getTimeTakenInt());
        certificate.put(USER_CERTIFICATE_HINTS_USED, user.getCertificate().getHintsUsed());
        certificate.put(USER_CERTIFICATE_SKIPS_USED, user.getCertificate().getSkipsUsed());
        certificate.put(USER_SCORE, user.getScore());

        output.put(USER_CERTIFICATE, certificate);
        try (FileWriter file = new FileWriter(USER_TEMP_CERTIFICATE)) {
            file.write(output.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataWriter.saveUser();
    }
}