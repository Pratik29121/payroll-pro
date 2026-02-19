package com.example.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class PayrollApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayrollApplication.class, args);
    }
}

@Controller
class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
}
