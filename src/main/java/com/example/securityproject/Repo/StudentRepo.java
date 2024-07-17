package com.example.securityproject.Repo;

import com.example.securityproject.Dto.StudentDto;
import com.example.securityproject.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query(value = "select * from student where username = :username", nativeQuery = true)

    Student findStudentsByUsername(@Param("username") String username);
}
