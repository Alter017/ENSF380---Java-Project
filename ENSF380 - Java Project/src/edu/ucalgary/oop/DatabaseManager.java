/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.sql.*;

public class DatabaseManager {
    private Connection dbConnect;

    public DatabaseManager() {}

    public void createConnection() {
        try {
            // Explicitly load driver class
            Class.forName("org.postgresql.Driver");
            
            // Then get connection
            dbConnect = DriverManager.getConnection(
                "jdbc:postgresql://localhost/ensf380project", 
                "oop", 
                "ucalgary"
            );
            System.out.println("Successfully connected to database!");
        } catch (ClassNotFoundException e) {
            System.err.println("POSTGRESQL DRIVER MISSING!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("CONNECTION FAILED!");
            e.printStackTrace();
        }
    }

    // ===================== PERSON =====================
    /**
     * @param first name
     * @param last name
     * @param date of birth
     * @param gender
     * @param comments
     * @param phone number
     * @param family group
     * @return person ID
     */
    public int insertPerson(String firstName, String lastName, Date dob, String gender,
                            String comments, String phoneNumber, int familyGroup) {
        String query = "INSERT INTO Person (first_name, last_name, date_of_birth, gender, comments, phone_number, family_group) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING person_id";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, dob);
            stmt.setString(4, gender);
            stmt.setString(5, comments);
            stmt.setString(6, phoneNumber);
            stmt.setInt(7, familyGroup);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * @param person ID
     * @param comments
     * @returns true or false
     */
    public boolean updatePersonComments(int personId, String comments) {
        String query = "UPDATE Person SET comments = ? WHERE person_id = ?";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setString(1, comments);
            stmt.setInt(2, personId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param person ID
     * @return person ID
     */
    public ResultSet getPersonById(int id) {
        try {
            String query = "SELECT * FROM Person WHERE person_id = ?";
            PreparedStatement stmt = dbConnect.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // ===================== LOCATION ==============================
    /**
     * @param person ID
     * @param location ID
     */
    public void insertPersonLocation(int personId, int locationId) {
        String query = "INSERT INTO personlocation (person_id, location_id) VALUES (?, ?)";
        
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, personId);
            stmt.setInt(2, locationId);
            stmt.executeUpdate();
            System.out.println("Person-location association successfully added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param location ID
     * @return location ID
     */
    public ResultSet getLocationById(int id) {
        try {
            String query = "SELECT * FROM location WHERE location_id = ?";
            PreparedStatement stmt = dbConnect.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===================== SUPPLY ALLOCATION =====================
    /**
     * @param supply ID
     * @param person ID
     */
    public void allocateSupplyToPerson(int supplyId, int personId) {
        String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id) VALUES (?, ?, NULL)";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, supplyId);
            stmt.setInt(2, personId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param supply ID
     * @param location ID
     */
    public void allocateSupplyToLocation(int supplyId, int locationId) {
        String query = "INSERT INTO SupplyAllocation (supply_id, location_id, person_id) VALUES (?, ?, NULL)";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, supplyId);
            stmt.setInt(2, locationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===================== SUPPLY =====================
    /**
     * @param supply type
     * @param supply comments
     * @return supply ID
     */
    public int insertSupply(String type, String comments) {
        String query = "INSERT INTO Supply (type, comments) VALUES (?, ?) RETURNING supply_id";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setString(1, type);
            stmt.setString(2, comments);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ===================== MEDICAL RECORD =====================
    /**
     * @param location ID
     * @param person ID
     * @param treatment date
     * @param medical record details
     */
    public void insertMedicalRecord(int locationId, int personId, Timestamp treatmentDate, String details) {
        String query = "INSERT INTO MedicalRecord (location_id, person_id, date_of_treatment, treatment_details) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, locationId);
            stmt.setInt(2, personId);
            stmt.setTimestamp(3, treatmentDate);
            stmt.setString(4, details);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===================== INQUIRY =====================
    /**
     * @param inquirer ID
     * @param missing person ID
     * @param location ID
     * @param date of inquiry
     * @param comments
     */
    public void logInquiry(int inquirerId, int seekingId, int locationId, Timestamp dateOfInquiry, String comments) {
        String query = "INSERT INTO Inquiry (inquirer_id, seeking_id, location_id, date_of_inquiry, comments) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, inquirerId);
            stmt.setInt(2, seekingId);
            stmt.setInt(3, locationId);
            stmt.setTimestamp(4, dateOfInquiry);
            stmt.setString(5, comments);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===================== CLEANUP =====================
    public void closeConnection() {
        try {
            if (dbConnect != null && !dbConnect.isClosed()) {
                dbConnect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
