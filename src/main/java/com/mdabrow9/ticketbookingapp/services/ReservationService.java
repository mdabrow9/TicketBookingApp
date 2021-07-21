package com.mdabrow9.ticketbookingapp.services;

import com.mdabrow9.ticketbookingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbookingapp.api.v1.Model.ReservationTicket;

public interface ReservationService
{
    public ReservationResponseDTO createReservation(Long[] seatId, Long screeningId, String name, String surname,
                                                    ReservationTicket[] reservationTickets);
}
