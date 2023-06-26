package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private Hotel hotel;
    private String type;
    private double price;
    private boolean availability;
}
