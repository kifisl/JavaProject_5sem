package com.example.nastya.controller;

import com.example.nastya.service.UsersService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Hidden
@Controller
public class ViewsController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/auth")
    public String authView() {
        return "auth";
    }

    @GetMapping("/main")
    public String mainView() {
        return "main";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = usersService.acrivateUser(code);

        if(isActivated) {
            model.addAttribute("message", "User successfully activated");
        }
        else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "auth";
    }

    @GetMapping("/documentation")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
