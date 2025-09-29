/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;
    
    public ReliefService() {}

    /**
     * @param inquirer
     * @param missing person
     * @param date of inquiry
     * @param information provided
     * @param last known location
     */
    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, String dateOfInquiry, 
                         String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        this.dateOfInquiry = dateOfInquiry;
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    /**
     * @return inquirer
     */
    public Inquirer getInquirer() {
        return inquirer;
    }

    /**
     * @param inquirer
     */
    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    /**
     * @return missing person
     */
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    /**
     * @param missing person
     */
    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    /**
     * @return date of inquiry
     */
    public String getDateOfInquiry() {
        return dateOfInquiry;
    }

    /**
     * @param date of inquiry
     */
    public void setDateOfInquiry(String dateOfInquiry) {
        if (isValidDateFormat(dateOfInquiry)) {
            this.dateOfInquiry = dateOfInquiry;
        } else {
            System.out.println("Invalid date format. Please use 'YYYY-MM-DD'.");
        }
    }

    /**
     * @return information provided
     */
    public String getInfoProvided() {
        return infoProvided;
    }

    /**
     * @param information provided
     */
    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    /**
     * @return last known location
     */
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    /**
     * @param last known location
     */
    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    /**
     * @param selected date
     * @return whether date is valid or not
     */
    private boolean isValidDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Method to log the details of the inquiry
    /**
     * @param inquirer
     * @param missing person
     * @param current location
     * @param information provided
     * @param selected language
     * @return relief service message
     */
    public String logDetails(int inquirer, int missingPerson, int currentLocation, String infoProvided, int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        return langManager.getTranslation("rs1") + "\n" +
               langManager.getTranslation("rs2") + String.valueOf(inquirer) + "\n" +
               langManager.getTranslation("rs3") + String.valueOf(missingPerson) + "\n" +
               langManager.getTranslation("rs4") + infoProvided + "\n" +
               langManager.getTranslation("rs5") + String.valueOf(currentLocation) + "\n" +
               langManager.getTranslation("rs6");
    }
}
