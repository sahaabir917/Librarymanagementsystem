package com.infinitycodehubltd.librarymanagement.dashboard;

import com.infinitycodehubltd.librarymanagement.book.BookRepository;
import com.infinitycodehubltd.librarymanagement.issuebook.IssueRepository;
import com.infinitycodehubltd.librarymanagement.room.RoomRepository;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final RoomRepository roomRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public DashboardService(MemberRepository memberRepository,
                            BookRepository bookRepository,
                            RoomRepository roomRepository,
                            IssueRepository issueRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.roomRepository = roomRepository;
        this.issueRepository = issueRepository;
    }

    public DashboardDTO getDashboardStats() {
        long totalUsers = memberRepository.count();
        long totalBooks = bookRepository.count();
        long totalRooms = roomRepository.count();
        long issuedBooks = issueRepository.countByStatus("Issued");
        long availableBooks = bookRepository.sumAvailableCopies();
        long totalStaff = memberRepository.countByRole(Member.Role.STAFF);

        return new DashboardDTO(totalUsers, totalBooks, totalRooms, issuedBooks, availableBooks, totalStaff);
    }
}
