package com.example.lmservice.repo;

import com.example.lmservice.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {
    @Query(value = "{bookName:'?0'}")
    Book findByName(String bookName);
}
