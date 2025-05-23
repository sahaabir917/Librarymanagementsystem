This is a simple Library Book Management System built with Spring Boot 3, designed to help libraries track books, manage members, and handle book borrowing operations. This project demonstrates core backend development using Spring Boot and a relational database with api implementation.

Database Relationship:
1. One book can be issued by many times.
2. One member can borrow many books.
3. Each issue record links one member to one book.

Database Tables: 
Book( id, title, author, isbn, category, publisher, total_copies, available_copy)
Member( id, name, email, phone, address, joined_date)
IssueRecord( id, member_id, book_id, issue_date, due_date, return_date, fine)


Functionality:

Member: 
1. Member get request for retrive all members.
2. Member post request for store a new member.
3. Request for store a new record of the book that issued by a member.
4. My issued book list
5. Change the status of the issued book record and also the update the book availability increase when someone return the issued books.