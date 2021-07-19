package com.mdabrow9.ticketbokkingapp.conventers;

import com.mdabrow9.ticketbokkingapp.api.v1.Model.MovieDTO;
import com.mdabrow9.ticketbokkingapp.domain.Movie;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class MovieToMovieDTO implements Converter<Movie, MovieDTO>
{
    @Override
    public MovieDTO convert(Movie movie)
    {
        if (movie == null) {
            return null;
        } else {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setDescription(movie.getDescription());
            movieDTO.setDurationTime(movie.getDurationTime());
            return movieDTO;
        }
    }
}
