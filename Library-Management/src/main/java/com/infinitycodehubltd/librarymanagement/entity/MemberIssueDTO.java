package com.infinitycodehubltd.librarymanagement.entity;

public class MemberIssueDTO {
    private Long issueId;
    private String issueDate;
    private String dueDate;
    private String returnDate;
    private double fine;
    private String status;
    private String memberName;
    private String bookTitle;

    public MemberIssueDTO(Long issueId, String issueDate, String dueDate, String returnDate,
                          double fine, String status, String memberName, String bookTitle) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.status = status;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
    }

    public static MemberIssueDTO fromRow(Object[] row) {
        return new MemberIssueDTO(
                ((Number) row[0]).longValue(),  // issueId
                (String) row[1],                // issueDate
                (String) row[2],                // dueDate
                (String) row[3],                // returnDate
                ((Number) row[4]).doubleValue(),// fine
                (String) row[5],                // status
                (String) row[6],                // memberName
                (String) row[7]                 // bookTitle
        );
    }

    // Getters (or use Lombok @Getter)


    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
