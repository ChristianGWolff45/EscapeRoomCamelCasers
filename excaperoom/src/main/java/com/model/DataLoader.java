package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray peopleJSON = (JSONArray) jsonObject.get("users");

            for (int i = 0; i < peopleJSON.size(); i++) {
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                UUID id = UUID.fromString((String) personJSON.get(USER_ID));
                String username = (String) personJSON.get(USER_USER_NAME);
                String firstName = (String) personJSON.get(USER_FIRST_NAME);
                String lastName = (String) personJSON.get(USER_LAST_NAME);
                String password = (String) personJSON.get(USER_PASSWORD);

                JSONObject inventoryJSONObject = (JSONObject) personJSON.get(USER_INVENTORY);
                Inventory inventory = new Inventory();
                JSONArray cluesJSONArray = (JSONArray) inventoryJSONObject.get(USER_INVENTORY_CLUES);
                for(Object clue :  cluesJSONArray){
                    JSONObject clueJSONObject = (JSONObject) clue;
                    String clueID = (String) clueJSONObject.get(CLUE_ID);
                    inventory.addClue(clueID);
                }

                JSONArray hintsJSONArray = (JSONArray) inventoryJSONObject.get(USER_INVENTORY_HINTS);
                for(Object hint :  hintsJSONArray){
                    JSONObject hintJSONObject = (JSONObject) hint;
                    String hintID = (String) hintJSONObject.get(HINT_ID);
                    inventory.addHint(hintID);
                }

                JSONObject progressJSONObject = (JSONObject) personJSON.get(USER_PROGRESS);
                ArrayList<String> hints = new ArrayList<>();
                JSONArray hintsProgressJSONArray = (JSONArray) progressJSONObject.get(USER_PROGRESS_HINTS);
                for(Object hint : hintsProgressJSONArray){
                    JSONObject hintJSONObject = (JSONObject) hint;
                    hints.add((String) hintJSONObject.get(HINT_ID));
                }

                ArrayList<String> skips = new ArrayList<>();
                JSONArray skipJSONArray = (JSONArray) progressJSONObject.get(USER_PROGRESS_SKIPS);
                for(Object skip : skipJSONArray){
                    JSONObject skipJSONObject = (JSONObject) skip;
                    skips.add((String) skipJSONObject.get(PUZZLE_ID));
                }
                
                ArrayList<String> puzzles = new ArrayList<>();
                JSONArray puzzlesProgressJSONArray = (JSONArray) progressJSONObject.get(USER_PROGRESS_PUZZLES);
                for(Object puzzle : puzzlesProgressJSONArray){
                    JSONObject puzzleJSONObject = (JSONObject) puzzle;
                    hints.add((String) puzzleJSONObject.get(PUZZLE_ID));
                } 

                Progress progress = new Progress(skips, hints, puzzles);

                userList.add(new User(id, username, firstName, lastName, password, inventory, progress));
            }


            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static ArrayList<Game> getGameList() {
        ArrayList<Game> gameList = new ArrayList<>();

        try {
            ArrayList<User> users = getUserList();

            FileReader reader = new FileReader(GAME_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray gamesJSON = (JSONArray) jsonObject.get(GAME_LIST);

            for (Object gameObj : gamesJSON) {
                JSONObject gameJSON = (JSONObject) gameObj;

                UUID userId = UUID.fromString((String) gameJSON.get(GAME_USER));
                String startingRoomId = (String) gameJSON.get(GAME_STARTING_ROOM);

                User user = null;
                for (User u : users) {
                    if (u.getId().equals(userId)) {
                        user = u;
                        break;
                    }
                }

                if (user == null)
                    continue;

                JSONArray roomsJSON = (JSONArray) gameJSON.get("rooms");
                Map<String, Room> roomMap = new HashMap<>();
                HashMap<String, Puzzle> puzzleMap = new HashMap<>();
                HashMap<String, Hint> hintMap = new HashMap<>();
                HashMap<String, Clue> clueMap = new HashMap<>();
                ArrayList<Room> rooms = new ArrayList<>();

                // Parse rooms and puzzles
                for (Object roomObj : roomsJSON) {
                    JSONObject roomJSON = (JSONObject) roomObj;

                    String roomId = (String) roomJSON.get(ROOM_ID);
                    String roomName = (String) roomJSON.get(ROOM_NAME);
                    boolean unlocked = (boolean) roomJSON.get(ROOM_UNLOCKED);
                    boolean isExit = roomJSON.get("isExit") != null ? (boolean) roomJSON.get("isExit") : false;
                    String orientation = (String) roomJSON.get(ROOM_ORIENTATION);

                    JSONArray puzzlesJSON = (JSONArray) roomJSON.get(ROOM_PUZZLES);
                    ArrayList<Puzzle> puzzles = new ArrayList<>();

                    for (Object puzzleObj : puzzlesJSON) {
                        JSONObject puzzleJSON = (JSONObject) puzzleObj;
                        String puzzleId = (String) puzzleJSON.get(PUZZLE_ID);
                        String puzzleType = (String) puzzleJSON.get(PUZZLE_TYPE);
                        String answer = (String) puzzleJSON.get(PUZZLE_ANSWER);
                        boolean completed = (boolean) puzzleJSON.get(PUZZLE_COMPLETED);

                        JSONArray cluesJSON = (JSONArray) puzzleJSON.get(PUZZLE_CLUES);
                        ArrayList<Clue> clues = new ArrayList<>();
                        for (Object clueObj : cluesJSON) {
                            JSONObject clueJSON = (JSONObject) clueObj;
                            String clueId = (String) clueJSON.get(CLUE_ID);
                            String clueDescription = (String) clueJSON.get(CLUE_DESCRIPTION);
                            Clue clue = new Clue(clueId, clueDescription);
                            clues.add(clue);
                            clueMap.put(clue.getClueID(), clue);

                        }

                        JSONArray hintsJSON = (JSONArray) puzzleJSON.get(PUZZLE_HINTS);
                        ArrayList<Hint> hints = new ArrayList<>();
                        for (Object hintObj : hintsJSON) {
                            JSONObject hintJSON = (JSONObject) hintObj;
                            String hintTip = (String) hintJSON.get(HINT_TIP);
                            String hintId = (String) hintJSON.get(HINT_ID);
                            Hint hint = new Hint(hintTip, hintId);
                            hints.add(hint);
                            hintMap.put(hint.getHintID(), hint);
                        }

                        Puzzle puzzle = null;
                        switch (puzzleType.toLowerCase()) {
                            case "wordle":
                                puzzle = new Wordle(clues, hints, "CRANE", completed, puzzleId);
                                break;
                            // add other puzzles here
                        }
                        

                        if (completed) {
                            puzzle.puzzleSolved(); // mark solved as true
                        }
                        puzzles.add(puzzle);
                        puzzleMap.put(puzzle.getPuzzleID(), puzzle);
                    }

                    Room room = new Room(roomId, roomName, puzzles, new ArrayList<>(), unlocked, isExit);
                    roomMap.put(roomId, room);
                }

                for (Object roomObj : roomsJSON) {
                    JSONObject roomJSON = (JSONObject) roomObj;
                    String roomId = (String) roomJSON.get(ROOM_ID);
                    Room currentRoom = roomMap.get(roomId);

                    JSONArray nextRoomsJSON = (JSONArray) roomJSON.get(ROOM_NEXT_ROOMS);
                    if (nextRoomsJSON != null) {
                        for (Object nextRoomId : nextRoomsJSON) {
                            Room nextRoom = roomMap.get((String) nextRoomId);
                            if (nextRoom != null) {
                                currentRoom.addNextRoom(nextRoom);
                            }
                        }
                    }
                }

                Room startingRoom = roomMap.get(startingRoomId);
                if (startingRoom != null) {
                    Game game = new Game(user, startingRoom, hintMap, puzzleMap, clueMap);
                    gameList.add(game);
                }

            }

            return gameList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameList;
    }
}
