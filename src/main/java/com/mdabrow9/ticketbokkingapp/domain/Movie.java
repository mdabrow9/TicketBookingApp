package com.mdabrow9.ticketbokkingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String description;
    private int durationTime;


    @OneToMany(cascade = CascadeType.ALL , mappedBy = "movie")
    private List<Screening> screenings;

}
