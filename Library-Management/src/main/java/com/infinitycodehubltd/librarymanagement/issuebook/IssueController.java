package com.infinitycodehubltd.librarymanagement.issuebook;


import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import com.infinitycodehubltd.librarymanagement.book.Book;
import com.infinitycodehubltd.librarymanagement.book.BookRepository;
import com.infinitycodehubltd.librarymanagement.entity.MemberIdRequest;
import com.infinitycodehubltd.librarymanagement.entity.MemberIssueDTO;
import com.infinitycodehubltd.librarymanagement.entity.ReturnUpdateRequest;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

//@RestController
//@RequestMapping(path = "api/issuebook")
//public class IssueController {
//
//    private final IssueService issueService;
//
//    @Autowired
//    public IssueController(IssueService issueService) {
//        this.issueService = issueService;
//    }
//
//    @GetMapping
//    public List<IssueBook> getAllIssueBook(){
//        return issueService.getAllIssueBook();
//    }
//
//    @PostMapping("/addNewBookIssue")
//    public ResponseEntity<ApiResponse> addNewBookIssue(@RequestBody IssueBook issueBook){
//        boolean created = issueService.addNewIssueBook(issueBook);
//
//        if (created) {
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ApiResponse("New book issued", 201));
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(new ApiResponse("Something went wrong please contact with admin", 409));
//        }
//    }
//}



@RestController
@RequestMapping("/api/issuebook")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BookRepository bookRepo;

    @PostMapping("/addNewBookIssue")
    public ResponseEntity<?> addNewBookIssue(@RequestBody IssueDto request) {
        Member member = memberRepo.findById(request.member_id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepo.findById(request.book_id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        IssueBook issueBook = new IssueBook(
                request.issueDate,
                request.dueDate,
                request.returnDate,
                request.fine,
                member,
                book,
                request.status
        );

        boolean saved = issueService.addNewIssueBook(issueBook);

        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("You issued a new book. The due date is : " + request.dueDate, 201));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("You cannot issue this book, please contact with admin", 409));
        }
    }


    @PostMapping("/updateReturnStatus")
    public ResponseEntity<?> updateReturnStatus(@RequestBody ReturnUpdateRequest request) {    // ? means any type
        IssueBook updatedIssue = issueService.updateReturnInfo(request.getId(), request.getReturnDate(), request.getFine());

        if (updatedIssue != null) {

            System.out.println("Book id is : " +updatedIssue.getBook().getId());

            try{
                issueService.updateBookQuantity(updatedIssue.getBook().getId());
                return ResponseEntity.ok(updatedIssue);  // Returns the updated IssueBook as JSON
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Something went wrong"));
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "IssueBook with given ID not found."));
        }
    }

    @PostMapping("/member/issued-books")
    public ResponseEntity<List<MemberIssueDTO>> getIssuedBooksByPost(@RequestBody MemberIdRequest request) {
        Long memberId = request.getMemberId();
        List<MemberIssueDTO> issuedBooks = issueService.getMyIssuedBooks(memberId);
        System.out.println("/member/issued-books"+issuedBooks);
        return ResponseEntity.ok(issuedBooks);
    }

}

