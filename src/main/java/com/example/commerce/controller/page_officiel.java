package com.example.commerce.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ComponentScan
@RequestMapping("dashboard")
public class page_officiel {
    @GetMapping
    public String showRegistrationForm(){
        return "dashboard" ;
    }
}
