package com.infinitycodehubltd.librarymanagement.book;

import com.infinitycodehubltd.librarymanagement.entity.MemberIssueDTO;
import com.infinitycodehubltd.librarymanagement.user.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public boolean addNewBook(Book book) {

        if (book.getId() != 0 && !bookRepository.existsById(book.getId())) {
            return false; // Or throw custom exception
        }

        Optional<Book> existing = bookRepository.findByIsbn(book.getIsbn());

        if (existing.isPresent()) {
            return false; // email already exists
        }

        book.setId(0); // Always save as a new entity
        bookRepository.save(book);
        return true;
    }

    public List<Book> getBookByTitleAuthorIsbn(String query) {
        List<Object[]> rows = bookRepository.getBookByTitleAuthorIsbn(query);
        System.out.println("response is : " +rows);
        return rows.stream()
                .map(Book::fromRow)
                .toList();
    }
}
