package com.mdabrow9.ticketbookingapp.repositories;


import com.mdabrow9.ticketbookingapp.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long>
{

    List<Screening> findAllByStartTimeIsBetween(LocalDateTime time, LocalDateTime time2);
    Optional<Screening> findAllById(Long id);
}
