package com.example.AAD_Task_II.repository;

import com.example.AAD_Task_II.entity.BookRecodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRecodeRepository extends JpaRepository<BookRecodes, Long> {

    @Query(value = "SELECT br.* FROM book_recodes br JOIN book b ON br.book_id = b.book_id WHERE LOWER(b.book_name) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(b.author_name) LIKE LOWER(CONCAT('%', ?2, '%'))", nativeQuery = true)
    List<BookRecodes> findByBooksBookNameOrAuthorName(String bookName, String authorName);
}
