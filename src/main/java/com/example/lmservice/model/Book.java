package com.example.lmservice.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Document("book_info")
@Builder
public class Book implements Serializable {
    @Id
    public String id;
    @NotBlank(message = "Book Must have a Name")
    public String bookName;
    public String bookStatus;

    public Book() {
    }

    public Book(String id, String bookName, String bookStatus) {
        this.id = id;
        this.bookName = bookName;
        this.bookStatus = bookStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
