package com.model;

public abstract class DataConstants {
    //User Data
    protected static final String USER_FILE_NAME = "json/UserList.json";
    protected static final String USER_TEMP_FILE_NAME = "json/users_temp.json";
    protected static final String USER_TEMP_CERTIFICATE = "excaperoom/src/main/java/com/model/certifacates/Certificate1.txt";
    protected static final String USER_ID = "id";
    protected static final String USER_LIST = "UserList";
    protected static final String USER_USERS = "users";
    protected static final String USER_USER_NAME = "username";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_INVENTORY = "inventory";
    protected static final String USER_INVENTORY_CLUES = "clues";
    protected static final String USER_INVENTORY_HINTS = "hints";
    protected static final String USER_PROGRESS = "progress";
    protected static final String USER_PROGRESS_SKIPS = "skips";
    protected static final String USER_PROGRESS_HINTS = "hints";
    protected static final String USER_PROGRESS_PUZZLES = "puzzles";
    protected static final String USER_CERTIFICATE = "certificate";
    protected static final String USER_CERTIFICATE_TIME_TAKEN = "timeTaken";
    protected static final String USER_CERTIFICATE_HINTS_USED = "hintsUsed";
    protected static final String USER_CERTIFICATE_SKIPS_USED = "skipsUsed";

    protected static final String USER_SCORE = "Score";


    //Game Data
    protected static final String GAME_FILE_NAME = "json/GameList.json";
    protected static final String GAME_LIST = "games";
    protected static final String GAME_USER = "user";
    protected static final String GAME_STARTING_ROOM = "startingRoom";
    protected static final String GAME_MAIN_MENU = "mainMenu";
    protected static final String GAME_TIME_LEFT = "timeLeft";
    protected static final String GAME_CERTIFICATE = "certificate";
    protected static final String GAME_CERT_TIME_TAKEN = "timeTaken";
    protected static final String GAME_CERT_HINTS_USED = "hintsUsed";
    protected static final String GAME_CERT_CLUES_USED = "cluesUsed";



    //Room Data
    protected static final String GAME_NAME = "name";
    protected static final String GAME_STORY = "story";
    protected static final String ROOM_ID = "id";
    protected static final String ROOM_NAME = "roomName";
    protected static final String ROOM_PUZZLES = "puzzles";
    protected static final String ROOM_NEXT_ROOMS = "nextRooms";
    protected static final String ROOM_UNLOCKED = "unlocked";
    protected static final String ROOM_ORIENTATION = "orientation";
    protected static final String ROOM_IS_EXIT = "isExit";

    //Puzzle Data
    protected static final String PUZZLE_ID = "id";
    protected static final String PUZZLE_TYPE = "type";
    protected static final String PUZZLE_CLUES = "clues";
    protected static final String PUZZLE_HINTS = "hints";
    protected static final String PUZZLE_ANSWER = "answer";
    protected static final String PUZZLE_COMPLETED = "completed";

    //Clue Data
    protected static final String CLUE_ID = "id";
    protected static final String CLUE_DESCRIPTION = "description";

    //Hint Data
    protected static final String HINT_ID = "id";
    protected static final String HINT_TIP = "tip";

    //MultipleChoice 
    protected static final String PUZZLE_CHOICES = "choices";

    //SaveCertificate
    protected static final String GAME = "game";

}
