package com.example.AAD_Task_II.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String authorName;

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookRecodes> bookRecodes;
}
