package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime expirationDate;

    @ManyToOne
    private Screening screening;
    @ManyToMany
    @JoinTable(name = "reservation_ticketType",
    joinColumns = @JoinColumn(name = "reservation_id"),
    inverseJoinColumns = @JoinColumn(name = "ticketType_id"))
    private List<TicketType> ticketTypes;

    @OneToMany(cascade = CascadeType.ALL , mappedBy="reservation")
    private List<SeatReservation> seatReservations;

}
