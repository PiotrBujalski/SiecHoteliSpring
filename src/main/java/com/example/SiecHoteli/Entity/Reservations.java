package com.example.SiecHoteli.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "Reservations")
public class Reservations {

    @Id
    @Column(name = "reservationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationID;

    @ManyToOne
    @JoinColumn(name = "hotelID", referencedColumnName = "hotelID", nullable = false)
    private Hotel hotel;

    @Column(name = "roomID", nullable = false)
    private Integer roomID;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    public Reservations(int reservationID, Hotel hotel, int roomID, int userID, Date startDate, Date endDate) {
        this.reservationID = reservationID;
        this.hotel = hotel;
        this.roomID = roomID;
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservations() {
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

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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
