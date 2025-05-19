package com.infinitycodehubltd.librarymanagement.book;


import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/books")
public class BookController {


    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }


    @PostMapping("/addNewBook")
    public ResponseEntity<ApiResponse> addNewMember(@RequestBody Book book) {
        boolean created = bookService.addNewBook(book);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("New member created", 201));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("This book is already entered", 409));
        }
    }




}
