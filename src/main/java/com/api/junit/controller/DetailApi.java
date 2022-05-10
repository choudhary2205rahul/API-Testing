package com.api.junit.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/employees-details")
@Slf4j
public class DetailApi {
    private RestTemplate restTemplate;

    private Counter counter;


    public DetailApi(RestTemplate restTemplate, MeterRegistry meterRegistry ) {
        this.restTemplate = restTemplate;
        this.counter = Counter.builder("hit_counter")
                .description("Number of hits")
                .register(meterRegistry);
    }

    @GetMapping
    public ResponseEntity<String> getEmployeeDetail() {
        log.info("EmployeeApi : getEmployeeDetail() : return Swisscom is employer of Rahul Choudhary");
        String employeeName = restTemplate.getForObject("http://localhost:1500/employees", String.class);
        String employerName = restTemplate.getForObject("http://localhost:1500/employer", String.class);

        counter.increment();

        return ResponseEntity.ok(employerName + " is employer of " + employeeName);
//        ResponseEntity<Genderize> employerName = restTemplate.getForEntity("https://api.genderize.io/?name=Rahul", Genderize.class);
//        return employerName.getBody().getName();
    }
}
