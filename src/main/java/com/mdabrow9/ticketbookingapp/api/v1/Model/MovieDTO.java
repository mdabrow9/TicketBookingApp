package com.mdabrow9.ticketbookingapp.api.v1.Model;

import lombok.Data;



@Data
public class MovieDTO implements Comparable<MovieDTO>
{

    private String title;

    private String description;
    private int durationTime;

    @Override
    public int compareTo(MovieDTO o)
    {
        return this.title.compareTo(o.title);
    }
}
