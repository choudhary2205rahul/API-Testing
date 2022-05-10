package com.api.junit.one.basic;

import com.api.junit.TestResultLoggerExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestResultLoggerExtension.class)
class DemoUtilsTest {

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
   // @Disabled("Disabled") // These can be used at class level or method level
    @EnabledOnOs(OS.MAC)
    @EnabledOnJre(JRE.JAVA_18)
    void name() {

    }

    @Test
    //@EnabledIfEnvironmentVariable(named = "ENV", matches = "TEST") // Under Environment: ENV=TEST
   // @EnabledIfSystemProperty(named = "ENV", matches = "TEST") // -ea ENV=TEST
    void testGetAcademy() {
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate()); // check object reference
        assertNotSame(academy, demoUtils.getAcademyDuplicate());
    }

    @Test
    void testIsGreater() {
        assertTrue(demoUtils.isGreater(5,2));
        assertFalse(demoUtils.isGreater(2,5));
    }

    @Test
    void testFirstThreeAlphabets() {
        String[] stringArray = {"A", "B", "C"};
        assertArrayEquals(stringArray, demoUtils.getFirstThreeAlphabets());
    }

    @Test
    void testAcademyInList() {
        List<String> name = List.of("Rahul", "Choudhary");
        assertIterableEquals(name, demoUtils.getAcademyInList());
    }

    @Test
    void testThrowsException() {
        assertThrows(Exception.class, () -> {demoUtils.throwsException(-1);});
        assertDoesNotThrow(() -> {demoUtils.throwsException(1);});
    }

    @Test
    void testCheckTimeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {demoUtils.checkTimeout();});
    }
}
