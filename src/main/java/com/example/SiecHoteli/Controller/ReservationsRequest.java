package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Hotel;
import com.example.SiecHoteli.Entity.Room;
import com.example.SiecHoteli.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationsRequest {
    private Hotel hotel;
    private Room room;
    private Integer userID;
    private Date startDate;
    private Date endDate;

}
