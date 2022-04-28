package com.swisscom.junit.service;

import com.swisscom.junit.models.CollegeStudent;
import com.swisscom.junit.models.Gradebook;
import com.swisscom.junit.repository.StudentDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application.properties")
class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentAndGradeService studentService;


    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Test', 'User', 'test@gmail.com')");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from student");
    }

    @Test
    void createStudent() {
        studentService.createStudent("Rahul", "Choudhary", "r@gmail.com");
        CollegeStudent byEmailAddress = studentDao.findByEmailAddress("r@gmail.com");
        assertEquals("r@gmail.com", byEmailAddress.getEmailAddress());
    }

    @Test
    void deleteStudent() {
        Optional<CollegeStudent> collegeStudent = studentDao.findById(1);
        assertTrue(collegeStudent.isPresent());

        studentService.deleteStudent(1);

        collegeStudent = studentDao.findById(1);
        assertFalse(collegeStudent.isPresent());

    }

    @Test
    void checkIfStudentIsNull() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void studentInformation() {
    }

    @Test
    void checkIfGradeIsNull() {
    }

    @Test
    void deleteGrade() {
    }

    @Test
    void createGrade() {
    }

    @Sql("/insertData.sql")
    @Test
    void getGradebook() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebookInitial();

        List<CollegeStudent> collegeStudentList = new ArrayList<>();
        for (CollegeStudent collegeStudent: collegeStudents) {
            collegeStudentList.add(collegeStudent);
        }

        assertEquals(5, collegeStudentList.size());
    }

    @Test
    void configureStudentInformationModel() {
    }
}
