package com.example.securityproject.service;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Entity.Student;
import com.example.securityproject.Repo.StudentRepo;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepo studentRepo;
    private final DatabaseReader databaseReader;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(ModelMapper modelMapper, StudentRepo studentRepo,
                              DatabaseReader databaseReader, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.studentRepo = studentRepo;
        this.databaseReader = databaseReader;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentDto findStudentByUsername(String username) {
        StudentDto studentDto = modelMapper.map(studentRepo.findStudentsByUsername(username), StudentDto.class);
        return studentDto;
    }

    @Override
    public void addStudent(StudentDto studentDto,String ip) throws IOException, GeoIp2Exception {
        Student student = modelMapper.map(studentDto, Student.class);
        String fkl = "37.114.193.194";
        String country = getCountryFromIP(fkl);
        System.out.println(country);
        student.setCountry(country);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepo.save(student);
    }

    private String getCountryFromIP(String ipAddress) {
        if ("127.0.0.1".equals(ipAddress) || "localhost".equalsIgnoreCase(ipAddress)) {
            return "Localhost"; // Return a default value for localhost
        }

        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            CountryResponse response = databaseReader.country(ip); // Use the country method
            return response.getCountry().getName(); // Get the country name
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
            return "Unknown"; // Default country if there's an error
        }
    }

}
