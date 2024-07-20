package com.example.securityproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDto {

    Long id;
    String username;;
    String name;
    String password;
    String country;
    boolean enabled;


}
