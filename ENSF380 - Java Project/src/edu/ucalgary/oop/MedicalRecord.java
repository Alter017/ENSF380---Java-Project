/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MedicalRecord {
    private static final Scanner scanner = new Scanner(System.in);
	MedicalRecord() {}
	
    /**
     * @param selected language
     */
    public static void addNewMedicalRecord(int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
    	DisasterVictim person = new DisasterVictim();
    	Location location = new Location();
        DatabaseManager db = new DatabaseManager();
        db.createConnection();
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
        	if (details == null) {
    			System.out.print(langManager.getTranslation("mr_treatment_details_error"));
        	}
		}
		
        Scanner scanner = new Scanner(System.in);
        LocalDate dot = null;
        Timestamp inquiryDate = null;

        while (dot == null) {
            System.out.print(langManager.getTranslation("mr_date_of_inquiry"));
            String input = scanner.nextLine(); // Read as String
            try {
            	dot = LocalDate.parse(input); // Try to parse as LocalDate
                inquiryDate = Timestamp.valueOf(dot.atStartOfDay());
            } catch (DateTimeParseException e) {
                System.out.println(langManager.getTranslation("mr_invalid_format"));
            }
        }
        
        // SAVE TO DATABASE
        db.insertMedicalRecord(locationId, victimId, inquiryDate, details);
        System.out.println(langManager.getTranslation("mr_record_allocated"));
        
        scanner.close();
    }
}