/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ReliefServiceTest {

    private ReliefService service;
    private Inquirer dummyInquirer;
    private DisasterVictim dummyVictim;
    private Location dummyLocation;

    @Before
    public void setUp() {
        service = new ReliefService();
        dummyInquirer = new Inquirer(); // Assuming default constructors exist
        dummyVictim = new DisasterVictim();
        dummyLocation = new Location();
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(service.getInquirer());
        assertNull(service.getMissingPerson());
        assertNull(service.getDateOfInquiry());
        assertNull(service.getInfoProvided());
        assertNull(service.getLastKnownLocation());
    }

    @Test
    public void testFullConstructor() {
        ReliefService fullService = new ReliefService(
                dummyInquirer, dummyVictim, "2025-04-10", "Found in sector A", dummyLocation
        );

        assertEquals(dummyInquirer, fullService.getInquirer());
        assertEquals(dummyVictim, fullService.getMissingPerson());
        assertEquals("2025-04-10", fullService.getDateOfInquiry());
        assertEquals("Found in sector A", fullService.getInfoProvided());
        assertEquals(dummyLocation, fullService.getLastKnownLocation());
    }

    @Test
    public void testSettersAndGetters() {
        service.setInquirer(dummyInquirer);
        service.setMissingPerson(dummyVictim);
        service.setInfoProvided("Some info");
        service.setLastKnownLocation(dummyLocation);
        service.setDateOfInquiry("2025-04-11");

        assertEquals(dummyInquirer, service.getInquirer());
        assertEquals(dummyVictim, service.getMissingPerson());
        assertEquals("Some info", service.getInfoProvided());
        assertEquals(dummyLocation, service.getLastKnownLocation());
        assertEquals("2025-04-11", service.getDateOfInquiry());
    }

    @Test
    public void testSetDateOfInquiry_Valid() {
        service.setDateOfInquiry("2025-12-25");
        assertEquals("2025-12-25", service.getDateOfInquiry());
    }

    @Test
    public void testSetDateOfInquiry_Invalid() {
        // Capture System.out
        String invalidDate = "invalid-date";
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        service.setDateOfInquiry(invalidDate);

        // Restore System.out
        System.setOut(originalOut);

        assertNull(service.getDateOfInquiry());
        assertThat(output.toString(), containsString("Invalid date format"));
    }

    @Test
    public void testLogDetails_ReturnsFormattedMessage() {
        // Stub the LanguageManager to return dummy translations
        LanguageManagerStub.install();

        String log = service.logDetails(1, 2, 3, "InfoXYZ", 1);

        assertThat(log, containsString("INQUIRY LOG"));
        assertThat(log, containsString("Inquirer ID: 1"));
        assertThat(log, containsString("Missing Person ID: 2"));
        assertThat(log, containsString("Information Provided: InfoXYZ"));
        assertThat(log, containsString("Current Location ID: 3"));
        assertThat(log, containsString("Thank you"));

        LanguageManagerStub.uninstall();
    }

    // === Stub LanguageManager to avoid needing real translations ===
    public static class LanguageManagerStub extends LanguageManager {
        public LanguageManagerStub(String langCode) { super(langCode); }

        public static void install() {
            LanguageManager.setLanguageProvider(new LanguageManagerStub("en-CA"));
        }

        public static void uninstall() {
            LanguageManager.setLanguageProvider(null); // Restore to default if needed
        }

        @Override
        public String getTranslation(String key) {
            switch (key) {
                case "rs1": return "INQUIRY LOG";
                case "rs2": return "Inquirer ID: ";
                case "rs3": return "Missing Person ID: ";
                case "rs4": return "Information Provided: ";
                case "rs5": return "Current Location ID: ";
                case "rs6": return "Thank you";
                default: return "[missing translation]";
            }
        }

        public static LanguageManager whatLanguage(int lang) {
            return new LanguageManagerStub("en-CA");
        }
    }
}
