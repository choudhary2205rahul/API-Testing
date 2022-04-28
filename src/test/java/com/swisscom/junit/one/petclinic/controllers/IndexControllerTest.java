package com.swisscom.junit.one.petclinic.controllers;

import com.swisscom.junit.one.petclinic.controllers.IndexController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
