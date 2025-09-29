/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Supply {
    private static final Scanner scanner = new Scanner(System.in);
    private Location location = new Location();
    private DisasterVictim person = new DisasterVictim();
    private DatabaseManager db = new DatabaseManager();
    private Pattern pattern = Pattern.compile("^(\\d{3})\\s([A-Za-z])(\\d{2})$");
    private String personComments;
    private String locationComments;
	private String comments = null;
    private int victimId;
    private int locationId;
    private int supplyId;
    private int supplyId2;
    
    public Supply() {}
    
    /**
     * @return victim ID
     */
    public int getVicitimId() {
        return victimId;
    }

    /**
     * @param victim ID
     */
    public void setVicitimId(int victimId) {
        this.victimId = victimId;
    }
    
    /**
     * @return location ID
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param location ID
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    /**
     * @return supply ID to victim
     */
    public int getSupplyId() {
        return supplyId;
    }

    /**
     * @param supply ID to victim
     */
    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }
    
    /**
     * @return supply ID to location
     */
    public int getSupplyId2() {
        return supplyId2;
    }

    /**
     * @param supply ID to location
     */
    public void setSupplyId2(int supplyId2) {
        this.supplyId2 = supplyId2;
    }
    
    /**
     * @param supply type
     * @param selected language
     */
    public void allocateSupply(String type, int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
    	
        System.out.println(langManager.getTranslation("supply_allocate_to"));
        
        String option = scanner.nextLine();

        db.createConnection();

        switch (option) {
            case "1":
        		victimId = -1;
        		while (victimId < 0) {
                    System.out.print(langManager.getTranslation("supply_victim_id"));
        		    try {
        		        int temp = Integer.parseInt(scanner.nextLine());
        		        if (person.personExists(temp)) {
        		        	victimId = temp;
        		        } else {
        		            System.out.println(langManager.getTranslation("supply_victim_id_failed"));
        		        }
        		    } catch (NumberFormatException e) {
        		        System.out.println(langManager.getTranslation("supply_invalid_num"));
        		    }
        		}
        		
        		personComments = comments(type, selectedLang);
        		
                // SAVE TO DATABASE
                supplyId = db.insertSupply(type, personComments);
                System.out.println(langManager.getTranslation("supply_inserted1"));
        		
                db.allocateSupplyToPerson(supplyId, victimId);
                System.out.println(langManager.getTranslation("supply_allocated1"));

                db.closeConnection();
        		
                break;
                
            case "2":
        		locationId = -1;
        		while (locationId < 0) {
                    System.out.print(langManager.getTranslation("supply_location_allocation"));

        		    try {
        		        int temp = Integer.parseInt(scanner.nextLine());
        		        if (location.locationExists(temp)) {
        		        	locationId = temp;
        		        } else {
        		            System.out.println(langManager.getTranslation("supply_location_failed"));
        		        }
        		    } catch (NumberFormatException e) {
        		        System.out.println(langManager.getTranslation("supply_invalid_num2"));
        		    }
        		}
        		
        		locationComments = comments(type, selectedLang);
        		
                // SAVE TO DATABASE
                supplyId2 = db.insertSupply(type, locationComments);
                System.out.println(langManager.getTranslation("supply_inserted2"));
        		
                db.allocateSupplyToLocation(supplyId2, locationId);
                System.out.println(langManager.getTranslation("supply_allocated2"));

                db.closeConnection();
        		
                break;
        }

        db.closeConnection();
    }
    
    /**
     * @param supply type
     * @param selected language
     * @return comments, details, or room and grid
     */
    public String comments(String type, int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
		
		if (type == "Cot") {
            while (true) {
                System.out.print(langManager.getTranslation("supply_room_grid"));
                comments = scanner.nextLine();
                Matcher matcher = pattern.matcher(comments);

                if (matcher.matches()) {
                    break;
                } else {
                    System.out.println(langManager.getTranslation("supply_invalid_format"));
                }
            }
		}
		else if (type == "Individual Item") {
            System.out.print(langManager.getTranslation("supply_brief_description"));
            comments = scanner.nextLine();
		}
		else {
            System.out.print(langManager.getTranslation("supply_some_comments"));
            comments = scanner.nextLine();
		}
		return comments;
    }
}
