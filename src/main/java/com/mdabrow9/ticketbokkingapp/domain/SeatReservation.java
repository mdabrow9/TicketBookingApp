package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SeatReservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Screening screening;

    @ManyToOne
    private Reservation reservation;


    @ManyToOne
    private Seat seat;
}
