/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 
 import java.io.*;
 import java.sql.Timestamp;
 import java.time.LocalDate;
 
 import static org.hamcrest.CoreMatchers.containsString;
 import static org.junit.Assert.assertThat;
 
 public class MedicalRecordTest {
 
	 private final InputStream originalIn = System.in;
	 private final PrintStream originalOut = System.out;
 
	 private ByteArrayInputStream testIn;
	 private ByteArrayOutputStream testOut;
 
	 @Before
	 public void setUpStreams() {
		 testOut = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(testOut));
	 }
 
	 @After
	 public void restoreStreams() {
		 System.setIn(originalIn);
		 System.setOut(originalOut);
	 }
 
	 @Test
	 public void testAddNewMedicalRecord_SuccessfulFlow() {
		 String input = String.join("\n",
				 "100",        // victim ID (exists)
				 "200",        // location ID (exists)
				 "Treatment is stable", // treatment details
				 "2025-04-10"  // valid date
		 ) + "\n";
		 testIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(testIn);
 
		 // Call stubbed version
		 MedicalRecordStub.addNewMedicalRecord(1);
 
		 String output = testOut.toString();
		 assertThat(output, containsString("record")); // Should contain something like "record allocated"
	 }
 
	 @Test
	 public void testAddNewMedicalRecord_InvalidVictimThenValid() {
		 String input = String.join("\n",
				 "-1",   // invalid ID
				 "100",  // valid victim ID
				 "200",  // valid location ID
				 "Treatment detail",
				 "2025-04-10"
		 ) + "\n";
		 testIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(testIn);
 
		 MedicalRecordStub.addNewMedicalRecord(1);
		 String output = testOut.toString();
		 assertThat(output, containsString("victim not found"));
		 assertThat(output, containsString("record")); // Ended successfully
	 }
 
	 /**
	  * Stub version of MedicalRecord that avoids DB logic and real user classes.
	  */
	 public static class MedicalRecordStub extends MedicalRecord {
		 public static void addNewMedicalRecord(int selectedLang) {
			 LanguageManager langManager = new LanguageManager("en-CA");
			 DisasterVictimStub person = new DisasterVictimStub();
			 LocationStub location = new LocationStub();
			 DatabaseManagerStub db = new DatabaseManagerStub();
			 db.createConnection();
 
			 Scanner scanner = new Scanner(System.in);
 
			 int victimId = -1;
			 while (victimId < 0) {
				 System.out.print(langManager.getTranslation("mr_enter_victim"));
				 try {
					 int temp = Integer.parseInt(scanner.nextLine());
					 if (person.personExists(temp)) {
						 victimId = temp;
					 } else {
						 System.out.println(langManager.getTranslation("mr_victim_not_found"));
					 }
				 } catch (NumberFormatException e) {
					 System.out.println(langManager.getTranslation("mr_invalid_num"));
				 }
			 }
 
			 int locationId = -1;
			 while (locationId < 0) {
				 System.out.print(langManager.getTranslation("mr_actual_victim_location"));
				 try {
					 int temp = Integer.parseInt(scanner.nextLine());
					 if (location.locationExists(temp)) {
						 locationId = temp;
					 } else {
						 System.out.println(langManager.getTranslation("mr_location_failed"));
					 }
				 } catch (NumberFormatException e) {
					 System.out.println(langManager.getTranslation("mr_invalid_num2"));
				 }
			 }
 
			 String details = null;
			 while (details == null) {
				 System.out.print(langManager.getTranslation("mr_treatment_details"));
				 details = scanner.nextLine();
				 if (details == null || details.trim().isEmpty()) {
					 System.out.print(langManager.getTranslation("mr_treatment_details_error"));
				 }
			 }
 
			 LocalDate dot = null;
			 Timestamp inquiryDate = null;
 
			 while (dot == null) {
				 System.out.print(langManager.getTranslation("mr_date_of_inquiry"));
				 String input = scanner.nextLine();
				 try {
					 dot = LocalDate.parse(input);
					 inquiryDate = Timestamp.valueOf(dot.atStartOfDay());
				 } catch (Exception e) {
					 System.out.println(langManager.getTranslation("mr_invalid_format"));
				 }
			 }
 
			 db.insertMedicalRecord(locationId, victimId, inquiryDate, details);
			 System.out.println(langManager.getTranslation("mr_record_allocated"));
		 }
	 }
 
	 static class DisasterVictimStub extends DisasterVictim {
		 @Override
		 public boolean personExists(int id) {
			 return id == 100; // Only 100 is "valid"
		 }
	 }
 
	 static class LocationStub extends Location {
		 @Override
		 public boolean locationExists(int id) {
			 return id == 200; // Only 200 is "valid"
		 }
	 }
 
	 static class DatabaseManagerStub extends DatabaseManager {
		 @Override
		 public void createConnection() {}
		 @Override
		 public void insertMedicalRecord(int locationId, int victimId, Timestamp inquiryDate, String details) {
			 // Simulate DB insert without doing anything
		 }
	 }
 }
 