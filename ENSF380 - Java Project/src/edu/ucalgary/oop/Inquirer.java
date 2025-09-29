/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Inquirer {
    private static final Scanner scanner = new Scanner(System.in);
    private DisasterVictim person = new DisasterVictim();
    private DatabaseManager db = new DatabaseManager();
    private ReliefService rlfsrvc = new ReliefService();
    private LocalDate doi = null;
    private Timestamp inquiryDate = null;
    private int inquirerId;
    private int missingPersonId;
    private int loc;
    private int victimLocation;
    private String inquiryComments;
    
    public Inquirer() {}
    
    /**
     * @return inquiry date
     */
    public Timestamp getInquiryDate() {
        return inquiryDate;
    }

    /**
     * @param inquiry date
     */
    public void setInquiryDate(Timestamp inquiryDate) {
        this.inquiryDate = inquiryDate;
    }
    
    /**
     * @return inquirer ID
     */
    public int getInquirerId() {
        return inquirerId;
    }

    /**
     * @param inquirer ID
     */
    public void setInquirerId(int inquirerId) {
        this.inquirerId = inquirerId;
    }
    
    /**
     * @return missing person ID
     */
    public int getMissingPersonId() {
        return missingPersonId;
    }

    /**
     * @param missing person ID
     */
    public void setMissingPersonId(int missingPersonId) {
        this.missingPersonId = missingPersonId;
    }
    
    /**
     * @return victim location
     */
    public int getVictimLocation() {
        return victimLocation;
    }

    /**
     * @param victim location
     */
    public void setVictimLocation(int victimLocation) {
        this.victimLocation = victimLocation;
    }
    
    /**
     * @param location ID
     * @return whether location exists or not
     */
    private boolean locationExists(int locationId) {

        db.createConnection();
    	
        try (ResultSet rs = db.getLocationById(locationId)) {
            return rs != null && rs.next(); // location exists if we get a result
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param selected language
     */
	public void makeAnInquiry(int selectedLang) {
		LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
		
        db.createConnection();
		
		inquirerId = -1;
		while (inquirerId < 0) {
		    System.out.print(langManager.getTranslation("inq_person_id"));
		    try {
		        int temp = Integer.parseInt(scanner.nextLine());
		        if (person.personExists(temp)) {
		            inquirerId = temp;
		        } else {
		            System.out.println(langManager.getTranslation("inq_person_id_fail"));
		        }
		    } catch (NumberFormatException e) {
		        System.out.println(langManager.getTranslation("inq_invalid_num"));
		    }
		}

		missingPersonId = -1;
		while (missingPersonId < 0) {
		    System.out.print(langManager.getTranslation("inq_missing_person_id"));
		    try {
		        int temp = Integer.parseInt(scanner.nextLine());
		        if (person.personExists(temp)) {
		            missingPersonId = temp;
		        } else {
		            System.out.println(langManager.getTranslation("inq_missing_person_id_fail"));
		        }
		    } catch (NumberFormatException e) {
		        System.out.println(langManager.getTranslation("inq_invalid_num2"));
		    }
		}

        loc = -1;
        victimLocation = -1;
        while (loc < 0) {
            System.out.print(langManager.getTranslation("inq_current_location"));
            
    		try {
    		    int temp = Integer.parseInt(scanner.nextLine());
    		    if (locationExists(temp)) {
    		       victimLocation = temp;
    		       loc = 1;
    		    } else {
    		        System.out.println(langManager.getTranslation("inq_current_location_fail"));
    		    }
    		} catch (NumberFormatException e) {
    		    System.out.println(langManager.getTranslation("inq_invalid_num3"));
    		}
    	}

        Scanner scanner = new Scanner(System.in);

        while (doi == null) {
            System.out.print(langManager.getTranslation("inq_inquiry_date"));
            String input = scanner.nextLine(); // Read as String
            try {
                doi = LocalDate.parse(input); // Try to parse as LocalDate
                inquiryDate = Timestamp.valueOf(doi.atStartOfDay());
            } catch (DateTimeParseException e) {
                System.out.println(langManager.getTranslation("inq_invalid_date"));
            }
        }
		System.out.print(langManager.getTranslation("inq_comments"));
		inquiryComments = scanner.nextLine();
		
        System.out.println("\n" + langManager.getTranslation("inq_summary1"));
        System.out.println(langManager.getTranslation("inq_summary2") + inquirerId);
        System.out.println(langManager.getTranslation("inq_summary3") + missingPersonId);
        System.out.println(langManager.getTranslation("inq_summary4") + victimLocation);
        System.out.println(langManager.getTranslation("inq_summary5") + inquiryDate);
        System.out.println(langManager.getTranslation("inq_summary6") + inquiryComments);
        System.out.println(langManager.getTranslation("inq_summary7") + "\n");

        // SAVE TO DATABASE
        db.logInquiry(inquirerId, missingPersonId, victimLocation, inquiryDate, inquiryComments);
        System.out.println(langManager.getTranslation("inq_success_log"));

        db.closeConnection();
        
        System.out.println(rlfsrvc.logDetails(inquirerId, missingPersonId, victimLocation, inquiryComments, selectedLang));
        
        scanner.close();
	}
}