package com.mdabrow9.ticketbookingapp.api.v1.Model;


import lombok.Data;

import java.util.List;

@Data
public class ScreeningDTO
{
    private String roomName;

    private List<SeatDTO> seats;

}
