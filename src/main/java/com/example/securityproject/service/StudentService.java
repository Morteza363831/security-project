package com.example.securityproject.service;

import com.example.securityproject.Dto.StudentDto;

public interface StudentService {

    StudentDto findStudentByUsername(String username);
}
