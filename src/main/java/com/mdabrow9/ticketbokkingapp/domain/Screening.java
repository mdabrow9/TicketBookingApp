package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Screening
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;


    @ManyToOne
    private Movie movie;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "screening")
    private List<Reservation> reservations;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "screening")
    private List<SeatReservation> seatReservations;

    @ManyToOne
    private Room room;
    



}
