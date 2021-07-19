package com.mdabrow9.ticketbokkingapp.api.v1.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mdabrow9.ticketbokkingapp.domain.Room;
import lombok.Data;

import java.util.List;

@Data
public class ScreeningDTO
{
    private String roomName;

    private List<SeatDTO> seats;

}
