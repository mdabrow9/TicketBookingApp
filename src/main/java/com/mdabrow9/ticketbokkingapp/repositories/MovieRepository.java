package com.mdabrow9.ticketbokkingapp.repositories;

import com.mdabrow9.ticketbokkingapp.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>
{
    Optional<Movie> findDistinctByTitle(String title);
}
