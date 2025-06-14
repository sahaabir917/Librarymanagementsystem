package com.infinitycodehubltd.librarymanagement.room;

import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import com.infinitycodehubltd.librarymanagement.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/room")

public class RoomController {


    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addNewRoom")
    public ResponseEntity<ApiResponse> addNewRoom(@RequestBody Rooms room) {
        boolean created = roomService.addNewRoom(room);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("New Room Created", 201));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("This Room already existed", 409));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @GetMapping
    public List<Rooms> getBooks() {
        return roomService.getRoomList();
    }

//    @PostMapping("/searchRoom")
    



}
