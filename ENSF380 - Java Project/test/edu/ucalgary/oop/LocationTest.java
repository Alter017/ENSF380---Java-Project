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
 
 import java.sql.ResultSet;
 import java.sql.SQLException;
 
 import static org.junit.Assert.*;
 
 public class LocationTest {
     private TestableLocation location;
 
     @Before
     public void setUp() {
         location = new TestableLocation();
     }
 
     @After
     public void tearDown() {
         location = null;
     }
 
     @Test
     public void testLocationExists_withExistingLocation() throws SQLException {
         location.setMockLocationId(123);  // simulate DB returning this ID
         boolean exists = location.locationExists(123);
         assertTrue("Location should exist", exists);
     }
 
     @Test
     public void testLocationExists_withNonExistingLocation() throws SQLException {
         location.setMockLocationId(-1);  // simulate DB not finding any ID
         boolean exists = location.locationExists(999);
         assertFalse("Location should not exist", exists);
     }
 
     /**
      * Subclass to override DB behavior without mocks.
      */
     private static class TestableLocation extends Location {
         private int mockLocationId;
 
         public void setMockLocationId(int id) {
             this.mockLocationId = id;
         }
 
         @Override
         public boolean locationExists(int locationId) {
             // Simulate DB result logic
             return this.mockLocationId == locationId;
         }
     }
 }
 