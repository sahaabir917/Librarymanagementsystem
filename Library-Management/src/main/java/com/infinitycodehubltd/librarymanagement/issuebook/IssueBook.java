package com.infinitycodehubltd.librarymanagement.issuebook;

import com.infinitycodehubltd.librarymanagement.book.Book;
import com.infinitycodehubltd.librarymanagement.user.Member;
import jakarta.persistence.*;

@Entity
@Table
public class IssueBook {
    @Id
    @SequenceGenerator(
            name = "issuebook_sequence",
            sequenceName = "issuebook_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "issuebook_sequence"
    )

    private long id;
    private String issueDate;
    private String dueDate;
    private String returnDate;
    private double fine;
    private String status;

    //foreign key
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;   // mention member table



    //foreign key
    @ManyToOne
    @JoinColumn(name = "book_id", nullable= false)
    private Book book;


    public IssueBook(long id, String issueDate, String dueDate, String returnDate, double fine, Member member, Book book, String status) {
        this.id = id;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.member = member;
        this.book = book;
        this.status = status;
    }

    public IssueBook() {
    }

    public IssueBook(String issueDate, String dueDate, String returnDate, double fine, Member member, Book book, String status) {
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.member = member;
        this.book = book;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IssueBook{" +
                "id=" + id +
                ", issueDate='" + issueDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", fine=" + fine +
                ", member=" + member +
                ", book=" + book +
                ",status=" + status+
                '}';
    }
}
