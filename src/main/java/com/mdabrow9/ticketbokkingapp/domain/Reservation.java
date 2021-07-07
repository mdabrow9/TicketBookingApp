package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Reservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerSurname;

    @ManyToOne
    private Screening screening;
    @ManyToOne
    private TicketType ticketType;

    @OneToMany(cascade = CascadeType.ALL , mappedBy="reservation")
    private List<SeatReservation> seatReservations;

}
