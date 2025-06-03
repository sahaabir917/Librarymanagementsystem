package com.infinitycodehubltd.librarymanagement.issuebook;

import com.infinitycodehubltd.librarymanagement.book.Book;
import com.infinitycodehubltd.librarymanagement.book.BookRepository;
import com.infinitycodehubltd.librarymanagement.entity.MemberIssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    //Dependency Injection

    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, BookRepository bookRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
    }


    //end of Dependency Injection

    public List<IssueBook> getAllIssueBook() {

        return issueRepository.findAll();

    }

    public boolean addNewIssueBook(IssueBook issueBook) {

        try {
            if (issueBook.getId() != 0 && !issueRepository.existsById(issueBook.getId())) {
                return false; // Or throw custom exception
            }

            issueBook.setId(0); // Always save as a new entity
            issueRepository.save(issueBook);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public IssueBook updateReturnInfo(Long id, String returnDate, double fine) {
        Optional<IssueBook> optionalIssue = issueRepository.findById(id);

        if (optionalIssue.isPresent()) {
            IssueBook issue = optionalIssue.get();
            System.out.println("return date is : "+ returnDate);
            issue.setReturnDate(returnDate);
            issue.setFine(fine);
            issue.setStatus("Returned");
            return issueRepository.save(issue);  // return updated object
        }

        return null;  // if not found
    }

    public void updateBookQuantity(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            // return updated object

            Book book = optionalBook.get();
            int bookQuantity = book.getAvailable_copy();
            int updatedBookQuantity = bookQuantity + 1;
            book.setAvailable_copy(updatedBookQuantity);
            bookRepository.save(book);

        }

    }

    public List<MemberIssueDTO> getMyIssuedBooks(Long userId) {
        List<Object[]> rows = issueRepository.myIssuedBooks(userId);
        System.out.println("response is : " +rows);
        return rows.stream()
                .map(MemberIssueDTO::fromRow)
                .toList();
    }


    public MemberIssueDTO searchIssueBook(long issueId) {
        List<Object[]> result = issueRepository.searchIssueBook(issueId);
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("No result found for issueId: " + issueId);
        }
        Object[] row = result.get(0);
        return MemberIssueDTO.fromRow(row);
    }

    public MemberIssueDTO searchIssueBookByTitle(String query) {
        List<Object[]> result = issueRepository.searchIssueBookByTitle(query);
        if(result == null || result.isEmpty()){
            throw new RuntimeException("No result found for issueId: " + query);
        }
        Object[] row = result.get(0);
        return MemberIssueDTO.fromRow(row);
    }
}
