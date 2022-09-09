package com.example.lmservice.service;

import com.example.lmservice.model.Book;
import com.example.lmservice.repo.BookRepo;
import com.example.lmservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepo bookRepo;

    private Book book;
    @BeforeEach
    public void setUp() {
        book = Book.builder().id("1L")
                .bookName("Zero To One")
                .bookStatus("Present")
                .build();
    }

    @Test
    public void saveBooks() {
//        given(bookRepo.findByName(book.getBookName())).willReturn(null);
        given(bookRepo.save(book)).willReturn(book);
        // when -  action or the behaviour that we are going test
        Book savedBook = bookService. saveBook(book);

        System.out.println(savedBook);
        // then - verify the output
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getBookName()).isEqualTo("Zero To One");
    }

    @Test
    public void getAllBooks() {
        Book book1 = Book.builder()
                .id("2L")
                .bookName("One To Hero")
                .bookStatus("Present")
                .build();
        given(bookRepo.findAll()).willReturn(List.of(book, book1));
        //bookService.saveBook(book);
        List<Book> list = bookService.getAllBooks();
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void getBookByName() {
        given(bookRepo.findByName("Zero To One")).willReturn(book);
        Book savedBook = bookService.getBookByName(book.getBookName());
        assertThat(savedBook).isNotNull();
    }

    @Test
    public void updateBook() {
        given(bookRepo.save(book)).willReturn(book);
        book.setBookName("Zero To Two");

        Book updatedBook = bookService.saveBook(book);
        assertThat(updatedBook.getBookName()).isEqualTo("Zero To Two");
    }

    @Test
    public void addBook() {
        given(bookRepo.save(any())).willReturn(book);

        Book book1 = bookService.addBook(book.getBookName());
        assertThat(book1.getBookName()).isEqualTo("Zero To One");
        assertThat(book1).isNotNull();
    }

}