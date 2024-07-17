package com.example.securityproject.controller;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Repo.StudentRepo;
import com.example.securityproject.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        return "login";
    }

    @GetMapping("home")
    public String result(Model model) {
        return "home";
    }

}
