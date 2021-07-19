package com.mdabrow9.ticketbokkingapp.repositories;

import com.mdabrow9.ticketbokkingapp.domain.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long>
{

    Optional<TicketType> findByName(String name);
}
