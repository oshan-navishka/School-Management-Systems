package com.example.AAD_Task_II.service.impl;

import com.example.AAD_Task_II.dto.BookDTO;
import com.example.AAD_Task_II.entity.Book;
import com.example.AAD_Task_II.repository.BookRepository;
import com.example.AAD_Task_II.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(BookDTO bookDTO) {
        log.info("Saving book: {}", bookDTO.getBookName());
        try {
            if (bookDTO.getBookName() == null || bookDTO.getBookName().isEmpty()) {
                throw new RuntimeException("Book name cannot be empty");
            }
            Book book = new Book();
            book.setBookName(bookDTO.getBookName());
            book.setAuthorName(bookDTO.getAuthorName());
            bookRepository.save(book);
            log.info("Book saved successfully");
        } catch (Exception e) {
            log.error("Error saving book: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        log.info("Updating book with ID: {}", bookDTO.getBookId());
        try {
            if (bookDTO.getBookName() == null || bookDTO.getBookName().isEmpty()) {
                throw new RuntimeException("Book name cannot be empty");
            }
            Optional<Book> bookOptional = bookRepository.findById(bookDTO.getBookId());
            if (bookOptional.isEmpty()) {
                throw new RuntimeException("Book not found with ID: " + bookDTO.getBookId());
            }
            Book book = bookOptional.get();
            book.setBookName(bookDTO.getBookName());
            book.setAuthorName(bookDTO.getAuthorName());
            bookRepository.save(book);
            log.info("Book updated successfully");
        } catch (Exception e) {
            log.error("Error updating book: {}", e.getMessage());
            throw e;
        }
    }
}
