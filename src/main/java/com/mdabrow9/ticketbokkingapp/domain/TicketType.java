package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class TicketType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketType")
    private List<Reservation> reservations;
}
