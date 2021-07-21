package com.mdabrow9.ticketbookingapp.Bootstrap;

import com.mdabrow9.ticketbookingapp.domain.*;
import com.mdabrow9.ticketbookingapp.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent>
{
    private final TicketTypeRepository ticketTypeRepository;
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;




    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
       setUpTicketTypes();
       setUpMovies();
       setUpRoomAndSeats();
       setUpScreenings();
       setUpReservations();

    }

    private void setUpReservations()
    {
        Reservation reservation = new Reservation();
        reservation.setCustomerName("Jan");
        reservation.setCustomerSurname("Kowalski");

        Screening screening = screeningRepository.findAllById(1L).get();
        reservation.setScreening(screening);
        reservation.setExpirationDate(screening.getStartTime().minusMinutes(10));

        TicketType ticketType = ticketTypeRepository.findById(1L).get();
        reservation.setTicketTypes(List.of(ticketType,ticketType,ticketType,ticketType));

        List<SeatReservation> seatList = new ArrayList<>();
        for(int i = 1; i < 5; i++)
        {
            SeatReservation s = new SeatReservation();
            s.setScreening(reservation.getScreening());
            s.setReservation(reservation);
            s.setSeat(seatRepository.findById(2L+i).get());
            seatList.add(s);
        }
        reservation.setSeatReservations(seatList);
        reservationRepository.save(reservation);
    }

    private void setUpTicketTypes()
    {
        List <TicketType> ticketTypes = new ArrayList<>();

        ticketTypes.add(new TicketType("Adult",new BigDecimal("25.0")));
        ticketTypes.add(new TicketType("Student",new BigDecimal("18.0")));
        ticketTypes.add(new TicketType("Child",new BigDecimal("12.5")));
        ticketTypeRepository.saveAll(ticketTypes);

    }
    private void setUpMovies()
    {
        Movie movie1 = new Movie();
        movie1.setTitle("The Avengers");
        movie1.setDurationTime(143);
        movie1.setDescription("In the film, S.H.I.E.L.D. director Nick Fury assembles Iron Man, Captain America, Hulk, Thor, Black Widow and Hawkeye to battle Thor's adoptive brother, Loki, who attempts to subjugate humanity by leading an invasion by the extraterrestrial race known as the Chitauri.");

        Movie movie2 = new Movie();
        movie2.setTitle("Spider-Man: Homecoming");
        movie2.setDurationTime(130);
        movie2.setDescription("Peter Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego Spider-Man, and finds himself on the trail of a new menace prowling the skies of New York City. A young Peter Parker/Spider-Man begins to navigate his newfound identity as the web-slinging super hero Spider-Man.");

        Movie movie3 = new Movie();
        movie3.setTitle("Captain America: The Winter Soldier");
        movie3.setDurationTime(136);
        movie3.setDescription("As Steve Rogers struggles to embrace his role in the modern world, he teams up with a fellow Avenger and S.H.I.E.L.D agent, Black Widow, to battle a new threat from history: an assassin known as the Winter Soldier.");

        movieRepository.saveAll( List.of(movie1,movie2,movie3));
    }

    private void setUpRoomAndSeats()
    {

        Room room1 = new Room();
        room1.setName("Red Room");

        Room room2 = new Room();
        room2.setName("Blue Room");
        Room room3 = new Room();
        room3.setName("Green Room");

        List <Seat> room1Seats = new ArrayList<>();
        List <Seat> room2Seats = new ArrayList<>();
        List <Seat> room3Seats = new ArrayList<>();

        for(int i =1;i<=5;i++)
        {
            for(int j = 1;j<10;j++)
            {
                Seat seat = new Seat();
                seat.setSeatRow(i);
                seat.setSeatNumber(j);
                seat.setRoom(room1);
                room1Seats.add(seat);

                Seat seat2 = new Seat();
                seat2.setSeatRow(i);
                seat2.setSeatNumber(j);
                seat2.setRoom(room2);
                room2Seats.add(seat2);

                Seat seat3 = new Seat();
                seat3.setSeatRow(i);
                seat3.setSeatNumber(j);
                seat3.setRoom(room3);
                room3Seats.add(seat3);
            }
        }
        room1.setSeat(room1Seats);
        room2.setSeat(room2Seats);
        room3.setSeat(room3Seats);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);


    }
    private void setUpScreenings()
    {


        List<Movie> movies = movieRepository.findAll();
        List<Room> rooms =roomRepository.findAll();

        int counter=0;
        int[] plusMinutes = {10,10,30,0,-30,-60};
        int[] plusDays =    {1,0,0,1,0,1};

        for( Room room: rooms)
        {
            for(int i = 0; i < 2; i++)
            {
                Screening screening1 = new Screening();
                screening1.setStartTime( LocalDateTime.now().plusMinutes(plusMinutes[counter%plusMinutes.length]).plusDays(plusDays[counter%plusDays.length]));
                screening1.setMovie( movies.get(counter++%movies.size()));
                screening1.setRoom(room);
                screeningRepository.save(screening1);
            }
        }
    }


}
