package com.example.AAD_Task_II.service;

import com.example.AAD_Task_II.dto.BookRecodesDTO;
import java.util.List;

public interface BookRecodesService {
    void saveBookRecode(BookRecodesDTO recodeDTO);
    void updateBookRecode(BookRecodesDTO recodeDTO);
    BookRecodesDTO getBookRecodeById(Long recodeId);
    List<BookRecodesDTO> getAllBookRecodes();
    void deleteBookRecode(Long recodeId);
    List<BookRecodesDTO> filterBookRecodes(String bookName, String authorName);
}
