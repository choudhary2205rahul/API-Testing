package com.swisscom.observability.api;

import com.swisscom.observability.model.Genderize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/employees-details")
@Slf4j
public class DetailApi {
    private RestTemplate restTemplate;

    public DetailApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getEmployeeDetail() {
        log.info("EmployeeApi : getEmployeeDetail() : return Swisscom is employer of Rahul Choudhary");

        ResponseEntity<Genderize> employerName = restTemplate.getForEntity("https://api.genderize.io/?name=Rahul", Genderize.class);
        return employerName.getBody().getName();
    }
}
