package com.example.SiecHoteli.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Hotel")
public class Hotel {
    @Id
    @Column(name = "hotelID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hotelID;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "city", nullable = false, length = 45)
    private String city;

    public Hotel() {
    }

    public Hotel(int hotelID, String name, String city) {
        this.hotelID = hotelID;
        this.name = name;
        this.city = city;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

