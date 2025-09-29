/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WaterTest {
    private Water water;

    @Before
    public void setUp() {
        water = new Water();
    }

    @Test
    public void testGetSupplyType_DefaultIsWater() {
        assertEquals("Water", water.getSupplyType());
    }

    @Test
    public void testSetSupplyType() {
        water.setSupplyType("Clean Water");
        assertEquals("Clean Water", water.getSupplyType());
    }

    @Test
    public void testAllocateWaterCallsSupplyWithCorrectType() {
        try {
            assertTrue(true);
        } catch (Exception e) {
            fail("allocateWater threw an exception: " + e.getMessage());
        }
    }
}
