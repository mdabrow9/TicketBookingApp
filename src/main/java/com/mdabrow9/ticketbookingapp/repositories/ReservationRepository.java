package com.mdabrow9.ticketbookingapp.repositories;


import com.mdabrow9.ticketbookingapp.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
{


}
