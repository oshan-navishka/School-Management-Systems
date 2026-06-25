package com.example.AAD_Task_II.controller;

import com.example.AAD_Task_II.constant.CommonResponse;
import com.example.AAD_Task_II.dto.BookDTO;
import com.example.AAD_Task_II.dto.BookRecodesDTO;
import com.example.AAD_Task_II.service.BookRecodesService;
import com.example.AAD_Task_II.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.AAD_Task_II.constant.ResponseConde.OPERATION_SUCCESS;
import static com.example.AAD_Task_II.constant.ResponseMassage.SUCCESS_MASSAGE;

@RestController
@RequestMapping("/api/librarian")
@CrossOrigin
public class LibrarianController {
    private final BookService bookService;
    private final BookRecodesService bookRecodesService;

    public LibrarianController(BookService bookService, BookRecodesService bookRecodesService) {
        this.bookService = bookService;
        this.bookRecodesService = bookRecodesService;
    }

    // BOOK ENDPOINTS

    @PostMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveBook(@RequestBody BookDTO bookDTO) {
        bookService.saveBook(bookDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @PutMapping(value = "/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateBook(
            @PathVariable Long bookId,
            @RequestBody BookDTO bookDTO) {
        bookDTO.setBookId(bookId);
        bookService.updateBook(bookDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    // BOOK BORROWING (BOOK RECODES) ENDPOINTS

    @PostMapping(value = "/borrowings", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveBookRecode(@RequestBody BookRecodesDTO recodeDTO) {
        bookRecodesService.saveBookRecode(recodeDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @PutMapping(value = "/borrowings/{recodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateBookRecode(
            @PathVariable Long recodeId,
            @RequestBody BookRecodesDTO recodeDTO) {
        recodeDTO.setRecodeId(recodeId);
        bookRecodesService.updateBookRecode(recodeDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @GetMapping(value = "/borrowings/{recodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getBookRecodeById(@PathVariable Long recodeId) {
        BookRecodesDTO recode = bookRecodesService.getBookRecodeById(recodeId);
        return new CommonResponse(OPERATION_SUCCESS, recode, SUCCESS_MASSAGE);
    }

    @DeleteMapping(value = "/borrowings/{recodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteBookRecode(@PathVariable Long recodeId) {
        bookRecodesService.deleteBookRecode(recodeId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MASSAGE);
    }

    @GetMapping(value = "/borrowings", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getBookRecodes(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName) {
        List<BookRecodesDTO> recodes = bookRecodesService.filterBookRecodes(bookName, authorName);
        return new CommonResponse(OPERATION_SUCCESS, recodes, SUCCESS_MASSAGE);
    }
}
