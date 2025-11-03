package com.model;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProgressTest {
    @Test
    public void nullConstructor(){
        ArrayList<String> emptyArrayList = new ArrayList<>();
        Progress progress = new Progress();

        assertEquals("getskips should return an empty arrayList", progress.getSkips(), emptyArrayList);
        assertEquals("getPuzzles should return an empty arrayList", progress.getPuzzles(), emptyArrayList);
        assertEquals("getHints should return an empty arrayList", progress.getHints(), emptyArrayList);
    }

    @Test
    public void ProgressConstructor_normal(){
        ArrayList<String> puzzleIDs = new ArrayList<>();
        puzzleIDs.add("puzzle1");
        puzzleIDs.add("puzzle2");
        Progress progress = new Progress(puzzleIDs, puzzleIDs, puzzleIDs);

        assertEquals("getskips should return the same arrayList we set it to", progress.getSkips(), puzzleIDs);
        assertEquals("getPuzzles should return the same arrayList we set it to", progress.getPuzzles(), puzzleIDs);
        assertEquals("getHints should return the same arrayList we set it to", progress.getHints(), puzzleIDs);
    }

    @Test
    public void ProgressConstructor_null(){
        ArrayList<String> emptyArrayList = new ArrayList<>();
        Progress progress = new Progress(null, null, null);

        assertEquals("getskips should return an empty arrayList", progress.getSkips(), emptyArrayList);
        assertEquals("getPuzzles should return an empty arrayList", progress.getPuzzles(), emptyArrayList);
        assertEquals("getHints should return an empty arrayList", progress.getHints(), emptyArrayList);
    }

    @Test
    public void addHint_normal(){
        ArrayList<String> hintArrayList = new ArrayList<>();
        hintArrayList.add("hintID1");
        hintArrayList.add("hintID2");

        Progress progress = new Progress();
        progress.addHint("hintID1");
        progress.addHint("hintID2");
        assertEquals("getHints should contain the same hintIDs as hintArrayList", progress.getHints(), hintArrayList);
    }

    @Test
    public void addHint_null(){
        ArrayList<String> hintArrayList = new ArrayList<>();
        hintArrayList.add("hintID2");

        Progress progress = new Progress();
        progress.addHint(null);
        progress.addHint("hintID2");
        assertEquals("getHints should contain the same hintIDs as hintArrayList", progress.getHints(), hintArrayList);
    }

    @Test
    public void addSkip_normal(){
        ArrayList<String> skipArrayList = new ArrayList<>();
        skipArrayList.add("puzzleSkipID1");
        skipArrayList.add("puzzleSkipID2");

        Progress progress = new Progress();
        progress.addSkip("puzzleSkipID1");
        progress.addSkip("puzzleSkipID2");
        assertEquals("getSkips should contain the same puzzleIDs as skipArrayList", progress.getSkips(), skipArrayList);
    }

    @Test
    public void addSkip_null(){
        ArrayList<String> skipArrayList = new ArrayList<>();
        skipArrayList.add("puzzleSkipID2");

        Progress progress = new Progress();
        progress.addSkip(null);
        progress.addSkip("puzzleSkipID2");
        assertEquals("getSkips should contain the same puzzleIDs as skipArrayList", progress.getSkips(), skipArrayList);
    }

    @Test
    public void addPuzzle_normal(){
        ArrayList<String> puzzleArrayList = new ArrayList<>();
        puzzleArrayList.add("puzzleID1");
        puzzleArrayList.add("puzzleID2");

        Progress progress = new Progress();
        progress.completePuzzle("puzzleID1");
        progress.completePuzzle("puzzleID2");
        assertEquals("getPuzzles should contain the same puzzleIDs as puzzleArrayList", progress.getPuzzles(), puzzleArrayList);
    }

    @Test
    public void addPuzzle_null(){
        ArrayList<String> puzzleArrayList = new ArrayList<>();
        puzzleArrayList.add("puzzleID2");

        Progress progress = new Progress();
        progress.completePuzzle(null);
        progress.completePuzzle("puzzleID2");
        assertEquals("getPuzzles should contain the same puzzleIDs as puzzleArrayList", progress.getPuzzles(), puzzleArrayList);
    }







} 
