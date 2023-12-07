package com.example.proxy.controller;

import com.example.proxy.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyContoller {
    private final RestTemplate rest = new RestTemplate();

    @Value("${name.service.url}")
    private String paymentsServiceUrl;


    @GetMapping
    public ResponseEntity<Student> getStudent(@RequestParam(required = true) Long id) {
        String uri = paymentsServiceUrl + "/api/v1/students/" + String.valueOf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", UUID.randomUUID().toString());

        HttpEntity<Student> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Student> response = rest.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                Student.class
        );

        Student studentList = response.getBody();

        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }
}
