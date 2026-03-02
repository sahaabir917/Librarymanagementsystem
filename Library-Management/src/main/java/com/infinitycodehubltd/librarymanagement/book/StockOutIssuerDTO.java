package com.infinitycodehubltd.librarymanagement.book;

public class StockOutIssuerDTO {

    private Long bookId;
    private String bookName;
    private Long userId;
    private String userEmail;
    private String returnDate;
    private Integer remainingDays;

    public StockOutIssuerDTO(Long bookId, String bookName, Long userId, String userEmail,
                             String returnDate, Integer remainingDays) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.returnDate = returnDate;
        this.remainingDays = remainingDays;
    }

    public static StockOutIssuerDTO fromRow(Object[] row) {
        return new StockOutIssuerDTO(
                ((Number) row[0]).longValue(),   // bookId
                (String) row[1],                 // bookName
                ((Number) row[2]).longValue(),   // userId
                (String) row[3],                 // userEmail
                row[4] != null ? row[4].toString() : null, // returnDate
                row[5] != null ? ((Number) row[5]).intValue() : null // remainingDays
        );
    }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public Integer getRemainingDays() { return remainingDays; }
    public void setRemainingDays(Integer remainingDays) { this.remainingDays = remainingDays; }
}
