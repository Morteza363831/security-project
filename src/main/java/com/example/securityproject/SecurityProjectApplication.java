package com.example.securityproject;

import com.example.securityproject.Entity.Student;
import com.example.securityproject.Repo.StudentRepo;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example")
public class SecurityProjectApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        File resource = new File("src/main/resources/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }

    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;

    public SecurityProjectApplication(StudentRepo studentRepo, PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student newStudent = new Student();
        newStudent.setName("John");
        newStudent.setUsername("mhzd");
        newStudent.setPassword(passwordEncoder.encode("mhzd"));
        studentRepo.save(newStudent);


    }
}
