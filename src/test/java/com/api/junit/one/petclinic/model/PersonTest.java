package com.api.junit.one.petclinic.model;

import com.api.junit.TestResultLoggerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
