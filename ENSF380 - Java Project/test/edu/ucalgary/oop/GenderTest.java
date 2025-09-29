/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.Before;
 import org.junit.Test;
 
 import static org.junit.Assert.assertNotNull;
 
 public class GenderTest {
 
     private Gender gender;
 
     @Before
     public void setUp() {
         gender = new Gender();
     }
 
     @Test
     public void testGenderObjectCreated() {
         assertNotNull("Gender object should be created", gender);
     }
 }
 