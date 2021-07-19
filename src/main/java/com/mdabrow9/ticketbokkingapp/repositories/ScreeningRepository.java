package com.mdabrow9.ticketbokkingapp.repositories;


import com.mdabrow9.ticketbokkingapp.domain.Room;
import com.mdabrow9.ticketbokkingapp.domain.Screening;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long>
{

    List<Screening> findAllByStartTimeIsBetween(LocalDateTime time, LocalDateTime time2);
    Optional<Screening> findAllById(Long id);
}
