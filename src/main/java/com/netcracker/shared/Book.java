package com.netcracker.shared;

public class Book {

    private int id;
    private String author;
    private String name;
    private int pageNum;
    private int year;
    private String dateAdded;

    public Book() {
    }

    public Book(int id, String author, String name, int pageNum, int year, String dateAdded) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.pageNum = pageNum;
        this.year = year;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", pageNum=" + pageNum +
                ", year=" + year +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
