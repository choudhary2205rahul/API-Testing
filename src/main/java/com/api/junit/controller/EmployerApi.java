package com.api.junit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employer")
@Slf4j
public class EmployerApi {

    @GetMapping
    public ResponseEntity<String> getEmployerName() {
        log.info("EmployerApi : getEmployerName() : return Swisscom");
        //return "Swisscom";
        return ResponseEntity.ok("Swisscom");
    }
}
