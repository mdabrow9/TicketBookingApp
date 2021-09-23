package com.mdabrow9.ticketbookingapp.conventers;

import com.mdabrow9.ticketbookingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.domain.Screening;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@NoArgsConstructor
@AllArgsConstructor
@Component
public class ScreeningToAvailableScreeningDTO implements Converter<Screening, AvailableScreeningDTO>
{
    private final MovieToMovieDTO movieToMovieDTO;
    @Override
    public AvailableScreeningDTO convert(Screening screening)
    {
        if (screening == null) {
            return null;
        } else {
            AvailableScreeningDTO availableScreeningDTO = new AvailableScreeningDTO();
            availableScreeningDTO.setId(screening.getId());
            availableScreeningDTO.setStartTime(screening.getStartTime());
            availableScreeningDTO.setMovie(movieToMovieDTO.convert(screening.getMovie()));
            return availableScreeningDTO;
        }
    }
}
