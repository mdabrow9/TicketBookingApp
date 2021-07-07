package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Seat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int seatRow;
    private int seatNumber;

    @ManyToOne
    private Room room;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "seat")
    private List<SeatReservation> seatReservations;

}
