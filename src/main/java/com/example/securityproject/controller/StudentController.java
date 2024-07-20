package com.example.securityproject.controller;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Repo.StudentRepo;
import com.example.securityproject.aop.LocationChecking;
import com.example.securityproject.service.StudentService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;

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
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("studentDto") StudentDto studentDto, HttpServletRequest request)
            throws IOException, GeoIp2Exception {
        studentService.addStudent(studentDto, getClientIP(request));
        System.out.println(getClientIP(request) + " ip address: " + request.getRemoteAddr());
        return "redirect:/login";
    }
    @GetMapping("home")
    @LocationChecking
    public String home(Model model) {
        return "home";
    }



    private String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
