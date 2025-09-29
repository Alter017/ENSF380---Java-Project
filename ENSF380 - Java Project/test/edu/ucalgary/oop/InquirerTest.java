/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.Before;
 import org.junit.Test;
 
 import java.sql.Timestamp;
 import java.time.LocalDateTime;
 
 import static org.hamcrest.CoreMatchers.is;
 import static org.junit.Assert.assertThat;
 
 public class InquirerTest {
 
     private Inquirer inquirer;
 
     @Before
     public void setUp() {
         inquirer = new Inquirer();
     }
 
     @Test
     public void testSetAndGetInquiryDate() {
         Timestamp now = Timestamp.valueOf(LocalDateTime.now());
         inquirer.setInquiryDate(now);
         assertThat(inquirer.getInquiryDate(), is(now));
     }
 
     @Test
     public void testSetAndGetInquirerId() {
         inquirer.setInquirerId(1234);
         assertThat(inquirer.getInquirerId(), is(1234));
     }
 
     @Test
     public void testSetAndGetMissingPersonId() {
         inquirer.setMissingPersonId(5678);
         assertThat(inquirer.getMissingPersonId(), is(5678));
     }
 
     @Test
     public void testSetAndGetVictimLocation() {
         inquirer.setVictimLocation(42);
         assertThat(inquirer.getVictimLocation(), is(42));
     }
 }
 