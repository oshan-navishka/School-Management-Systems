package com.example.AAD_Task_II.service.impl;

import com.example.AAD_Task_II.dto.StudentDTO;
import com.example.AAD_Task_II.entity.Section;
import com.example.AAD_Task_II.entity.Student;
import com.example.AAD_Task_II.enumaration.StudentStatus;
import com.example.AAD_Task_II.repository.StudentRepository;
import com.example.AAD_Task_II.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void saveStudent(StudentDTO studentDTO) {
        log.info("Saving student: {}", studentDTO.getName());
        try {
            if (studentDTO.getName() == null || studentDTO.getName().isEmpty()) {
                throw new RuntimeException("Student name cannot be empty");
            }
            Student student = new Student();
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setStatus(studentDTO.getStatus() != null ? studentDTO.getStatus() : StudentStatus.ACTIVE);
            if (studentDTO.getSectionId() != null) {
                Section section = new Section();
                section.setSectionId(studentDTO.getSectionId());
                student.setSection(section);
            }
            studentRepository.save(student);
            log.info("Student saved successfully");
        } catch (Exception e) {
            log.error("Error saving student: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", studentDTO.getStudentId());
        try {
            if (studentDTO.getName() == null || studentDTO.getName().isEmpty()) {
                throw new RuntimeException("Student name cannot be empty");
            }
            Optional<Student> studentOptional = studentRepository.findById(studentDTO.getStudentId());
            if (studentOptional.isEmpty()) {
                throw new RuntimeException("Student not found with ID: " + studentDTO.getStudentId());
            }
            Student student = studentOptional.get();
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setStatus(studentDTO.getStatus() != null ? studentDTO.getStatus() : (student.getStatus() != null ? student.getStatus() : StudentStatus.ACTIVE));
            if (studentDTO.getSectionId() != null) {
                Section section = new Section();
                section.setSectionId(studentDTO.getSectionId());
                student.setSection(section);
            } else {
                student.setSection(null);
            }
            studentRepository.save(student);
            log.info("Student updated successfully");
        } catch (Exception e) {
            log.error("Error updating student: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteStudent(Long studentId) {
        log.info("Deleting student with ID: {}", studentId);
        try {
            Optional<Student> studentOptional = studentRepository.findById(studentId);
            if (studentOptional.isEmpty()) {
                throw new RuntimeException("Student not found with ID: " + studentId);
            }
            Student student = studentOptional.get();
            student.setStatus(StudentStatus.INACTIVE);
            studentRepository.save(student);
            log.info("Student deleted successfully (status set to INACTIVE)");
        } catch (Exception e) {
            log.error("Error deleting student: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void addStudentToSection(Long studentId, Long sectionId) {
        log.info("Adding student {} to section {}", studentId, sectionId);
        try {
            Optional<Student> studentOptional = studentRepository.findById(studentId);
            if (studentOptional.isEmpty()) {
                throw new RuntimeException("Student not found with ID: " + studentId);
            }
            Student student = studentOptional.get();
            Section section = new Section();
            section.setSectionId(sectionId);
            student.setSection(section);
            studentRepository.save(student);
            log.info("Student added to section successfully");
        } catch (Exception e) {
            log.error("Error adding student to section: {}", e.getMessage());
            throw e;
        }
    }
}
