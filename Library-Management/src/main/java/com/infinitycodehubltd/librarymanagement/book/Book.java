package com.infinitycodehubltd.librarymanagement.book;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

@Entity
@Table
public class Book {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")


    private long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String publisher;
    private int total_copies;
    private int available_copy;


    public Book() {}

    public static Book fromRow(Object[] row) {
        return new Book(
                ((Number) row[0]).longValue(),        // id
                row[1].toString(),                    // title
                row[2].toString(),                    // author
                row[3].toString(),                    // isbn
                row[4].toString(),                    // category
                row[5].toString(),                    // publisher
                Integer.parseInt(row[6].toString()),    // total_copies
                Integer.parseInt(row[7].toString())          // available_copy
        );
    }


    public Book(long l, String author, String isbn, String category, String publisher, int availableCopy, int totalCopies) {
    }

    public Book(long id, String title, String isbn, String author, String category, String publisher, int total_copies, int available_copy) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.total_copies = total_copies;
        this.available_copy = available_copy;
    }

    public Book(String title, String author, String isbn, String category, String publisher, int available_copy, int total_copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.publisher = publisher;
        this.available_copy = available_copy;
        this.total_copies = total_copies;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getTotal_copies() {
        return total_copies;
    }

    public void setTotal_copies(int total_copies) {
        this.total_copies = total_copies;
    }

    public int getAvailable_copy() {
        return available_copy;
    }

    public void setAvailable_copy(int available_copy) {
        this.available_copy = available_copy;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", publisher='" + publisher + '\'' +
                ", total_copies='" + total_copies + '\'' +
                ", available_copy='" + available_copy + '\'' +
                '}';
    }
}
