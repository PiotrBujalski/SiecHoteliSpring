package com.example.SiecHoteli.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "Reservations_History")
public class Reservations_History {

    @Id
    @Column(name = "reservationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationID;

    @ManyToOne
    @JoinColumn(name = "hotelID", referencedColumnName = "hotelID", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    private User userID;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    public Reservations_History(int reservationID, Hotel hotel, Room room, User userID, Date startDate, Date endDate) {
        this.reservationID = reservationID;
        this.hotel = hotel;
        this.room = room;
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservations_History() {
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

