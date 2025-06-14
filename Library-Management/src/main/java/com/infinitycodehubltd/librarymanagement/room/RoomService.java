package com.infinitycodehubltd.librarymanagement.room;

import com.infinitycodehubltd.librarymanagement.book.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean addNewRoom(Rooms room) {

        try {
            if (room.getId() != 0 && !roomRepository.existsById(room.getId())) {
                return false; // Or throw custom exception
            }

            Optional<Rooms> existing = roomRepository.findByRoomName(room.getRoomName());

            if (existing.isPresent()) {
                return false; // email already exists
            }

            room.setId(0); // Always save as a new entity
            roomRepository.save(room);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rooms> getRoomList() {
        return roomRepository.findAll();
    }


}
