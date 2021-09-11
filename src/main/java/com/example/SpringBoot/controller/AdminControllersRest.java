package com.example.SpringBoot.controller;


import com.example.SpringBoot.model.User;
import com.example.SpringBoot.service.UserService;
import com.example.SpringBoot.service.UserServiceImpl;
import com.example.SpringBoot.transferObject.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/users/admin")
public class AdminControllersRest {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        String[] roles = {"ADMIN", "USER"};
        model.addAttribute("user", new NewUserRequest());
        model.addAttribute("roles", roles);
        return "new";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("user") NewUserRequest user) {
        userService.save(user);
        return "redirect:/users/admin/list";
    }

//    @GetMapping("/update/{id}")
//    public String update(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//    @PostMapping("/{id}")
//    public String up(@ModelAttribute("user") User user){
//        userService.save(user);
//        return "redirect:/users/admin/list";
//    }
    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userInfo";
    }

    @GetMapping("/delete/{id}")
        public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/admin/list";
    }

}