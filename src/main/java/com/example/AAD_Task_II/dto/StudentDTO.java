package com.example.AAD_Task_II.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long studentId;
    private String name;
    private String email;
    private Long sectionId;
}
