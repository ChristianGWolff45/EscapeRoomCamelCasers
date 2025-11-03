package com.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataLoaderTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String userFilePath;
    private String gameFilePath;

    @Before
    public void setUp() throws Exception {
        userFilePath = tempFolder.newFile("tempUsers.json").getAbsolutePath();
        gameFilePath = tempFolder.newFile("tempGames.json").getAbsolutePath();

        DataLoader.setUserFilePath(userFilePath);
        DataLoader.setGameFilePath(gameFilePath);
    }

    @Test
    public void testGetUserList_emptyFile_returnsEmptyList() throws IOException {
        try (FileWriter writer = new FileWriter(userFilePath)) {
            writer.write("{\"users\":[]}");
        }

        ArrayList<User> users = DataLoader.getUserList();
        assertTrue("Expected empty user list", users.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetUserList_singleUser_returnsCorrectUser() throws IOException {
        UUID userId = UUID.randomUUID();
        JSONObject user = new JSONObject();
        user.put(DataConstants.USER_ID, userId.toString());
        user.put(DataConstants.USER_USER_NAME, "jdoe");
        user.put(DataConstants.USER_FIRST_NAME, "John");
        user.put(DataConstants.USER_LAST_NAME, "Doe");
        user.put(DataConstants.USER_PASSWORD, "password");

        JSONObject inventory = new JSONObject();
        inventory.put(DataConstants.USER_INVENTORY_CLUES, new JSONArray());
        inventory.put(DataConstants.USER_INVENTORY_HINTS, new JSONArray());
        user.put(DataConstants.USER_INVENTORY, inventory);

        JSONObject progress = new JSONObject();
        progress.put(DataConstants.USER_PROGRESS_SKIPS, new JSONArray());
        progress.put(DataConstants.USER_PROGRESS_HINTS, new JSONArray());
        progress.put(DataConstants.USER_PROGRESS_PUZZLES, new JSONArray());
        user.put(DataConstants.USER_PROGRESS, progress);

        JSONObject certificate = new JSONObject();
        certificate.put(DataConstants.USER_CERTIFICATE_TIME_TAKEN, 0L);
        certificate.put(DataConstants.USER_CERTIFICATE_HINTS_USED, 0L);
        certificate.put(DataConstants.USER_CERTIFICATE_SKIPS_USED, 0L);
        user.put(DataConstants.USER_CERTIFICATE, certificate);

        JSONArray usersArray = new JSONArray();
        usersArray.add(user);

        JSONObject root = new JSONObject();
        root.put("users", usersArray);

        try (FileWriter writer = new FileWriter(userFilePath)) {
            writer.write(root.toJSONString());
        }

        ArrayList<User> users = DataLoader.getUserList();
        assertTrue("Expected user list size 1", users.size() == 1);
        assertTrue("Expected username to be 'jdoe'", users.get(0).getUsername().equals("jdoe"));
    }

    @Test
    public void testGetGameList_emptyFile_returnsEmptyList() throws IOException {
        try (FileWriter writer = new FileWriter(gameFilePath)) {
            writer.write("{\"games\":[]}");
        }

        ArrayList<Game> games = DataLoader.getGameList();
        assertTrue("Expected empty game list", games.isEmpty());
    }
}