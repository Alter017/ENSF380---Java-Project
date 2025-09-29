/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DisasterVictim {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Map<String, String>> victims = new HashMap<>();
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String dobInput;
    private String phoneNumber;
    private String comments;
    private String gender;
    private int familyGroup;
    private int personId;
    private Gender gd = new Gender();
    private DatabaseManager db = new DatabaseManager();
    private FamilyGroup fmlygrp = new FamilyGroup();
    private Location lctn = new Location();
    
    public DisasterVictim() {}
    
    /**
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return date of birth
     */
    public LocalDate getDateOfBirth() {
        return dob;
    }

    /**
     * @param date of birth
     */
    public void setDateOfBirth(LocalDate dob) {
        this.dob = dob;
    }
    
    /**
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return family group
     */
    public int getFamilyGroup() {
        return familyGroup;
    }

    /**
     * @param family group
     */
    public void setFamilyGroup(int familyGroup) {
        this.familyGroup = familyGroup;
    }
    
    /**
     * @return person ID
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * @param person ID
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    
    /**
     * @param selected language
     */
    public void addNewVictim(int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        db.createConnection();
        
        firstName = "";
        while (firstName == "") {
	        System.out.print(langManager.getTranslation("dv_input_firstname"));
	        firstName = scanner.nextLine();
	        if (firstName == "")  {
	        	System.out.println(langManager.getTranslation("dv_invalid_firstname"));
	        }
        }
        lastName = "";
        while (lastName == "") {
	        System.out.print(langManager.getTranslation("dv_input_lastname"));
	        lastName = scanner.nextLine();
	        if (lastName == "")  {
	        	System.out.println(langManager.getTranslation("dv_invalid_lastname"));
	        }
        }

        dob = null;
        while (dob == null) {
            System.out.print(langManager.getTranslation("dv_input_birthdate"));
            dobInput = scanner.nextLine();
            try {
                dob = LocalDate.parse(dobInput);
            } catch (Exception e) {
                System.out.println(langManager.getTranslation("dv_invalid_birthdate"));
            }
        }

        gender = gd.selectGender(selectedLang);

        System.out.print(langManager.getTranslation("dv_input_comments"));
        comments = scanner.nextLine();

        while (true) {
            System.out.print(langManager.getTranslation("dv_input_phonenumber"));
            phoneNumber = scanner.nextLine();

            if (phoneNumber.matches("\\d{3}-\\d{4}")) {
                break;
            } else {
                System.out.println(langManager.getTranslation("dv_invalid_phonenumber"));
            }
        }

        familyGroup = fmlygrp.selectFamilyGroup(selectedLang);

        personId = db.insertPerson(firstName, lastName, Date.valueOf(dob), gender, comments, phoneNumber, familyGroup);
        db.closeConnection();
        
        lctn.selectLocation(personId, selectedLang);

        if (personId > 0) {
            Map<String, String> info = new HashMap<>();
            info.put("First Name", firstName);
            info.put("Last Name", lastName);
            info.put("Date of Birth", dob.toString());
            info.put("Gender", gender);
            info.put("Comments", comments);
            info.put("Phone Number", phoneNumber);
            info.put("Family Group", String.valueOf(familyGroup));
            info.put("Person ID", String.valueOf(personId));
            victims.put(personId, info);
            System.out.println(langManager.getTranslation("dv_victim_info_saved") + personId);
        } else {
            System.out.println(langManager.getTranslation("dv_victim_info_failed"));
        }
    }
    
    /**
     * @param person ID
     * @return whether person exists or not
     */
    public boolean personExists(int personId) {
    	
        db.createConnection();
    	
        try (ResultSet rs = db.getPersonById(personId)) {
            return rs != null && rs.next(); // person exists if we get a result
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}