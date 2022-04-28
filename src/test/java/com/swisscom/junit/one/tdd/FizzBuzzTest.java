package com.swisscom.junit.one.tdd;

import com.swisscom.junit.one.tdd.FizzBuzz;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    @Test
    @DisplayName("Divisible by Three")
    @Order(1)
    void testNumberDivisibleByThree() {
        // Step 1
        //fail("fail");

        // Step 2
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3), "Should return Fizz");

    }

    @Test
    @DisplayName("Divisible by Five")
    @Order(2)
    void testNumberDivisibleByFive() {

        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.compute(5), "Should return Buzz");

    }

    @Test
    @DisplayName("Divisible by Three & Five")
    @Order(3)
    void testNumberDivisibleByThreeAndFive() {

        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.compute(15), "Should return FizzBuzz");

    }

    @Test
    @DisplayName("Not Divisible by Three & Five")
    @Order(4)
    void testNumberNotDivisibleByThreeAndFive() {

        String expected = "1";
        assertEquals(expected, FizzBuzz.compute(1), "Should return 1");

    }


    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "value={0}, expected={1}") // update custom name for input and output
    @CsvSource({
            "1,1",
            "2,2",
            "3,Fizz",
            "4,4",
            "5,Buzz",
            "15,FizzBuzz"
    })
    @Order(5)
    void testParameterizedTest(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value), "Should return 1");
    }

    @DisplayName("Parameterized Test Csv File Source")
    @ParameterizedTest(name = "value={0}, expected={1}") // update custom name for input and output
    @CsvFileSource(resources = "/fizz-buzz-test-data.csv")
    @Order(5)
    void testParameterizedTestCsvFileSource(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value), "Should return 1");
    }
}
