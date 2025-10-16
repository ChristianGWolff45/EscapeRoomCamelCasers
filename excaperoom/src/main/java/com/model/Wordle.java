package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Wordle extends Puzzle{
    private HashMap<Character, Set<Integer>> letterIndex = new HashMap<>();
    private String puzzleId;

    public Wordle(String puzzleId, ArrayList<Clue> clues, ArrayList<Hint> hints, String answer, boolean completed) {
        super(clues, hints, answer, completed);
        this.puzzleId = puzzleId;
        answer = answer.toUpperCase();
        for(int i = 0; i < answer.length(); i++){
            char letter = answer.charAt(i);
            letterIndex.putIfAbsent(letter, new HashSet<>());
            letterIndex.get(letter).add(i);
        }
    }
    /**
     * 
     * @param guess string guess from user
     * @return a 2D array of size [n][2]
     * first position letter second position how correct
     * red = wrong letter
     * yellow = right letter wrong place
     * green = right letter right place
     */
    public String[][] guessAnswer(String guess){
        guess = guess.toUpperCase();
        String[][] output = new String[guess.length()][2];
        HashMap<Character, Set<Integer>> copyLetterIndex = deepCopyMap(letterIndex);
        for(int i = 0; i < guess.length(); i++){
            char letter = guess.charAt(i);
            if(!copyLetterIndex.containsKey(letter)){
                output[i][0] = String.valueOf(letter);
                output[i][1] ="red";
            }else if(copyLetterIndex.get(letter).contains(i)){
                output[i][0] = String.valueOf(letter);
                output[i][1] = "green";
                copyLetterIndex.get(letter).remove(i);
            }
        }
        for(int i = 0; i < guess.length(); i++){
            char letter = guess.charAt(i);
            if(output[i][0] == null){
                if(!copyLetterIndex.containsKey(letter)){
                    output[i][0] = String.valueOf(letter);
                    output[i][1] ="red";
                }else{
                    Iterator<Integer> it = copyLetterIndex.get(letter).iterator();
                    it.next();
                    output[i][0] = String.valueOf(letter);
                    output[i][1] = "yellow";
                    it.remove();
                    if(copyLetterIndex.get(letter).isEmpty()) copyLetterIndex.remove(letter);
                }
            }
        }
        return output;
    }

     
    public static HashMap<Character, Set<Integer>> deepCopyMap(HashMap<Character, Set<Integer>> original) {
        HashMap<Character, Set<Integer>> copy = new HashMap<>();
        for (Map.Entry<Character, Set<Integer>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        return copy;
    }

    /**
     * Used for returning the PuzzleId for access
     * @return
     */
    public String getPuzzleId(){
        return puzzleId;
    }
    
}