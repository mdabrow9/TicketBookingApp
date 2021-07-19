package com.mdabrow9.ticketbokkingapp.repositories;


import com.mdabrow9.ticketbokkingapp.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
{


}
