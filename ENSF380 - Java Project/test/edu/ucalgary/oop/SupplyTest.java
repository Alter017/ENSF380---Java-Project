/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.lang.reflect.Field;

public class SupplyTest {
    private Supply supply;

    @Before
    public void setUp() {
        supply = new Supply();
    }

    @Test
    public void testSetAndGetVictimId() {
        supply.setVicitimId(101);
        assertEquals(101, supply.getVicitimId());
    }

    @Test
    public void testSetAndGetLocationId() {
        supply.setLocationId(202);
        assertEquals(202, supply.getLocationId());
    }

    @Test
    public void testSetAndGetSupplyId() {
        supply.setSupplyId(303);
        assertEquals(303, supply.getSupplyId());
    }

    @Test
    public void testSetAndGetSupplyId2() {
        supply.setSupplyId2(404);
        assertEquals(404, supply.getSupplyId2());
    }

    @Test
    public void testCommentsCotValidFormat() throws Exception {
        // Mock Scanner input for "Cot" (room/grid format: 123 A45)
        setScannerInput("123 A45\n");
        String comment = supply.comments("Cot", 1);
        assertEquals("123 A45", comment);
    }

    @Test
    public void testCommentsIndividualItem() throws Exception {
        setScannerInput("Blankets\n");
        String comment = supply.comments("Individual Item", 1);
        assertEquals("Blankets", comment);
    }

    @Test
    public void testCommentsOtherType() throws Exception {
        setScannerInput("Some generic comment\n");
        String comment = supply.comments("Other", 1);
        assertEquals("Some generic comment", comment);
    }

    // Helper method to inject input into the scanner via reflection
    private void setScannerInput(String data) throws Exception {
        Field scannerField = Supply.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new java.util.Scanner(data));
    }
}
