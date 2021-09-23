package com.mdabrow9.ticketbookingapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class TicketType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "ticketTypes")
    private List<Reservation> reservations;


    public TicketType(String name, BigDecimal price)
    {
        this.name = name;
        this.price = price;
    }

}
