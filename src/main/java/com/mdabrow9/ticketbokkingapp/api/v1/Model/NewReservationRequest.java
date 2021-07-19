package com.mdabrow9.ticketbokkingapp.api.v1.Model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NewReservationRequest
{

    @NotEmpty(message = "seatId may not be empty")
    private Long[] seatId;
    @NotNull(message = "screeningId may not be empty")
    private Long screeningId ;
    @NotBlank(message = "name may not be blank")
    private String name;
    @NotBlank(message = "surname may not be blank")
    private String surname;
    @NotEmpty(message = "reservationTickets may not be empty")
    private ReservationTicket[] reservationTickets;

}
