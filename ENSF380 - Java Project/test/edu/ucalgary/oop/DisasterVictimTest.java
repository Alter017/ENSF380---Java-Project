/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DisasterVictimTest {

    private DisasterVictim victim;

    @Before
    public void setUp() {
        victim = new DisasterVictim();
    }

    @Test
    public void testSetAndGetFirstName() {
        victim.setFirstName("Jane");
        assertThat(victim.getFirstName(), is("Jane"));
    }

    @Test
    public void testSetAndGetLastName() {
        victim.setLastName("Doe");
        assertThat(victim.getLastName(), is("Doe"));
    }

    @Test
    public void testSetAndGetDateOfBirth() {
        LocalDate dob = LocalDate.of(1990, 5, 15);
        victim.setDateOfBirth(dob);
        assertThat(victim.getDateOfBirth(), is(dob));
    }

    @Test
    public void testSetAndGetPhoneNumber() {
        victim.setPhoneNumber("555-1234");
        assertThat(victim.getPhoneNumber(), is("555-1234"));
    }

    @Test
    public void testSetAndGetGender() {
        victim.setGender("Other");
        assertThat(victim.getGender(), is("Other"));
    }

    @Test
    public void testSetAndGetFamilyGroup() {
        victim.setFamilyGroup(101);
        assertThat(victim.getFamilyGroup(), is(101));
    }

    @Test
    public void testSetAndGetPersonId() {
        victim.setPersonId(999);
        assertThat(victim.getPersonId(), is(999));
    }
}
