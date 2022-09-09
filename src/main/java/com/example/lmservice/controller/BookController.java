package com.example.lmservice.controller;

import com.example.lmservice.DTO.BookDto;
import com.example.lmservice.model.Book;
import com.example.lmservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/cache")
    public List<BookDto> getAllBooksDto() {
        return bookService.getAllBooksDto();
    }

    @GetMapping("/book/{bookName}")
    public Book getBookByName(@PathVariable("bookName") String bookName) {
        return bookService.getBookByName(bookName);
    }

    @PutMapping("/book/borrow/{bookName}")
    public ResponseEntity<?> borrowBook(@PathVariable("bookName") String bookName) {
        Book book = bookService.getBookByName(bookName);
        if(Objects.equals(book.getBookStatus(), "Absent")) {
            return new ResponseEntity<>("Returning from MongoDB, Book Currently Not Available", HttpStatus.OK);
        }
        book.setBookStatus("Absent");
        bookService.updateBook(book);
        return new ResponseEntity<>("Returning from MongoDB, Book Borrowed!", HttpStatus.OK);
    }

    @PutMapping("/book/return/{bookName}")
    public ResponseEntity<?> returnBook(@PathVariable("bookName") String bookName) {
        Book book = bookService.getBookByName(bookName);
        if(Objects.equals(book.getBookStatus(), "Present")) {
            return new ResponseEntity<>("Returning from MongoDB, Book Already Returned", HttpStatus.OK);
        }
        book.setBookStatus("Present");
        bookService.updateBook(book);
        return new ResponseEntity<>("Returning from MongoDB, Book Returned!", HttpStatus.OK);
    }

    @PostMapping("/book/{bookName}")
    public ResponseEntity<Book> newBook(@PathVariable("bookName") String bookName) {
        return new ResponseEntity<>(bookService.addBook(bookName), HttpStatus.CREATED);
    }

    @PutMapping("/book/{bookName}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookName") String bookName) {
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/book/{bookName}")
    public void deleteBookByName(@PathVariable("bookName") String bookName) {
        bookService.deleteBookByName(bookName);
    }

    @DeleteMapping("/books")
    public void deleteBook() {
        bookService.deleteBook();
    }
}
