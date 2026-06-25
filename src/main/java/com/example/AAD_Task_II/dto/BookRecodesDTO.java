package com.example.AAD_Task_II.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRecodesDTO {
    private Long recodeId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Long studentId;
    private Long bookId;
}
