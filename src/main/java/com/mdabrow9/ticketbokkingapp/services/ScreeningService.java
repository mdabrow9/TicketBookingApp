package com.mdabrow9.ticketbokkingapp.services;

import com.mdabrow9.ticketbokkingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ScreeningDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService
{
    public List<AvailableScreeningDTO> getScreeningsInGivenTime(LocalDateTime time, LocalDateTime time2);
    public ScreeningDTO getRoomInfoAndAvailableSeats(Long screeningId);
}
