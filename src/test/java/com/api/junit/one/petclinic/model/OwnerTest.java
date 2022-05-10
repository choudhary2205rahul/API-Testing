package com.api.junit.one.petclinic.model;

import com.api.junit.TestResultLoggerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(1l, "Rahul", "Choudhary");
        owner.setCity("Rotterdam");
        owner.setAddress("Voermanweg 570");
        owner.setTelephone("0616818794");
    }

    @Test
    void ownerPojoTest() {
        assertAll("Owner Pojo Test",
                () ->
                    assertAll("Person Tests",
                            () -> assertEquals(1, owner.getId()),
                            () -> assertEquals("Rahul", owner.getFirstName()),
                            () -> assertEquals("Choudhary", owner.getLastName())
                    ),
                () -> assertAll("Owner Tests",
                        () -> assertEquals("Rotterdam", owner.getCity()),
                        () -> assertEquals("Voermanweg 570", owner.getAddress()),
                        () -> assertEquals("0616818794", owner.getTelephone())
                )
        );
    }

    @Test
    void ownerPojoNegativeTest() {
        assertAll("Owner Pojo Negative Test",
                () ->
                        assertAll("Person Tests",
                                () -> assertNotEquals(2, owner.getId()),
                                () -> assertNotEquals("Shalu", owner.getFirstName()),
                                () -> assertNotEquals("Baliyan", owner.getLastName())
                        ),
                () -> assertAll("Owner Tests",
                        () -> assertNotEquals("Gurgaon", owner.getCity()),
                        () -> assertNotEquals("Synera 2105", owner.getAddress()),
                        () -> assertNotEquals("9582816659", owner.getTelephone())
                )
        );
    }
}
