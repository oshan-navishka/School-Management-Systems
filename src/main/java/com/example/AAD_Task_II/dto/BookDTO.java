package com.example.AAD_Task_II.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bookId;
    private String bookName;
    private String authorName;
}
