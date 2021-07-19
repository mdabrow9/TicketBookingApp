package com.mdabrow9.ticketbokkingapp.conventers;

import com.mdabrow9.ticketbokkingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbokkingapp.domain.Screening;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@NoArgsConstructor
@AllArgsConstructor
@Component
public class ScreeningToAvailableScreeningDTO implements Converter<Screening, AvailableScreeningDTO>
{
    private MovieToMovieDTO movieToMovieDTO;
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
