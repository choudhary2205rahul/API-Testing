package com.api.junit.one.petclinic.controllers;

import com.api.junit.TestResultLoggerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(),"This test will pass");
        assertEquals("index", controller.index(), () -> "This test will pass");
        assertTrue("index".equals(controller.index()), () -> "This test will pass");
    }

    @Test
    void oopsHandler() {
    }
}
