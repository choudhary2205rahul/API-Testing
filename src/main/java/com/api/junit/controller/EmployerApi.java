package com.swisscom.observability.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/employer")
@Slf4j
public class EmployerApi {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEmployerName() {
        log.info("EmployerApi : getEmployerName() : return Swisscom");
        return "Swisscom";
    }
}
