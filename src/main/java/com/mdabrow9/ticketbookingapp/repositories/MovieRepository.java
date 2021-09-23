package com.mdabrow9.ticketbookingapp.repositories;

import com.mdabrow9.ticketbookingapp.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>
{
    Optional<Movie> findDistinctByTitle(String title);
}
