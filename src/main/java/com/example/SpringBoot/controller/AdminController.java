package com.example.SpringBoot.controller;

import com.example.SpringBoot.model.User;
import com.example.SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listCustomers(Model model) {
        model.addAttribute("users", userService.getCustomerList());
        return "list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.showById(id));
        return "user";
    }

    @GetMapping("/{id}/edit")
    public String update(Model model, @PathVariable("id") Long id) {
        User user1 = userService.get(id);
        model.addAttribute("user", user1);
        return "edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user, id);
        return "redirect:/admin/users";
    }

    @GetMapping("/new")
    public String newCustomer(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/create")
    public String saveCustomer(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }
//    @RequestMapping("/getOne")
//    @ResponseBody
//    public Optional<User> getOne(Long id) {
//        return userService.getOne(id);
//    }
//    @RequestMapping(value="/update", method={RequestMethod.PUT, RequestMethod.GET})
//    public String update(User user) {
//        userService.update(user, user.getId());
//        return "redirect:/hello";
//    }
}
