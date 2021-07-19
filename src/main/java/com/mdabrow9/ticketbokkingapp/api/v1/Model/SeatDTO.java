package com.mdabrow9.ticketbokkingapp.api.v1.Model;

import lombok.Data;

@Data
public class SeatDTO
{
    private Long id;

    private int seatRow;
    private int seatNumber;
}
