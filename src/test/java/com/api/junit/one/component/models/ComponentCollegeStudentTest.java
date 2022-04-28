package com.api.junit.one.component.models;

import com.api.junit.JunitApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JunitApplication.class)
class ComponentCollegeStudentTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    ComponentCollegeStudent collegeStudent;

    @Autowired
    ComponentStudentGrades studentGrades;

    @BeforeEach
    void setUp() {
        collegeStudent.setFirstname("Rahul");
        collegeStudent.setLastname("Choudhary");
        collegeStudent.setEmailAddress("rahul@gmail.com");
        collegeStudent.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(collegeStudent, "id", 1);
        ReflectionTestUtils.setField(collegeStudent, "studentGrades",
                new ComponentStudentGrades(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75))));
    }

    @Test
    void invokePrivateFeild() {
        assertEquals(1, ReflectionTestUtils.getField(collegeStudent, "id"));
    }

    @Test
    void invokePrivateMethod() {
        assertEquals("Rahul 1", ReflectionTestUtils.invokeMethod(collegeStudent, "getFirstNameAndId"));
    }
}
