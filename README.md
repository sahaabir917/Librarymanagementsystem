# Librarymanagementsystem
This is a simple Library Book Management System built with Spring Boot 3, designed to help libraries track books, manage members, and handle book borrowing operations. This project demonstrates core backend development using Spring Boot and a relational database with api implementation.

Database table and relations:
1. One book can be issued by many times.
2. One member can borrow many books.
3. Each issuse record links one member to one book.

Tables:

Book(id, title, author, isbn, category, publisher, total_copies, available_copy)
Member(id, name, email, phone, address, joined_date)
IssueRecord(id, memberId, bookId, issueDate, dueDate, ReturnDate, Fine)
