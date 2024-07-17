package com.example.securityproject.service;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Repo.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepo studentRepo;

    public StudentServiceImpl(ModelMapper modelMapper, StudentRepo studentRepo) {
        this.modelMapper = modelMapper;
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentDto findStudentByUsername(String username) {
        StudentDto studentDto = modelMapper.map(studentRepo.findStudentsByUsername(username), StudentDto.class);
        return studentDto;
    }
}
