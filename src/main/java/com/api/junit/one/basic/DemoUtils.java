package com.api.junit.one.basic;

import java.util.List;

public class DemoUtils {

    private String academy = "Academy";
    private String academyDuplicate = academy;

    public String getAcademy() {
        return academy;
    }

    public String getAcademyDuplicate() {
        return academyDuplicate;
    }

    public Boolean isGreater(int n1, int n2) {
        if (n1 > n2) {
            return true;
        }
        return false;
    }

    private String[] firstThreeAlphabets = {"A", "B", "C"};

    public String[] getFirstThreeAlphabets() {
        return firstThreeAlphabets;
    }

    private List<String> academyInList = List.of("Rahul", "Choudhary");

    public List<String> getAcademyInList() {
        return academyInList;
    }

    public String throwsException(int a) throws Exception {
        if (a < 0) {
            throw new Exception("Value should be greater than 0");
        }
        return "Value is greater then 0";
    }

    public void checkTimeout() throws InterruptedException {
        System.out.println("Going to sleep for 2 sec");
        Thread.sleep(2000);
        System.out.println("Sleeping Over");
    }
}
