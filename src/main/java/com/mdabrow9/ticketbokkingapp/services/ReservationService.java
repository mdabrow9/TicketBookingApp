package com.mdabrow9.ticketbokkingapp.services;

import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationTicket;

public interface ReservationService
{
    public ReservationResponseDTO createReservation(Long[] seatId, Long screeningId, String name, String surname,
                                                    ReservationTicket[] reservationTickets);
}
