package com.example.AAD_Task_II.controller;

import com.example.AAD_Task_II.constant.CommonResponse;
import com.example.AAD_Task_II.dto.SectionDTO;
import com.example.AAD_Task_II.dto.StudentDTO;
import com.example.AAD_Task_II.service.SectionService;
import com.example.AAD_Task_II.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.example.AAD_Task_II.constant.ResponseConde.OPERATION_SUCCESS;
import static com.example.AAD_Task_II.constant.ResponseMassage.SUCCESS_MASSAGE;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {
    private final SectionService sectionService;
    private final StudentService studentService;

    public AdminController(SectionService sectionService, StudentService studentService) {
        this.sectionService = sectionService;
        this.studentService = studentService;
    }

    // SECTION ENDPOINTS

    @PostMapping(value = "/sections", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveSection(@RequestBody SectionDTO sectionDTO) {
        sectionService.saveSection(sectionDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @PutMapping(value = "/sections/{sectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateSection(
            @PathVariable Long sectionId,
            @RequestBody SectionDTO sectionDTO) {
        sectionDTO.setSectionId(sectionId);
        sectionService.updateSection(sectionDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    // STUDENT ENDPOINTS

    @PostMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveStudent(@RequestBody StudentDTO studentDTO) {
        studentService.saveStudent(studentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @PutMapping(value = "/students/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentDTO studentDTO) {
        studentDTO.setStudentId(studentId);
        studentService.updateStudent(studentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @DeleteMapping(value = "/students/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @PutMapping(value = "/students/{studentId}/section/{sectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse addStudentToSection(
            @PathVariable Long studentId,
            @PathVariable Long sectionId) {
        studentService.addStudentToSection(studentId, sectionId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }
}
