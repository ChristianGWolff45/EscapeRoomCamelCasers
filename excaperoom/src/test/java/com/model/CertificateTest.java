package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

public class CertificateTest {
    private Certificate certificate;

    @Before
    public void setUp() {
        certificate = new Certificate();
    }

    @Test
    public void testConstructor_default_initializesWithZeros() {
        assertEquals("Hints used should be 0", 0, certificate.getHintsUsed());
        assertEquals("Skips used should be 0", 0, certificate.getSkipsUsed());
        assertEquals("Time taken should be ZERO", Duration.ZERO, certificate.getTimeTaken());
    }

    @Test
    public void testConstructor_withParameters_initializesCorrectly() {
        Certificate cert = new Certificate(120000, 2, 1);
        
        assertEquals("Hints used should be 2", 2, cert.getHintsUsed());
        assertEquals("Skips used should be 1", 1, cert.getSkipsUsed());
        assertEquals("Time taken should be 120000 milliseconds", Duration.ofMillis(120000), cert.getTimeTaken());
    }

    @Test
    public void testGetTimeTaken_returnsCorrectDuration() {
        certificate.setTimeTaken(Duration.ofSeconds(150));
        
        assertEquals("Time taken should match", Duration.ofSeconds(150), certificate.getTimeTaken());
    }

    @Test
    public void testGetTimeTakenInt_convertsToMilliseconds() {
        certificate.setTimeTaken(Duration.ofSeconds(120));
        
        int timeTakenInt = certificate.getTimeTakenInt();
        assertEquals("Time taken in milliseconds should be 120000", 120000, timeTakenInt);
    }

    @Test
    public void testSetTimeTaken_updatesTimeTaken() {
        Duration newTime = Duration.ofSeconds(300);
        certificate.setTimeTaken(newTime);
        
        assertEquals("Time taken should be updated", newTime, certificate.getTimeTaken());
    }

    @Test
    public void testAddTime_addsToExistingTime() {
        certificate.setTimeTaken(Duration.ofSeconds(60));
        certificate.addTime(40000);
        
        Duration expected = Duration.ofMillis(100000);
        assertEquals("Time should be added correctly", expected, certificate.getTimeTaken());
    }

    @Test
    public void testUseHint_incrementsHintsUsed() {
        assertEquals("Initial hints used should be 0", 0, certificate.getHintsUsed());
        
        certificate.useHint();
        assertEquals("Hints used should be 1", 1, certificate.getHintsUsed());
        
        certificate.useHint();
        assertEquals("Hints used should be 2", 2, certificate.getHintsUsed());
    }

    @Test
    public void testUseSkip_incrementsSkipsUsed() {
        assertEquals("Initial skips used should be 0", 0, certificate.getSkipsUsed());
        
        certificate.useSkip();
        assertEquals("Skips used should be 1", 1, certificate.getSkipsUsed());
        
        certificate.useSkip();
        assertEquals("Skips used should be 2", 2, certificate.getSkipsUsed());
    }

    @Test
    public void testTimeTakenMMSS_formatsTimeCorrectly() {
        certificate.setTimeTaken(Duration.ofSeconds(125));
        
        String formatted = certificate.timeTakenMMSS();
        assertEquals("Time should be formatted as MM:SS", "02:05", formatted);
    }

    @Test
    public void testTimeTakenMMSS_formatsZeroTime() {
        certificate.setTimeTaken(Duration.ZERO);
        
        String formatted = certificate.timeTakenMMSS();
        assertEquals("Zero time should be formatted as 00:00", "00:00", formatted);
    }

    @Test
    public void testTimeTakenMMSS_formatsLargeTime() {
        certificate.setTimeTaken(Duration.ofSeconds(1265));
        
        String formatted = certificate.timeTakenMMSS();
        assertEquals("Time should be formatted correctly", "21:05", formatted);
    }

    @Test
    public void testTimeTakenMMSS_formatsSingleDigitSeconds() {
        certificate.setTimeTaken(Duration.ofSeconds(65));
        
        String formatted = certificate.timeTakenMMSS();
        assertEquals("Single digit seconds should be zero-padded", "01:05", formatted);
    }

    @Test
    public void testToString_displaysAllInformation() {
        certificate.setTimeTaken(Duration.ofSeconds(125));
        certificate.useHint();
        certificate.useHint();
        certificate.useSkip();
        
        String result = certificate.toString();
        
        assertTrue("toString should contain skips used", result.contains("Skips Used: 1"));
        assertTrue("toString should contain hints used", result.contains("Hints Used: 2"));
        assertTrue("toString should contain time taken", result.contains("Time Taken: 02:05"));
    }

    @Test
    public void testMultipleOperations_allValuesUpdateCorrectly() {
        certificate.setTimeTaken(Duration.ofSeconds(30));
        certificate.useHint();
        certificate.useHint();
        certificate.useHint();
        certificate.useSkip();
        
        assertEquals("Hints used should be 3", 3, certificate.getHintsUsed());
        assertEquals("Skips used should be 1", 1, certificate.getSkipsUsed());
        assertEquals("Time taken should be 30 seconds", Duration.ofSeconds(30), certificate.getTimeTaken());
    }
}