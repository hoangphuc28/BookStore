package com.example.ecommerce.controller;

import com.example.ecommerce.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private CustomUserDetailsService userDetailsService;
    @GetMapping("/")
    public String index() {
        return "Home/index";
    }
}
