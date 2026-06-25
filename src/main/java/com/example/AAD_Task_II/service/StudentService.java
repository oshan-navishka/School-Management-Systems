package com.example.AAD_Task_II.service;

import com.example.AAD_Task_II.dto.StudentDTO;

public interface StudentService {
    void saveStudent(StudentDTO studentDTO);
    void updateStudent(StudentDTO studentDTO);
    void deleteStudent(Long studentId);
    void addStudentToSection(Long studentId, Long sectionId);
}
