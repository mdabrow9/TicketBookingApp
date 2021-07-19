package com.mdabrow9.ticketbokkingapp.repositories;

import com.mdabrow9.ticketbokkingapp.domain.Screening;
import com.mdabrow9.ticketbokkingapp.domain.Seat;
import com.mdabrow9.ticketbokkingapp.domain.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long>
{
    List<SeatReservation> findAllByScreening(Screening screening);
    Optional<SeatReservation> findByScreeningAndSeat(Screening screening, Seat seat);
    @Query("select s from SeatReservation s where s.seat.seatRow = ?1 and s.screening = ?2")
    List<SeatReservation> findAllByRowAndScreening(int row, Screening screening);
}
