package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public String messageGET() {
        return "Hola GET";
    }

    @PostMapping("/message")
    public String messagePOST() {
        return "Hola POST";
    }

    @DeleteMapping("/message")
    public String messageDELETE() {
        return "Hola DELETE";
    }

    @PutMapping("/message")
    public String messagePUT() {
        return "Hola PUT";
    }


}