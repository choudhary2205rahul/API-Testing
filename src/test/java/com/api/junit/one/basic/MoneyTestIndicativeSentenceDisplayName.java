package com.api.junit.one.basic;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class) // use Class and Method Name- ClassName, MethodName
class MoneyTestIndicativeSentenceDisplayName {

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
    void timesDollar() {

        Money expect10 = five.times(2);
        assertEquals(new Money(10), expect10);

        Money expect30 = five.times(3);
        assertEquals(new Money(15), expect30);
    }

    @Test
    void testEqualityDollar() {
        assertTrue(new Money(5).equals(new Money(5)));
        assertFalse(new Money(5).equals(new Money(6)));
    }

    @Test
    void timesFranc() {

        Money expect10 = five.times(2);
        assertEquals(new Money(10), expect10);

        Money expect30 = five.times(3);
        assertEquals(new Money(15), expect30);
    }

    @Test
    void testEqualityFranc() {
        assertTrue(new Money(5).equals(new Money(5)));
        assertFalse(new Money(5).equals(new Money(6)));
    }
}
