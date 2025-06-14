package com.infinitycodehubltd.librarymanagement.room;

import com.infinitycodehubltd.librarymanagement.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Rooms, Long> {

    @Query("SELECT r FROM Rooms r WHERE r.roomName = ?1")
    Optional<Rooms> findByRoomName(String roomName);




}
