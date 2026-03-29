package com.infinitycodehubltd.librarymanagement.dashboard;

public class DashboardDTO {

    private long totalUsers;
    private long totalBooks;
    private long totalRooms;
    private long issuedBooks;
    private long availableBooks;
    private long totalStaff;

    public DashboardDTO(long totalUsers, long totalBooks, long totalRooms,
                        long issuedBooks, long availableBooks, long totalStaff) {
        this.totalUsers = totalUsers;
        this.totalBooks = totalBooks;
        this.totalRooms = totalRooms;
        this.issuedBooks = issuedBooks;
        this.availableBooks = availableBooks;
        this.totalStaff = totalStaff;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(long totalRooms) {
        this.totalRooms = totalRooms;
    }

    public long getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(long issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    public long getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(long availableBooks) {
        this.availableBooks = availableBooks;
    }

    public long getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(long totalStaff) {
        this.totalStaff = totalStaff;
    }
}
