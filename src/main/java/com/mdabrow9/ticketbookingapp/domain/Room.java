package com.mdabrow9.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "room")
    private List<Screening> screenings;



    @OneToMany(cascade = CascadeType.ALL , mappedBy = "room")
    private List<Seat> seat;

}
