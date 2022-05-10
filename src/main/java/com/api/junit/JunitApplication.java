package com.api.junit;

import com.api.junit.models.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JunitApplication {

	public static void main(String[] args) {
		SpringApplication.run(JunitApplication.class, args);
	}

	@Bean
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

	@Bean
	@Scope(value = "prototype")
	Grade getMathGrade(double grade) {
		return new MathGrade(grade);
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("mathGrades")
	MathGrade getGrade() {
		return new MathGrade();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("scienceGrades")
	ScienceGrade getScienceGrade() {
		return new ScienceGrade();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("historyGrades")
	HistoryGrade getHistoryGrade() {
		return new HistoryGrade();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
