package com.api.junit.one.basic;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // replace under score with spaces
class MoneyTestReplaceUnderscoreDisplayName {

    Money five;
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting Money Tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Ending Money Tests");
    }

    @BeforeEach
    void setUp() {
        five = new Money(5);
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each Money Tests");
    }

    @Test
    void times_Dollar() {

        Money expect10 = five.times(2);
        assertEquals(new Money(10), expect10);

        Money expect30 = five.times(3);
        assertEquals(new Money(15), expect30);
    }

    @Test
    void test_Equality_Dollar() {
        assertTrue(new Money(5).equals(new Money(5)));
        assertFalse(new Money(5).equals(new Money(6)));
    }

    @Test
    void times_Franc() {

        Money expect10 = five.times(2);
        assertEquals(new Money(10), expect10);

        Money expect30 = five.times(3);
        assertEquals(new Money(15), expect30);
    }

    @Test
    void test_Equality_Franc() {
        assertTrue(new Money(5).equals(new Money(5)));
        assertFalse(new Money(5).equals(new Money(6)));
    }
}
