package com.api.junit.controller;

import com.api.junit.RunnerExtension;
import com.api.junit.models.CollegeStudent;
import com.api.junit.repository.HistoryGradesDao;
import com.api.junit.repository.MathGradesDao;
import com.api.junit.repository.ScienceGradesDao;
import com.api.junit.repository.StudentDao;
import com.api.junit.service.StudentAndGradeService;
import com.api.junit.util.ObservabilityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
@ExtendWith(RunnerExtension.class)
class GradeBookAPITest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentDao studentDao;

    @Mock
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CollegeStudent student;

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

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;


    //private static Tracer tracer;

    @BeforeAll
    public static void setup() {

       // tracer = ObservabilityUtil.initTracer("GradeBookApiTest");

        request = new MockHttpServletRequest();

        request.setParameter("firstname", "Chad");

        request.setParameter("lastname", "Darby");

        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }


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

//    @Test
//    void getStudents() throws Exception {
//
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//
//        try {
//
//            student.setFirstname("rahul");
//            student.setLastname("choudhary");
//            student.setEmailAddress("r@gmail.com");
//
//            entityManager.persist(student);
//            entityManager.flush();
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$", hasSize(2)));
//
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void createStudent() throws Exception {
//
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//
//            student.setFirstname("rahul");
//            student.setLastname("choudhary");
//            student.setEmailAddress("r@gmail.com");
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(student))
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$", hasSize(2)));
//
//            CollegeStudent byEmailAddress = studentDao.findByEmailAddress("r@gmail.com");
//            assertEquals("rahul", byEmailAddress.getFirstname());
//            assertNotNull(byEmailAddress);
//
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void deleteStudent() throws Exception {
//
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            assertTrue(studentDao.findById(1).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 1)
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$", hasSize(0)));
//
//            assertFalse(studentDao.findById(1).isPresent());
//
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void deleteStudentWhichNotExist() throws Exception {
//
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//
//            assertFalse(studentDao.findById(0).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 0)
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void getStudentInformation() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//
//            assertTrue(studentDao.findById(1).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1)
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.id", is(1)))
//                    .andExpect(jsonPath("$.firstname", is("Eric")))
//                    .andExpect(jsonPath("$.lastname", is("Roby")))
//                    .andExpect(jsonPath("$.emailAddress", is("eric.roby@luv2code_school.com")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void getStudentInformationWhichNotExist() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            assertFalse(studentDao.findById(0).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0)
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void createValidGrade() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.post("/grades")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("grade", "85.00")
//                            .param("gradeType", "math")
//                            .param("studentId", "1")
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.id", is(1)))
//                    .andExpect(jsonPath("$.firstname", is("Eric")))
//                    .andExpect(jsonPath("$.lastname", is("Roby")))
//                    .andExpect(jsonPath("$.emailAddress", is("eric.roby@luv2code_school.com")))
//                    .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(2)));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void createGradeForNonExistingStudent() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            assertFalse(studentDao.findById(0).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/grades")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("grade", "85.00")
//                            .param("gradeType", "math")
//                            .param("studentId", "0")
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void createInvalidGradeWithGradeType() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.post("/grades")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("grade", "85.00")
//                            .param("gradeType", "test")
//                            .param("studentId", "1")
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void deleteGrade() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            assertTrue(mathGradesDao.findById(1).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "math")
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.id", is(1)))
//                    .andExpect(jsonPath("$.firstname", is("Eric")))
//                    .andExpect(jsonPath("$.lastname", is("Roby")))
//                    .andExpect(jsonPath("$.emailAddress", is("eric.roby@luv2code_school.com")))
//                    .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(0)));
//
//            assertFalse(mathGradesDao.findById(1).isPresent());
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void deleteGradeNotFound() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            assertFalse(mathGradesDao.findById(0).isPresent());
//
//            mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 0, "math")
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void deleteGradeInvalidGradeType() throws Exception {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "test")
//                            .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().is4xxClientError())
//                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                    .andExpect(jsonPath("$.status", is(404)))
//                    .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void parentSpan() {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            childSpan(span);
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    private void childSpan(Span parentSpan) {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get())
//                .asChildOf(parentSpan)
//                .start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try {
//            log.info("Parent & Child Span");
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    @Test
//    void parentSpanUsingScope() {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get()).start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try(Scope scope = tracer.scopeManager().activate(span)) {
//            childSpanUsingScope();
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }
//
//    private void childSpanUsingScope() {
//        StackWalker walker = StackWalker.getInstance();
//        Optional<String> methodName = walker.walk(frames -> frames
//                .findFirst()
//                .map(StackWalker.StackFrame::getMethodName));
//
//
//        Span span = tracer.buildSpan(this.getClass().getName() + "-" + methodName.get())
//                .start();
//
//        span.setTag("class.name", this.getClass().getName());
//        span.setTag("method.name", methodName.get());
//
//        span.log("testing "+this.getClass().getName() + " - " + methodName.get());
//        try(Scope scope = tracer.scopeManager().activate(span)) {
//            log.info("Parent & Child Span");
//        } catch (Exception ex) {
//            log.error("Error " + ex);
//        } finally {
//            span.finish();
//        }
//    }



}
