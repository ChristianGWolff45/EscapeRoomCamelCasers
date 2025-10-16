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
                    if (u.getId().equals(userId)){
                        user = u;
                        break;
                    }
                }

                if (user == null) continue;

                JSONArray roomsJSON = (JSONArray) gameJSON.get("rooms");
                Map<String, Room> roomMap = new HashMap();

                //Parse rooms and puzzles
                for (Object roomObj : roomsJSON) {
                    JSONObject roomJSON = (JSONObject) roomObj;

                    String roomId = (String) roomJSON.get(ROOM_ID);
                    String roomName = (String) roomJSON.get(ROOM_NAME);
                    boolean unlocked = (boolean) roomJSON.get(ROOM_UNLOCKED);
                    
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
                            clues.add(new Clue(clueId, clueDescription));
                        }

                        JSONArray hintsJSON = (JSONArray) puzzleJSON.get(PUZZLE_HINTS);
                        ArrayList<Hint> hints = new ArrayList<>();
                        for (Object hintObj : hintsJSON) {
                            JSONObject hintJSON = (JSONObject) hintObj;
                            String hintTip = (String) hintJSON.get(HINT_TIP);
                            String hintId = (String) hintJSON.get(HINT_ID);
                            hints.add(new Hint(hintTip, hintId));
                        }

                        Puzzle puzzle = null;
                        switch (puzzleType.toLowerCase()) {
                            case "wordle":
                                puzzle = new Wordle(puzzleId, clues, hints, "CRANE", completed);
                                break;
                            //add other puzzles here
                        }

                       if (completed) {
                        puzzle.puzzleSolved();
                       }
                       puzzles.add(puzzle);
                    }
                }

                
            }
        } 
    }
}
