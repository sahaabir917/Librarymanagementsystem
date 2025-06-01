package com.infinitycodehubltd.librarymanagement.issuebook;

import com.infinitycodehubltd.librarymanagement.book.Book;
import com.infinitycodehubltd.librarymanagement.entity.MemberIssueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueBook, Long> {

    @Query(value = """
                SELECT
                    i.id AS issueId,
                    i.issue_date AS issueDate,
                    i.due_date AS dueDate,
                    i.return_date AS returnDate,
                    i.fine,
                    i.status,
                    m.name AS memberName,
                    b.title AS bookTitle
                FROM issue_book i
                JOIN member m ON m.id = i.member_id
                JOIN book b ON b.id = i.book_id
                WHERE m.id = :userId
            """, nativeQuery = true)
    List<Object[]> myIssuedBooks(@Param("userId") Long userId);


    @Query(value = """
            SELECT
            i.id AS issueId,
            i.issue_date AS issueDate,
            i.due_date AS dueDate,
            i.return_date AS returnDate,
            i.fine,
            i.status,
            m.name AS memberName,
            b.title AS bookTitle
            FROM issue_book i
            JOIN member m ON m.id = i.member_id
            JOIN book b ON b.id = i.book_id
            WHERE i.id = :issueId
            """, nativeQuery = true)
    List<Object[]> searchIssueBook(@Param("issueId") Long issueId);


    @Query(value = """
                SELECT
            i.id AS issueId,
            i.issue_date AS issueDate,
            i.due_date AS dueDate,
            i.return_date AS returnDate,
            i.fine,
            i.status,
            m.name AS memberName,
            b.title AS bookTitle
            FROM issue_book i
            JOIN member m ON m.id = i.member_id
            JOIN book b ON b.id = i.book_id
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR
                    LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR
                    b.isbn = :query
            """, nativeQuery = true)
    List<Object[]> searchIssueBookByTitle(String query);


}
