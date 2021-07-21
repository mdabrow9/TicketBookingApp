package com.mdabrow9.ticketbookingapp.services;

import com.mdabrow9.ticketbookingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.api.v1.Model.ScreeningDTO;
import com.mdabrow9.ticketbookingapp.conventers.MovieToMovieDTO;
import com.mdabrow9.ticketbookingapp.conventers.ScreeningToAvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.conventers.SeatToSeatDTO;
import com.mdabrow9.ticketbookingapp.domain.Movie;
import com.mdabrow9.ticketbookingapp.domain.Room;
import com.mdabrow9.ticketbookingapp.domain.Screening;
import com.mdabrow9.ticketbookingapp.domain.Seat;
import com.mdabrow9.ticketbookingapp.repositories.ScreeningRepository;
import com.mdabrow9.ticketbookingapp.repositories.SeatRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScreeningServiceImplTest
{
    @Mock
    private ScreeningRepository screeningRepository;
    @Spy  @InjectMocks
    private ScreeningToAvailableScreeningDTO screeningToAvailableScreeningDTO;
    @Mock
    private SeatRepository seatRepository;
    @Spy
    private SeatToSeatDTO seatToSeatDTO;

    @Spy
    private MovieToMovieDTO movieToMovieDTO;

    private ScreeningService screeningService;

    List<Screening> screeningList = new ArrayList<>();
    List <Seat> seatList = new ArrayList<>();
    Movie movie = new Movie();

    @Before
    public void setUp()
    {
        screeningService = new ScreeningServiceImpl(screeningRepository,screeningToAvailableScreeningDTO,seatRepository,seatToSeatDTO);
        movie.setTitle("abc");
        for(int i = 0; i < 5; i++)
        {
            Screening s = new Screening();
            s.setId((long) i);
            s.setStartTime(LocalDateTime.of(2021,7,16,16-i,0));
            s.setMovie(movie);
            screeningList.add(s);
        }

        for(int i = 0; i < 4; i++)
        {
            Seat s = new Seat();
            seatList.add(s);
        }
    }
    @Test
    public void TestGetScreeningsInGivenTime()
    {
        LocalDateTime time = LocalDateTime.of(2021, 7, 16, 13, 59);
        LocalDateTime time2 = time.plusHours(2);
        Mockito.when(screeningRepository.findAllByStartTimeIsBetween(time, time2)).thenReturn(List.of(screeningList.get(2), screeningList.get(3)));

        List<AvailableScreeningDTO> result =screeningService.getScreeningsInGivenTime(time,time2);
       
        assertEquals(3L, (long) result.get(0).getId());
        assertEquals(2L, (long) result.get(1).getId());
        assertEquals(2 , result.size());
    }

    @Test
    public void TestGetRoomInfoAndAvailableSeats()
    {
        Screening screening = screeningList.get(0);
        Room room = new Room();
        room.setName("test room");
        screening.setRoom(room);

        Mockito.when(seatRepository.findNotOccupied(screening,screening.getRoom() )).thenReturn(seatList);
        Mockito.when(screeningRepository.findAllById(screening.getId())).thenReturn(Optional.of(screening));

        ScreeningDTO result = screeningService.getRoomInfoAndAvailableSeats(screening.getId());


        assertEquals(room.getName(),result.getRoomName());
        assertEquals(seatList.size(), result.getSeats().size());

        for(int i = 0; i < seatList.size(); i++)
        {
            assertEquals(seatList.get(i).getId(), result.getSeats().get(i).getId());
        }
    }
}