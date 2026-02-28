package com.infinitycodehubltd.librarymanagement.issuebook;


import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import com.infinitycodehubltd.librarymanagement.book.Book;
import com.infinitycodehubltd.librarymanagement.book.BookRepository;
import com.infinitycodehubltd.librarymanagement.entity.*;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/issuebook")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BookRepository bookRepo;


    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/getIssuedBook")
    public List<IssueBook> getIssuedBooks(){
        return issueService.getAllIssueBook();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
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

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/updateReturnStatus")
    public ResponseEntity<?> updateReturnStatus(@RequestBody ReturnUpdateRequest request) {    // ? means any type
        IssueBook updatedIssue = issueService.updateReturnInfo(request.getId(), request.getReturnDate(), request.getFine());

        if (updatedIssue != null) {

            System.out.println("Book id is : " + updatedIssue.getBook().getId());

            try {
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

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/member/issued-books")
    public ResponseEntity<List<MemberIssueDTO>> getIssuedBooksByPost(@RequestBody MemberIdRequest request) {
        Long memberId = request.getMemberId();
        List<MemberIssueDTO> issuedBooks = issueService.getMyIssuedBooks(memberId);
        System.out.println("/member/issued-books" + issuedBooks);
        return ResponseEntity.ok(issuedBooks);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/search/byIssuedId")
    public ResponseEntity<MemberIssueDTO> searchIssueBook(@RequestBody issueIdRequest request) {
        long issueId = request.getIssueId();
        MemberIssueDTO issuedBook = issueService.searchIssueBook(issueId);
        return ResponseEntity.ok(issuedBook);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    @PostMapping("/search/byBookAuthorTitleIsbn")
    public ResponseEntity<Optional<MemberIssueDTO>> searchIssueBookByTitle(@RequestBody query request){
        String query = request.getQuery();
        Optional<MemberIssueDTO> issuedBook = issueService.searchIssueBookByTitle(query);
        return ResponseEntity.ok(issuedBook);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @PostMapping("/return")
    public ResponseEntity<?> returnBooks(@RequestBody BulkReturnRequest request) {
        Member authenticatedMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (request.getUserId() == null || request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("userId and items are required", 400));
        }

        String returnedBy = request.getReturnedBy();
        if (returnedBy == null || (!returnedBy.equalsIgnoreCase("USER") && !returnedBy.equalsIgnoreCase("ADMIN"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("returnedBy must be USER or ADMIN", 400));
        }

        Member.Role role = authenticatedMember.getRole();
        boolean isAdminOrStaff = role == Member.Role.ADMIN || role == Member.Role.STAFF;

        if (returnedBy.equalsIgnoreCase("USER") && !isAdminOrStaff) {
            // USER can only return their own books
            if (authenticatedMember.getId() != request.getUserId().longValue()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse("You can only return your own books", 403));
            }
        } else if (returnedBy.equalsIgnoreCase("ADMIN") && !isAdminOrStaff) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse("Only ADMIN or STAFF can process returns for other users", 403));
        }

        List<Long> issueBookIds = request.getItems().stream()
                .map(BulkReturnRequest.ReturnItem::getIssueBookId)
                .collect(Collectors.toList());

        try {
            List<ReturnItemResult> results = issueService.bulkReturnBooks(
                    request.getUserId(), issueBookIds, authenticatedMember.getId());
            return ResponseEntity.ok(results);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @GetMapping("/return-history")
    public ResponseEntity<List<MemberIssueDTO>> getReturnHistory(@RequestParam(required = false) Long userId) {
        Member authenticatedMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member.Role role = authenticatedMember.getRole();
        boolean isAdminOrStaff = role == Member.Role.ADMIN || role == Member.Role.STAFF;

        Long effectiveUserId = userId;
        if (!isAdminOrStaff) {
            // USER can only view their own return history
            effectiveUserId = authenticatedMember.getId();
        }

        List<MemberIssueDTO> history = issueService.getReturnHistory(effectiveUserId);
        return ResponseEntity.ok(history);
    }

}