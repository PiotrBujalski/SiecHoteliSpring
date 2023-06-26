package com.example.SiecHoteli.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "Room")
public class Room {

    @Id
    @Column(name = "roomID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roomID;

    @ManyToOne
    @JoinColumn(name = "hotelID", referencedColumnName = "hotelID", nullable = false)
    private Hotel hotel;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    public Room() {
    }

    public Room(int roomID, Hotel hotel, String type, double price, boolean availability) {
        this.roomID = roomID;
        this.hotel = hotel;
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public int getRoomID() {
        return roomID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public boolean isAvailability() {
        return availability;
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
