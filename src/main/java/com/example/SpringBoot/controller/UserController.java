package com.example.SpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users/user")
public class UserController {
    @GetMapping("/info")
    public String userInfo(Principal principal) {
        return "user: " + principal.getName();

    }
}
