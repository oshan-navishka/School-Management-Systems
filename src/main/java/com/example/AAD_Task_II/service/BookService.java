package com.example.AAD_Task_II.service;

import com.example.AAD_Task_II.dto.BookDTO;

public interface BookService {
    void saveBook(BookDTO bookDTO);
    void updateBook(BookDTO bookDTO);
}
