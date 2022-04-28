package com.swisscom.junit.one.petclinic.model;

import com.swisscom.junit.one.petclinic.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void personModel() {
        Person person = new Person(1l, "Rahul", "Choudhary");

        assertAll("Testing Person Model",
                () -> assertEquals(1, person.getId()),
                () -> assertEquals("Rahul", person.getFirstName(), "First Name should match"),
                () -> assertEquals("Choudhary", person.getLastName())
                );
    }
}
