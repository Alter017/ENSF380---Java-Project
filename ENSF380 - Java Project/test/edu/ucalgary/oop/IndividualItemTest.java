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
 
 public class IndividualItemTest {
 
     private IndividualItem item;
 
     @Before
     public void setUp() {
         item = new IndividualItem();
     }
 
     @Test
     public void testDefaultSupplyType() {
         assertThat(item.getSupplyType(), is("Individual Item"));
     }
 
     @Test
     public void testSetAndGetSupplyType() {
         item.setSupplyType("Water Bottle");
         assertThat(item.getSupplyType(), is("Water Bottle"));
     }
 }
 