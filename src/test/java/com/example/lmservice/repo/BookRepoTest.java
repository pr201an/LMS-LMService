package com.example.lmservice.repo;

import com.example.lmservice.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    public void saveEmployeeTest() {
        Book book = new Book("1L","Mein Kampf", "Present");
        assertEquals(book, bookRepo.save(book));
    }

    @Test
    @Order(2)
    public void getEmployeeTest() {
        Book book = bookRepo.findById("1L").get();
        assertThat(book.getId()).isEqualTo("1L");
    }

    @Test
    @Order(3)
    public void getListOfEmployeesTest() {
        List<Book> employees = bookRepo.findAll();
        assertThat(employees.size()).isGreaterThanOrEqualTo(6);
    }

    @Test
    @Order(4)
    public void updateEmployeesTest() {
        Book book = bookRepo.findById("1L").get();
        book.setBookName("Zero to one");
        Book bookUpdated = bookRepo.save(book);
        assertThat(bookUpdated.getBookName()).isEqualTo("Zero to one");
    }

    @Test
    @Order(5)
    public void deleteEmployeeTest() {
        Book book = bookRepo.findByName("Zero to one");
        bookRepo.delete(book);
        Optional<Book> optionalBook = bookRepo.findById("1L");
        assertThat(optionalBook).isEmpty();
    }
}
