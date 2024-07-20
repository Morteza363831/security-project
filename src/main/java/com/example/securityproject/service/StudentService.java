package com.example.securityproject.service;

import com.example.securityproject.Dto.StudentDto;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.UnknownHostException;

public interface StudentService {

    StudentDto findStudentByUsername(String username);

    void addStudent(StudentDto studentDto,String ip) throws IOException, GeoIp2Exception;
}
