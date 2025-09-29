/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.Before;
 import org.junit.Test;
 
 import static org.hamcrest.CoreMatchers.is;
 import static org.junit.Assert.assertThat;
 
 public class FamilyGroupTest {
 
     private FamilyGroup familyGroup;
 
     @Before
     public void setUp() {
         familyGroup = new FamilyGroup();
     }
 
     @Test
     public void testSetAndGetFamilyGroup() {
         familyGroup.setFamilyGroup(42);
         assertThat(familyGroup.getFamilyGroup(), is(42));
     }
 }
 