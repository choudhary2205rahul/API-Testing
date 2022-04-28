package com.swisscom.junit.one.basic;

import com.swisscom.junit.one.basic.DemoUtils;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTestOrderByOrder {

    DemoUtils demoUtils;
    String academy = "Test";

    @BeforeEach
    void setUp() {
        demoUtils = new DemoUtils();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void testGetAcademy() {
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate()); // check object reference
        assertNotSame(academy, demoUtils.getAcademyDuplicate());
    }

    @Test
    @Order(2)
    void testIsGreater() {
        assertTrue(demoUtils.isGreater(5,2));
        assertFalse(demoUtils.isGreater(2,5));
    }

    @Test
    @Order(3)
    void testFirstThreeAlphabets() {
        String[] stringArray = {"A", "B", "C"};
        assertArrayEquals(stringArray, demoUtils.getFirstThreeAlphabets());
    }

    @Test
    @Order(4)
    void testAcademyInList() {
        List<String> name = List.of("Rahul", "Choudhary");
        assertIterableEquals(name, demoUtils.getAcademyInList());
    }

    @Test
    @Order(5)
    void testThrowsException() {
        assertThrows(Exception.class, () -> {demoUtils.throwsException(-1);});
        assertDoesNotThrow(() -> {demoUtils.throwsException(1);});
    }

    @Test
    @Order(6)
    void testCheckTimeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {demoUtils.checkTimeout();});
    }
}
