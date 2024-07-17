package com.example.securityproject;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Entity.Student;
import com.example.securityproject.Repo.StudentRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.example")
public class WebConfig {

    private final StudentRepo studentRepo;

    public WebConfig(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Student student = studentRepo.findStudentsByUsername(username);
            if (student == null) {
                throw new UsernameNotFoundException(username);
            }
            else {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(student.getUsername())
                        .password(student.getPassword())
                        .roles("STUDENT")
                        .build();
            }
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request -> {
                    request.requestMatchers("/login")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(request -> {
                    request.loginPage("/login")
                            .defaultSuccessUrl("/home", true)
                            .permitAll();
                })
                .csrf(csrf -> {
                    csrf.ignoringRequestMatchers("/h2-console/**");
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
