package com.hotel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @PostMapping("/changeLogLevel")
    @Secured("ROLE_ADMIN")
    public void changeLogLevel(
            HttpServletResponse response,
            @RequestParam("LogLevel") String logLevel) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("configuredLevel", logLevel);

        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);

        restTemplate.postForObject("http://localhost:8080/actuator/loggers/ROOT", request, String.class);
        response.sendRedirect("/admin");
    }
}
