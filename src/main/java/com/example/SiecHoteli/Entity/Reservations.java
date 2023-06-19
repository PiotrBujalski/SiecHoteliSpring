package com.example.SiecHoteli.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Reservations")
public class Reservations {

    @Id
    @Column(name = "reservationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationID;

    @Column(name = "hotelID", nullable = false)
    private int hotelID;

    @Column(name = "roomID", nullable = false)
    private int roomID;

    @Column(name = "userID", nullable = false)
    private int userID;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    public Reservations(int reservationID, int hotelID, int roomID, int userID, Date startDate, Date endDate) {
        this.reservationID = reservationID;
        this.hotelID = hotelID;
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

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
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
