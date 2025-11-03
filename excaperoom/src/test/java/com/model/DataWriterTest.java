package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataWriterTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String userFilePath;
    private String certificateFilePath;

    @Before
    public void setUp() throws Exception {
        userFilePath = tempFolder.newFile("tempUsers.json").getAbsolutePath();
        certificateFilePath = tempFolder.newFile("tempCertificate.json").getAbsolutePath();

        DataWriter.setUserFilePath(userFilePath);
        DataWriter.setTempCertificatePath(certificateFilePath);

        UserList userList = UserList.getInstance();
        userList.getAllUsers().clear();
        userList.setCurrentUser(null);

        GameList gameList = GameList.getInstance();
        gameList.getAllGames().clear();
        gameList.setCurrentGame(null);
    }

    @Test
    public void testSaveUser_emptyList_createsEmptyJSON() throws Exception {
        DataWriter.saveUser();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(userFilePath));
        JSONArray users = (JSONArray) obj.get(DataConstants.USER_USERS);

        assertTrue("User list should be empty", users.isEmpty());
    }

    @Test
    public void testSaveUser_singleUser_savesCorrectly() throws Exception {
        UserList.getInstance().addUser("testuser", "Test", "User", "pass");

        DataWriter.saveUser();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(userFilePath));
        JSONArray users = (JSONArray) obj.get(DataConstants.USER_USERS);

        assertEquals("User list size should be 1", 1, users.size());

        JSONObject savedUser = (JSONObject) users.get(0);
        assertEquals("Username should match", "testuser", savedUser.get(DataConstants.USER_USER_NAME));
        assertEquals("First name should match", "Test", savedUser.get(DataConstants.USER_FIRST_NAME));
        assertEquals("Last name should match", "User", savedUser.get(DataConstants.USER_LAST_NAME));
        assertEquals("Password should match", "pass", savedUser.get(DataConstants.USER_PASSWORD));
    }

    @Test
    public void testSaveCertificate_createsFileWithContent() throws Exception {
        UserList.getInstance().addUser("testuser", "Test", "User", "pass");
        User user = UserList.getInstance().getAllUsers().get(0);
        UserList.getInstance().setCurrentUser(user);

        //Create and set up a game with all required parameters
        String dummyGameName = "Dummy Escape Game";
        Game dummyGame = new Game(
            dummyGameName,
            user,
            null,
            new java.util.HashMap<>(),
            new java.util.HashMap<>(),
            new java.util.HashMap<>(), 
            "Test Story", 
            new java.util.ArrayList<>() 
        );
        
        GameList.getInstance().addGame(dummyGame);
        GameList.getInstance().setCurrentGame(dummyGameName);
        
        Game currentGame = GameList.getInstance().getCurrentGame();
        assertTrue("Current game should be set", currentGame != null);
        assertEquals("Game name should match", dummyGameName, currentGame.getName());

        Certificate cert = user.getCertificate();

        Field hintsField = Certificate.class.getDeclaredField("hintsUsed");
        hintsField.setAccessible(true);
        hintsField.setInt(cert, 1); // 1 hint used

        Field skipsField = Certificate.class.getDeclaredField("skipsUsed");
        skipsField.setAccessible(true);
        skipsField.setInt(cert, 0); // 0 skips used

        Field timeField = Certificate.class.getDeclaredField("timeTaken");
        timeField.setAccessible(true);
        timeField.set(cert, java.time.Duration.ofMillis(120000)); // 2 minutes

        user.setScore();

        DataWriter.saveCertificate();

        File file = new File(certificateFilePath);
        assertTrue("Certificate file should exist", file.exists());

        String content = java.nio.file.Files.readString(file.toPath());
        assertTrue("Content should mention hints used", content.contains("1 hints"));
        assertTrue("Content should mention skips used", content.contains("0 skips"));
        assertTrue("Content should mention score", content.contains(String.valueOf(user.getScore())));
        assertTrue("Content should mention game name", content.contains(dummyGameName));
    }
}