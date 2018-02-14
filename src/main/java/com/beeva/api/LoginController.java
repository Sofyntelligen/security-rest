package com.beeva.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class LoginController {

    @GetMapping("/")
    public String authenticationUser() {
        return "hola ";
    }
}

