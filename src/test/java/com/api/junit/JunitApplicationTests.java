package com.api.junit;

import com.api.junit.one.models.OldCollegeStudent;
import com.api.junit.one.models.OldStudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(classes = JunitApplication.class)
class JunitApplicationTests {

	private static int count = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
    OldCollegeStudent oldStudent;

	@Autowired
    OldStudentGrades oldStudentGrades;

	@Autowired
	ApplicationContext context;

	@BeforeEach
	void setUp() {
		count = count + 1;
		System.out.println("Testing " + appInfo + " which is " + appDescription + " Version " + appVersion
		+ ". Execution of test method " + count);

		oldStudent.setFirstname("Rahul");
		oldStudent.setLastname("Choudhary");
		oldStudent.setEmailAddress("student@gmail.com");

		oldStudentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
		oldStudent.setStudentGrades(oldStudentGrades);
	}

	@Test
	void contextLoads() {
	}

	@DisplayName("Add grades result for student grades")
	@Test
	void addGradeResultForStudentGrades() {
		assertEquals(353.25,
				oldStudentGrades.addGradeResultsForSingleClass(oldStudent.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("Add grades result for student grades not equal")
	@Test
	void addGradeResultForStudentGradesNotEqual() {
		assertNotEquals(355.25,
				oldStudentGrades.addGradeResultsForSingleClass(oldStudent.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("Is Grade Greater")
	@Test
	public void isGradeGreaterStudentGrades() {
		assertTrue(oldStudentGrades.isGradeGreater(90,75));
	}

	@DisplayName("Is Grade Not Greater")
	@Test
	public void isGradeNotGreaterStudentGrades() {
		assertFalse(oldStudentGrades.isGradeGreater(75,90));
	}

	@DisplayName("Check Null for student grades")
	@Test
	public void checkNullForStudentGrades() {
		assertNotNull(oldStudentGrades.checkNull(oldStudent.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("Verifies Student are prototype")
	@Test
	public void verifyStudentArePrototypes() {
		OldCollegeStudent oldCollegeStudent = context.getBean("oldCollegeStudent", OldCollegeStudent.class);
		assertSame(oldStudent, oldCollegeStudent); // default spring context scope is singleton that's why sam
	}

	@DisplayName("Find grade point average")
	@Test
	public void findGradePointAverage() {
		assertAll("Testing all assert",
				() -> assertEquals(353.25,
						oldStudentGrades.addGradeResultsForSingleClass(oldStudent.getStudentGrades().getMathGradeResults())),
				() -> assertNotEquals(355.25,
						oldStudentGrades.addGradeResultsForSingleClass(oldStudent.getStudentGrades().getMathGradeResults()))
				);
	}

}
