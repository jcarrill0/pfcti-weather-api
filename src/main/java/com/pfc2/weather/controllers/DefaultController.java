package com.pfc2.weather.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DefaultApiController")
@RestController
@RequestMapping("/sys/status")
@CrossOrigin(origins = "*")
public class DefaultController {

    @GetMapping
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
