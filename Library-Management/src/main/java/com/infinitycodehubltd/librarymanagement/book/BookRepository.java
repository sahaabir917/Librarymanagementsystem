package com.infinitycodehubltd.librarymanagement.book;

import com.infinitycodehubltd.librarymanagement.issuebook.IssueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.isbn = ?1")
    Optional<Book> findByIsbn(String isbn);

    @Query(value = """
            Select  id,
                       title,
                       author,
                       isbn,
                       category,
                       publisher,
                       total_copies,
                       available_copy from Book WHERE title = :query OR isbn = :query OR publisher = :query OR author = :query
            """, nativeQuery = true)
    List<Object[]> getBookByTitleAuthorIsbn(String query);
}
