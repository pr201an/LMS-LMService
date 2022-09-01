package com.example.lmservice.DTO;

public class BookDto {
    public String bookName;
    public String bookStatus;

    public BookDto() {
    }

    public BookDto(String bookName, String bookStatus) {
        this.bookName = bookName;
        this.bookStatus = bookStatus;
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
