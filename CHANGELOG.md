# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added

#### Book Return Feature

- **`IssueBook.java`** – Added `returnedByUserId` (nullable `Long`) field to track who performed the return (user or admin ID). Added getter, setter, and updated `toString()`.

- **`entity/BulkReturnRequest.java`** *(new file)* – DTO for the bulk return request. Contains:
  - `userId` – the member whose books are being returned
  - `items` – list of `ReturnItem` objects each holding an `issueBookId`
  - `returnedBy` – `"USER"` or `"ADMIN"` indicating the type of performer

- **`entity/ReturnItemResult.java`** *(new file)* – Per-item result DTO returned in the bulk return response. Contains `issueBookId`, `success` flag, and a `message`.

- **`IssueRepository.java`** – Added two new native queries:
  - `findAllReturnedBooks()` – retrieves all issue records with status `'Returned'`
  - `findReturnedBooksByUserId(Long userId)` – retrieves returned records filtered by member ID

- **`IssueService.java`** – Added two new methods:
  - `bulkReturnBooks(Long userId, List<Long> issueBookIds, Long performedByUserId)` – processes multiple book returns in a single `@Transactional` operation. Validates each issue record (exists, active, belongs to user), marks it as `Returned`, records `returnedByUserId`, and increments the corresponding book's `available_copy`.
  - `getReturnHistory(Long userId)` – returns the list of returned books, optionally filtered by member ID.

- **`IssueController.java`** – Added two new endpoints:
  - `POST /api/issuebook/return` – bulk return endpoint accessible by `ADMIN`, `STAFF`, and `USER`. Rules:
    - If `returnedBy = "USER"`: the authenticated user may only return their own books (`userId` must match the authenticated member's ID).
    - If `returnedBy = "ADMIN"`: only `ADMIN` or `STAFF` roles are permitted.
    - Uses a transaction; any invalid item (not found, already returned, wrong owner) causes the entire operation to fail.
  - `GET /api/issuebook/return-history` – return history endpoint accessible by `ADMIN`, `STAFF`, and `USER`. Accepts optional `userId` query parameter (ignored for `USER` role who always sees only their own history).

### Changed

- **`IssueController.java`** – Added `import java.util.stream.Collectors` and `import org.springframework.security.core.context.SecurityContextHolder` for the new endpoints.
- **`IssueService.java`** – Added imports for `@Transactional`, `LocalDateTime`, `DateTimeFormatter`, `ArrayList`, `Collectors`, and `ReturnItemResult`.
