package com.mdabrow9.ticketbokkingapp.services;

import com.mdabrow9.ticketbokkingapp.Exceptions.ConflictException;
import com.mdabrow9.ticketbokkingapp.Exceptions.NotFoundException;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationTicket;
import com.mdabrow9.ticketbokkingapp.domain.*;
import com.mdabrow9.ticketbokkingapp.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService
{
    private final ReservationRepository reservationRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketTypeRepository ticketTypeRepository;


    @Transactional
    @Override
    public ReservationResponseDTO createReservation(Long[] seatId, Long screeningId, String name, String surname, ReservationTicket[] reservationTickets)
    {
        Reservation reservation = new Reservation();
        //screening
        Screening screening = getScreening(screeningId);
        reservation.setScreening(screening);

        if(screening.getStartTime().isBefore(LocalDateTime.now().plusMinutes(15)))
        {
            throw new ConflictException("Seats can be booked at latest 15 minutes before the screening begins.");
        }


        //seats
        List<Seat> seats = getSeats(seatId, screening);

        //checking single seat slots
        checkSingleSeatSlots(seats, screening);
        //saving
        reservation.setSeatReservations(seats.stream()
                .map(e -> {
                    SeatReservation s = new SeatReservation();
                    s.setSeat(e);
                    s.setScreening(screening);
                    s.setReservation(reservation);
                    return s;
                })
                .collect(Collectors.toList()));

        //name and surname
        Pattern compiledSurnamePattern = Pattern.compile("(([AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWVXYZŹŻ])[aąbcćdeęfghijklłmnńoóprsśtuwvxyzźż]+)(-([AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWVXYZŹŻ])[aąbcćdeęfghijklłmnńoóprsśtuwvxyzźż]+)?");
        Pattern compiledNamePattern = Pattern.compile("(([AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWVXYZŹŻ])[aąbcćdeęfghijklłmnńoóprsśtuwvxyzźż]+)");


        if(!compiledNamePattern.matcher(name).matches() || name.length() < 3)
        {
            throw new ConflictException("Incorrect client name");
        }
        if(!compiledSurnamePattern.matcher(surname).matches() || surname.length() < 3)
        {
            throw new ConflictException("Incorrect client surname");
        }
        reservation.setCustomerName(name);
        reservation.setCustomerSurname(surname);

        //tickets

        List<TicketType> tickets = new ArrayList<>();

        for(ReservationTicket item: reservationTickets)
        {
            Optional<TicketType> ticketTypeOptional = ticketTypeRepository.findByName(item.getName());
            if(ticketTypeOptional.isEmpty())
            {
                throw new NotFoundException("Ticket type: " + item.getName() +"doesn't exist ");
            }
            TicketType ticketType = ticketTypeOptional.get();
            for(int i = 0; i < item.getNumber() ; i++)
            {
                tickets.add(ticketType);
            }
        }

        if(tickets.size() != seats.size())
        {
            throw new ConflictException("Total number of tickets and chosen seats doesn't match");
        }

        reservation.setTicketTypes(tickets);

        //expiration of reservation
        reservation.setExpirationDate(screening.getStartTime().minusMinutes(10));

        reservationRepository.save(reservation);


        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        reservationResponseDTO.setExpirationTime(reservation.getExpirationDate());
        reservationResponseDTO.setTotalCost(reservation.getTicketTypes().stream().map(TicketType::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        return reservationResponseDTO;
    }

    private void checkSingleSeatSlots(List<Seat> seats, Screening screening)
    {
        int[] seatsRowNumbers = seats.stream().map(Seat::getSeatRow).distinct().mapToInt(e->e).toArray();
        List <Seat> reservedSeats = new ArrayList<>();
        for(int row: seatsRowNumbers)
        {
            seats.stream().filter(e->e.getSeatRow()==row).forEach(reservedSeats::add);

            reservedSeats.addAll(seatReservationRepository.findAllByRowAndScreening(row, screening).stream().map(SeatReservation::getSeat).collect(Collectors.toList()));
            reservedSeats.sort(Comparator.comparingInt(Seat::getSeatNumber));
            if(reservedSeats.size() <=1)
            {
                continue;
            }
            for(int i = 1; i < reservedSeats.size() ; i++)
            {
                if(reservedSeats.get(i-1).getSeatNumber() - reservedSeats.get(i).getSeatNumber() == -2)
                {
                    throw new ConflictException("There cannot be a single place left over in a row between two seats: " +reservedSeats.get(i-1).getId() + " and  "+reservedSeats.get(i).getId());
                }
            }
            reservedSeats.clear();
        }
    }

    private List<Seat> getSeats(Long[] seatId, Screening screening)
    {
        List<Seat> seats = new ArrayList<>();

        for(long id: seatId)
        {
            Optional<Seat> seatOptional = seatRepository.findById(id);
            if(seatOptional.isEmpty())
            {
                throw new NotFoundException("Seat with:"+ id+ " id doesn't exist");
            }

            Seat seat = seatOptional.get();
            if(!seat.getRoom().getId().equals(screening.getRoom().getId()))
            {
                throw new NotFoundException("Seat with: "+id+ " id is not available during this screening");
            }
            seats.add(seat);
        }

        for(Seat seat: seats)
        {
           if( seatReservationRepository.findByScreeningAndSeat(screening, seat).isPresent())
           {
                throw new ConflictException("Seat with: " + seat.getId() +" id is already reserved");
           }
        }
        return seats;
    }

    private Screening getScreening(long screeningId)
    {
        Optional<Screening> screeningOptional = screeningRepository.findAllById(screeningId);
        if(screeningOptional.isEmpty())
        {
            throw new NotFoundException("Screening with: "+screeningId+" id doesn't exist");
        }
        return screeningOptional.get();
    }
}
