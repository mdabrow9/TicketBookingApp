package com.mdabrow9.ticketbookingapp.repositories;


import com.mdabrow9.ticketbookingapp.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>
{
    Optional<Room> findDistinctByName(String name);
}
