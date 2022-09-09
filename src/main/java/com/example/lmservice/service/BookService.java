package com.example.lmservice.service;

import com.example.lmservice.DTO.BookDto;
import com.example.lmservice.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<BookDto> getAllBooksDto();
    Book getBookByName(String bookName);
    Book saveBook(Book book);
    Book addBook(String bookName);
    Book updateBook(Book book);
    void deleteBookByName(String bookName);
    void deleteBook();
}
