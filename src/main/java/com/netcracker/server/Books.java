package com.netcracker.server;

import com.netcracker.shared.Book;

import java.util.List;

public class Books {

    private List<Book> bookList;

    public Books() {
    }

    public Books(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
