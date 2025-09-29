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

public class CotTest {

    private Cot cot;

    @Before
    public void setUp() {
        cot = new Cot();
    }

    @Test
    public void testDefaultSupplyType() {
        assertThat(cot.getSupplyType(), is("Cot"));
    }

    @Test
    public void testSetAndGetSupplyType() {
        cot.setSupplyType("Sleeping Cot");
        assertThat(cot.getSupplyType(), is("Sleeping Cot"));
    }

    @Test
    public void testAllocateCotRunsWithoutError() {
        // This test just ensures allocateCot() executes without throwing exceptions
        try {
            cot.allocateCot(2);
        } catch (Exception e) {
            assertThat("allocateCot() should not throw exception", false);
        }
    }
}
