package com.example.lmservice.service.impl;

import com.example.lmservice.DTO.BookDto;
import com.example.lmservice.model.Book;
import com.example.lmservice.repo.BookRepo;
import com.example.lmservice.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private ModelMapper modelMapper;
    private final BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public List<BookDto> getAllBooksDto() {
        return bookRepo.findAll().stream().map(book -> {
            return modelMapper.map(book, BookDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book addBook(Book book) {
        if(book.bookStatus == null){
            book.setBookStatus("Present");
        }
        return bookRepo.save(book);
    }

    @Override
    @CachePut(cacheNames = "books", key = "#book.bookName")
    public Book updateBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    @CacheEvict(cacheNames = "books", key = "#bookName")
    public void deleteBookByName(String bookName) {
        Book book = bookRepo.findByName(bookName);
        bookRepo.deleteById(book.getId());
    }

    @Override
    @CacheEvict(cacheNames = "books", allEntries = true)
    public void deleteBook() {
        bookRepo.deleteAll();
    }

    @Override
    @Cacheable(cacheNames = "books", key = "#bookName")
    public Book getBookByName(String bookName) {
        if(bookRepo.findByName(bookName) == null){
            throw new NoSuchElementException("The Following Book is not Present in Library " +  bookName);
        }
        System.out.println("Not from cache");
        return bookRepo.findByName(bookName);
    }
}