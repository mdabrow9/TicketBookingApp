package com.mdabrow9.ticketbookingapp.api.v1.Model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvailableScreeningDTO implements Comparable <AvailableScreeningDTO>
{
    private Long id;
    private LocalDateTime startTime;
    private MovieDTO movie;


    @Override
    public int compareTo(AvailableScreeningDTO o)
    {
        if(this.movie.compareTo(o.movie) ==0)
        {
            return this.startTime.compareTo(o.startTime);
        }
        return this.movie.compareTo(o.movie);
    }
}
