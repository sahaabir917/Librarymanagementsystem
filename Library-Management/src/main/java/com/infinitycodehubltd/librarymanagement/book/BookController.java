package com.infinitycodehubltd.librarymanagement.book;


import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import com.infinitycodehubltd.librarymanagement.entity.MemberIssueDTO;
import com.infinitycodehubltd.librarymanagement.entity.query;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class BookController {


    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addNewBook")
    public ResponseEntity<ApiResponse> addNewBook(@RequestBody Book book) {
        boolean created = bookService.addNewBook(book);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("New Book Record Created", 201));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("This book is already entered", 409));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @PostMapping("/searchByTitleAuthorIsbn")
    public ResponseEntity<List<Book>> getBookByTitleAuthorIsbn(@RequestBody query request) {
        List<Book> books = bookService.getBookByTitleAuthorIsbn(request.getQuery());
        return ResponseEntity.ok(books);
    }


}
