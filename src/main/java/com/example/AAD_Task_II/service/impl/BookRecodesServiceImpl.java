package com.example.AAD_Task_II.service.impl;

import com.example.AAD_Task_II.dto.BookRecodesDTO;
import com.example.AAD_Task_II.entity.Book;
import com.example.AAD_Task_II.entity.BookRecodes;
import com.example.AAD_Task_II.entity.Student;
import com.example.AAD_Task_II.enumaration.StudentStatus;
import com.example.AAD_Task_II.repository.BookRecodeRepository;
import com.example.AAD_Task_II.repository.StudentRepository;
import com.example.AAD_Task_II.service.BookRecodesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookRecodesServiceImpl implements BookRecodesService {
    private final BookRecodeRepository bookRecodeRepository;
    private final StudentRepository studentRepository;

    public BookRecodesServiceImpl(BookRecodeRepository bookRecodeRepository, StudentRepository studentRepository) {
        this.bookRecodeRepository = bookRecodeRepository;
        this.studentRepository = studentRepository;
    }

    private BookRecodesDTO toDTO(BookRecodes recode) {
        if (recode == null) return null;
        Long studentId = (recode.getStudent() != null) ? recode.getStudent().getStudentId() : null;
        Long bookId = (recode.getBooks() != null) ? recode.getBooks().getBookId() : null;
        return new BookRecodesDTO(recode.getRecodeId(), recode.getBorrowDate(), recode.getReturnDate(), studentId, bookId);
    }

    @Override
    public void saveBookRecode(BookRecodesDTO recodeDTO) {
        log.info("Saving book borrowing record");
        try {
            BookRecodes recode = new BookRecodes();
            recode.setBorrowDate(recodeDTO.getBorrowDate());
            recode.setReturnDate(recodeDTO.getReturnDate());

            if (recodeDTO.getStudentId() != null) {
                Optional<Student> studentOptional = studentRepository.findById(recodeDTO.getStudentId());
                if (studentOptional.isEmpty()) {
                    throw new RuntimeException("Student not found with ID: " + recodeDTO.getStudentId());
                }
                Student student = studentOptional.get();
                if (student.getStatus() == StudentStatus.INACTIVE) {
                    throw new RuntimeException("Cannot borrow book for inactive or deleted student");
                }
                recode.setStudent(student);
            }
            if (recodeDTO.getBookId() != null) {
                Book book = new Book();
                book.setBookId(recodeDTO.getBookId());
                recode.setBooks(book);
            }

            bookRecodeRepository.save(recode);
            log.info("Book borrowing record saved successfully");
        } catch (Exception e) {
            log.error("Error saving book borrowing record: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateBookRecode(BookRecodesDTO recodeDTO) {
        log.info("Updating book borrowing record with ID: {}", recodeDTO.getRecodeId());
        try {
            Optional<BookRecodes> recodeOptional = bookRecodeRepository.findById(recodeDTO.getRecodeId());
            if (recodeOptional.isEmpty()) {
                throw new RuntimeException("Borrowing record not found with ID: " + recodeDTO.getRecodeId());
            }
            BookRecodes recode = recodeOptional.get();
            recode.setBorrowDate(recodeDTO.getBorrowDate());
            recode.setReturnDate(recodeDTO.getReturnDate());

            if (recodeDTO.getStudentId() != null) {
                Optional<Student> studentOptional = studentRepository.findById(recodeDTO.getStudentId());
                if (studentOptional.isEmpty()) {
                    throw new RuntimeException("Student not found with ID: " + recodeDTO.getStudentId());
                }
                Student student = studentOptional.get();
                if (student.getStatus() == StudentStatus.INACTIVE) {
                    throw new RuntimeException("Cannot borrow/update book record for inactive or deleted student");
                }
                recode.setStudent(student);
            } else {
                recode.setStudent(null);
            }
            if (recodeDTO.getBookId() != null) {
                Book book = new Book();
                book.setBookId(recodeDTO.getBookId());
                recode.setBooks(book);
            } else {
                recode.setBooks(null);
            }

            bookRecodeRepository.save(recode);
            log.info("Book borrowing record updated successfully");
        } catch (Exception e) {
            log.error("Error updating book borrowing record: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public BookRecodesDTO getBookRecodeById(Long recodeId) {
        log.info("Retrieving book borrowing record with ID: {}", recodeId);
        try {
            Optional<BookRecodes> recodeOptional = bookRecodeRepository.findById(recodeId);
            if (recodeOptional.isEmpty()) {
                throw new RuntimeException("Borrowing record not found with ID: " + recodeId);
            }
            return toDTO(recodeOptional.get());
        } catch (Exception e) {
            log.error("Error retrieving borrowing record: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BookRecodesDTO> getAllBookRecodes() {
        log.info("Retrieving all borrowing records");
        try {
            List<BookRecodes> recodes = bookRecodeRepository.findAll();
            List<BookRecodesDTO> dtoList = new ArrayList<>();
            for (BookRecodes recode : recodes) {
                dtoList.add(toDTO(recode));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error retrieving all borrowing records: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteBookRecode(Long recodeId) {
        log.info("Deleting book borrowing record with ID: {}", recodeId);
        try {
            Optional<BookRecodes> recodeOptional = bookRecodeRepository.findById(recodeId);
            if (recodeOptional.isEmpty()) {
                throw new RuntimeException("Borrowing record not found with ID: " + recodeId);
            }
            bookRecodeRepository.delete(recodeOptional.get());
            log.info("Book borrowing record deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting book borrowing record: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BookRecodesDTO> filterBookRecodes(String bookName, String authorName) {
        log.info("Filtering book borrowing records (bookName: {}, authorName: {})", bookName, authorName);
        try {
            String queryBookName = (bookName != null) ? bookName.trim() : "";
            String queryAuthorName = (authorName != null) ? authorName.trim() : "";

            List<BookRecodes> recodes = bookRecodeRepository.findByBooksBookNameOrAuthorName(queryBookName, queryAuthorName);
            List<BookRecodesDTO> dtoList = new ArrayList<>();
            for (BookRecodes recode : recodes) {
                dtoList.add(toDTO(recode));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error filtering borrowing records: {}", e.getMessage());
            throw e;
        }
    }
}
