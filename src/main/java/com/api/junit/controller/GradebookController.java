package com.api.junit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        return "index";
    }
}
