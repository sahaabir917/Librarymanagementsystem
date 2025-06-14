package com.infinitycodehubltd.librarymanagement.room;

import jakarta.persistence.*;

@Entity
@Table
public class Rooms {

    @Id
    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_sequence")

    private long id;
    private String roomName;
    private int capacity;
    private String roomType;
    private String Availability;

    public Rooms() {
    }

    public Rooms(String roomName, int capacity, String roomType, String availability) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.roomType = roomType;
        Availability = availability;
    }

    public Rooms(long id, String roomName, int capacity, String roomType, String availability) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
        this.roomType = roomType;
        Availability = availability;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", roomType='" + roomType + '\'' +
                ", Availability='" + Availability + '\'' +
                '}';
    }
}
