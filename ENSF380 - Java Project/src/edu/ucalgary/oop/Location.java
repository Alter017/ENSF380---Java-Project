/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.sql.ResultSet;
import java.util.Scanner;

public class Location {
	private int locationId;
    private static final Scanner scanner = new Scanner(System.in);
    private DatabaseManager db = new DatabaseManager();
	Location() {}
	
    /**
     * @param person ID
     * @param selected language
     */
	public void selectLocation(int personId, int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        db.createConnection();
        
		locationId = -1;
		while (locationId < 0) {
            System.out.print(langManager.getTranslation("loc_actual_victim_location"));

		    try {
		        int temp = Integer.parseInt(scanner.nextLine());
		        if (locationExists(temp)) {
		        	locationId = temp;
		        } else {
		            System.out.println(langManager.getTranslation("loc_actual_victim_location_fail"));
		        }
		    } catch (NumberFormatException e) {
		        System.out.println(langManager.getTranslation("loc_invalid_num"));
		    }
		}
		
        // SAVE TO DATABASE
        db.insertPersonLocation(personId, locationId);
        System.out.println(langManager.getTranslation("loc_database_success"));
        db.closeConnection();
	}
	
    /**
     * @param location ID
     * @return whether location exists or not
     */
    public boolean locationExists(int locationId) {
    	
        db.createConnection();
    	
        try (ResultSet rs = db.getLocationById(locationId)) {
            return rs != null && rs.next(); // location exists if we get a result
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
}