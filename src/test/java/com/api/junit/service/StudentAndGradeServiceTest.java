package com.api.junit.service;

import com.api.junit.TestResultLoggerExtension;
import com.api.junit.models.*;
import com.api.junit.repository.HistoryGradesDao;
import com.api.junit.repository.MathGradesDao;
import com.api.junit.repository.ScienceGradesDao;
import com.api.junit.repository.StudentDao;
import com.api.junit.util.ObservabilityUtil;
import io.opentracing.Span;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ExtendWith(TestResultLoggerExtension.class)
class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;


    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
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
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradesDao.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradesDao.findById(1);

        assertTrue(collegeStudent.isPresent());
        assertTrue(mathGrade.isPresent());
        assertTrue(scienceGrade.isPresent());
        assertTrue(historyGrade.isPresent());

        // Call Delete Service
        studentService.deleteStudent(1);

        // Check Student Delete
        collegeStudent = studentDao.findById(1);
        assertFalse(collegeStudent.isPresent());

        // Check Grades Delete
        mathGrade = mathGradesDao.findById(1);
        scienceGrade = scienceGradesDao.findById(1);
        historyGrade = historyGradesDao.findById(1);

        assertFalse(mathGrade.isPresent());
        assertFalse(scienceGrade.isPresent());
        assertFalse(historyGrade.isPresent());

    }

    @Test
    void checkIfStudentIsNull() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("Eric", gradebookCollegeStudent.getFirstname());
        assertEquals("Roby", gradebookCollegeStudent.getLastname());
        assertEquals("eric.roby@luv2code_school.com", gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    void invalidStudentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }

    @Test
    void checkIfGradeIsNull() {

        assertFalse(studentService.checkIfGradeIsNull(1,"test"));
        assertTrue(studentService.checkIfGradeIsNull(1,"math"));
        assertTrue(studentService.checkIfGradeIsNull(1,"science"));
        assertTrue(studentService.checkIfGradeIsNull(1,"history"));

    }

    @Test
    void deleteGrade() {
        assertEquals(1, studentService.deleteGrade(1,"math"));
        assertEquals(1, studentService.deleteGrade(1,"science"));
        assertEquals(1, studentService.deleteGrade(1,"history"));
        assertEquals(0, studentService.deleteGrade(1,"test"));
    }

    @Test
    void deleteInvalidGrade() {
        assertEquals(0, studentService.deleteGrade(2,"math"));
        assertEquals(0, studentService.deleteGrade(2,"science"));
        assertEquals(0, studentService.deleteGrade(2,"history"));
        assertEquals(0, studentService.deleteGrade(1,"test"));
    }

    @Test
    void createGrade() {
        assertTrue(studentService.createGrade(80.55, 1, "math"));
        assertTrue(studentService.createGrade(81.55, 1, "science"));
        assertTrue(studentService.createGrade(82.55, 1, "history"));

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2 );
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2 );
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2 );
    }

    @Test
    void createInvalidGrade() {
        assertFalse(studentService.createGrade(180.55, 1, "math"));
        assertFalse(studentService.createGrade(-180.55, 1, "math"));
        assertFalse(studentService.createGrade(180.55, 2, "math"));
        assertFalse(studentService.createGrade(180.55, 2, "test"));

        assertFalse(studentService.createGrade(181.55, 1, "science"));
        assertFalse(studentService.createGrade(-181.55, 1, "science"));
        assertFalse(studentService.createGrade(181.55, 2, "science"));

        assertFalse(studentService.createGrade(182.55, 1, "history"));
        assertFalse(studentService.createGrade(-182.55, 1, "history"));
        assertFalse(studentService.createGrade(182.55, 2, "history"));

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);


        assertTrue(((Collection<MathGrade>) mathGrades).size() == 1 );
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 1 );
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 1 );

    }


    @Sql("/insertData.sql")
    @Test
    void getGradebook() {
        Gradebook gradebook = studentService.getGradebook();

        List<CollegeStudent> collegeStudentList = new ArrayList<>();
        for (CollegeStudent collegeStudent: gradebook.getStudents()) {
            collegeStudentList.add(collegeStudent);
        }

        assertEquals(5, collegeStudentList.size());
    }


}
