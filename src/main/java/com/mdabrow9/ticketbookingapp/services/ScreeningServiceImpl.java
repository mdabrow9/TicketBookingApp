package com.mdabrow9.ticketbookingapp.services;

import com.mdabrow9.ticketbookingapp.Exceptions.NotFoundException;
import com.mdabrow9.ticketbookingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.api.v1.Model.ScreeningDTO;
import com.mdabrow9.ticketbookingapp.conventers.ScreeningToAvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.conventers.SeatToSeatDTO;
import com.mdabrow9.ticketbookingapp.domain.Screening;
import com.mdabrow9.ticketbookingapp.domain.Seat;
import com.mdabrow9.ticketbookingapp.repositories.ScreeningRepository;
import com.mdabrow9.ticketbookingapp.repositories.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScreeningServiceImpl implements ScreeningService
{
    private final ScreeningRepository screeningRepository;
    private final ScreeningToAvailableScreeningDTO screeningToAvailableScreeningDTO;
    private final SeatRepository seatRepository;
    private final SeatToSeatDTO seatToSeatDTO;



    public List<AvailableScreeningDTO> getScreeningsInGivenTime(LocalDateTime time, LocalDateTime time2)
    {


        return screeningRepository.findAllByStartTimeIsBetween(time,time2)
                .stream()
                .map(screeningToAvailableScreeningDTO::convert)
                .sorted(AvailableScreeningDTO::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public ScreeningDTO getRoomInfoAndAvailableSeats(Long screeningId)
    {
        ScreeningDTO screeningDTO =new ScreeningDTO();


        Optional<Screening> screeningOptional =  screeningRepository.findAllById(screeningId);
        if(screeningOptional.isEmpty())
        {
            throw new NotFoundException("Screening with given id doesn't exist");
        }
        Screening screening = screeningOptional.get();
        screeningDTO.setRoomName(screening.getRoom().getName());
        List<Seat> s = seatRepository.findNotOccupied(screening, screening.getRoom() );
        screeningDTO.setSeats(s.stream().map(seatToSeatDTO::convert).collect(Collectors.toList()));
        return screeningDTO;
    }
}
