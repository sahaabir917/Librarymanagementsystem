package com.infinitycodehubltd.librarymanagement.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    //Dependency Injection

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    //end of Dependency Injection

    public List<Book> getBooks(){

        return bookRepository.findAll();

    }


}
