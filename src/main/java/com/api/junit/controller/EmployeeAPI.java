package com.api.junit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeApi {

    @GetMapping
    public ResponseEntity<String> getEmployeeName() {
        log.info("EmployeeApi : getEmployeeName() : return Rahul Choudhary");
        //return "Rahul Choudhary";
        return ResponseEntity.ok("Rahul Choudhary");
    }


}
