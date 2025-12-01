package com.example.springbootrestdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    // Static welcome endpoint
    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to Spring Boot!";
    }

    // /greet?name=Alice
    @GetMapping("/greet")
    public ResponseEntity<String> greetUser(
            @RequestParam(required = false) String name) {

        if (name == null || name.isBlank()) {
            // Default message
            return ResponseEntity.ok("Hello, Guest!");
        }

        // Valid request
        return ResponseEntity.ok("Hello, " + name + "!");
    }

    // Example error handling: /badrequest
    @GetMapping("/badrequest")
    public ResponseEntity<String> badRequest() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid request parameters");
    }
}
