package com.api.junit.one.component.service;

import com.api.junit.JunitApplication;
import com.api.junit.TestResultLoggerExtension;
import com.api.junit.one.component.dao.ApplicationDao;
import com.api.junit.one.component.models.ComponentCollegeStudent;
import com.api.junit.one.component.models.ComponentStudentGrades;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = JunitApplication.class)
@ExtendWith(TestResultLoggerExtension.class)
class ApplicationServiceTest {

    // Using Mockito Mock

//    @Mock // Mock ApplicationDao Class
//    private ApplicationDao applicationDao;
//
//    @InjectMocks // Only* inject Mock into ApplicationService class
//    private ApplicationService applicationService;

    // Using Spring @MockBean support with Mockito

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired // this will inject other dependency from context
    private ApplicationService applicationService;

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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addGradeResultsForSingleClass() {

        // when
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()))
                .thenReturn(100.00);

        assertEquals(100,
                applicationService.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,
                times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    void findGradePointAverage() {
        // when
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults()))
                .thenReturn(88.31);

        assertEquals(88.31,
                applicationService.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,
                times(1)).findGradePointAverage(studentGrades.getMathGradeResults());

    }

    @Test
    void checkNull() {
        // when
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);

        assertNotNull(applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,
                times(1)).checkNull(studentGrades.getMathGradeResults());

    }

    @Test
    void checkException() {
        // when
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () ->
                applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,
                times(1)).checkNull(studentGrades.getMathGradeResults());

    }

    @Test
    void checkExceptionMultipleStubbing() {
        // when
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenThrow(new RuntimeException())
                .thenReturn("Hey - Don't throw exception now");


        assertThrows(RuntimeException.class, () ->
                applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));

        assertEquals("Hey - Don't throw exception now",
                applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,
                times(2)).checkNull(studentGrades.getMathGradeResults());

    }


}
