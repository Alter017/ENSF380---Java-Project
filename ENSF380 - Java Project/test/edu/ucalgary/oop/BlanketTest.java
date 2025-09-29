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

public class BlanketTest {

    private Blanket blanket;

    @Before
    public void setUp() {
        blanket = new Blanket();
    }

    @Test
    public void testDefaultSupplyType() {
        assertThat(blanket.getSupplyType(), is("Blanket"));
    }

    @Test
    public void testSetAndGetSupplyType() {
        blanket.setSupplyType("Thermal Blanket");
        assertThat(blanket.getSupplyType(), is("Thermal Blanket"));
    }

    @Test
    public void testAllocateBlanketRunsWithoutError() {
        try {
            blanket.allocateBlanket(1);
        } catch (Exception e) {
            // If any exception is thrown, the test should fail
            assertThat("allocateBlanket() should not throw exception", false);
        }
    }
}
