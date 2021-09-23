package com.mdabrow9.ticketbookingapp.repositories;


import com.mdabrow9.ticketbookingapp.domain.Room;
import com.mdabrow9.ticketbookingapp.domain.Screening;
import com.mdabrow9.ticketbookingapp.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>
{
    @Override
    Optional<Seat> findById(Long aLong);

    @Query("select s from Seat s where s not in (select sr.seat from SeatReservation sr where sr.screening = ?1) and s.room = ?2")
    List<Seat> findNotOccupied(Screening screening, Room room);
}
