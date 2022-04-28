package com.api.junit.one.tdd;

public class FizzBuzz {
    public static String compute(int i) {

//        if ((i % 3 == 0) && (i % 5 == 0)) {
//            return "FizzBuzz";
//        } else if (i % 3 == 0) {
//            return "Fizz";
//        } else if (i % 5 == 0) {
//            return "Buzz";
//        } else {
//            return Integer.toString(i);
//        }

        // Code Refactor
        StringBuilder result = new StringBuilder();

        if (i % 3 == 0) {
            result.append("Fizz");
        }

        if (i % 5 == 0) {
            result.append("Buzz");
        }

        if (result.isEmpty()) {
            result.append(i);
        }

        return result.toString();
    }
}
