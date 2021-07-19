package com.mdabrow9.ticketbokkingapp.services;

import com.mdabrow9.ticketbokkingapp.Exceptions.ConflictException;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.NewReservationRequest;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationTicket;
import com.mdabrow9.ticketbokkingapp.domain.*;
import com.mdabrow9.ticketbokkingapp.repositories.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest
{
    @Mock
    private  ReservationRepository reservationRepository;
    @Mock
    private  SeatReservationRepository seatReservationRepository;
    @Mock
    private  ScreeningRepository screeningRepository;
    @Mock
    private  SeatRepository seatRepository;
    @Mock
    private  TicketTypeRepository ticketTypeRepository;

    private ReservationService reservationService;

    private NewReservationRequest request;

    private Screening screening;

    private Room room;

    private Movie movie;

    private List<Seat> seats;

    private TicketType ticketType;
    private List<SeatReservation> seatReservations;


    @Before
    public void setUp()
    {

        //MockitoAnnotations.initMocks(this);

        reservationService = new ReservationServiceImpl(reservationRepository,seatReservationRepository,screeningRepository,seatRepository,ticketTypeRepository);

        request = new NewReservationRequest();
        request.setScreeningId(1L);
        request.setName("Jan");
        request.setSurname("Kowalski");
        request.setSeatId(new Long[]{1L,2L,3L});
        ReservationTicket rT = new ReservationTicket();
        rT.setName("Adult");
        rT.setNumber(3);
        request.setReservationTickets(new ReservationTicket[] {rT});


        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("test movie");
        movie.setDescription("description");
        movie.setDurationTime(100);

        seats = new ArrayList<Seat>();


        room = new Room();
        room.setId(1L);
        room.setName("test room");
        room.setSeat(seats);


        for(int i = 1; i <=10; i++)
        {
            Seat seat = new Seat();
            seat.setId((long) i);
            seat.setSeatRow(0);
            seat.setSeatNumber(i);
            seat.setRoom(room);
            seats.add(seat);
        }

        screening = new Screening();
        screening.setId(1L);
        screening.setMovie(movie);
        movie.setScreenings(List.of(screening));

        screening.setStartTime(LocalDateTime.of(2021,7,20,12,0));
        screening.setRoom(room);
        room.setScreenings(List.of(screening));

        ticketType = new TicketType("Adult",new BigDecimal("25.0"));

        seatReservations = new ArrayList<>();
        /*SeatReservation  sR = new SeatReservation();
        sR.setId(1L);
        sR.setScreening(screening);
        sR.setSeat(seats.get(3));*/

        Mockito.when(screeningRepository.findAllById(anyLong())).thenReturn(Optional.of(screening));

        for( Seat s:seats)
        {
            Mockito.when(seatRepository.findById(eq(s.getId()))).thenReturn(Optional.of(s));
        }
        Mockito.when(seatReservationRepository.findByScreeningAndSeat(any(), any())).thenReturn(Optional.empty());

        Mockito.when(seatReservationRepository.findAllByRowAndScreening(anyInt(), any())).thenReturn(seatReservations);
        Mockito.when(ticketTypeRepository.findByName(anyString())).thenReturn(Optional.of(ticketType));


    }

    @Test
    public void createReservationTest()
    {


        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());

        assertNotEquals(null,response);
        assertEquals(BigDecimal.valueOf(75.0),response.getTotalCost());
        assertEquals(screening.getStartTime().minusMinutes(10),response.getExpirationTime());
    }

    @Test(expected = ConflictException.class)
    public void SingleSlotLeftBetweenSeatsTest()
    {

        request.setSeatId(new Long[]{1L,2L,4L});

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());


    }

    @Test(expected = ConflictException.class)
    public void fifteenMinutesBeforeScreeningTest()
    {
        screening.setStartTime(LocalDateTime.now());

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());


    }

    @Test(expected = ConflictException.class)
    public void wrongClientName1()
    {
        request.setName("jan");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientName2()
    {
        request.setName("jan Kowalski");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientName3()
    {
        request.setName("123Jan");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientName4()
    {
        request.setName("#Jan");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }

    @Test(expected = ConflictException.class)
    public void wrongClientName5()
    {
        request.setName("Ja");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }

    @Test(expected = ConflictException.class)
    public void wrongClientSurname1()
    {
        request.setSurname("Kowaski Nowak");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientSurname2()
    {
        request.setSurname("kowalski");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientSurname3()
    {
        request.setSurname("123Kowaslki");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientSurname4()
    {
        request.setSurname("Kowaslki - Nowak");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void wrongClientSurname5()
    {
        request.setSurname("Ko");

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }

    @Test(expected = ConflictException.class)
    public void ticketAndSeatNumberDoesNotMatch()
    {
        request.setSeatId(new Long[]{1L,2L});

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
    @Test(expected = ConflictException.class)
    public void alreadyReservedSeat()
    {
        SeatReservation  sR = new SeatReservation();
        sR.setId(1L);
        sR.setScreening(screening);
        sR.setSeat(seats.get(1));

        Mockito.when(seatReservationRepository.findByScreeningAndSeat(any(), any())).thenReturn(Optional.of(sR));

        ReservationResponseDTO response = reservationService.createReservation(request.getSeatId(),request.getScreeningId(),request.getName(),request.getSurname(),request.getReservationTickets());
    }
}