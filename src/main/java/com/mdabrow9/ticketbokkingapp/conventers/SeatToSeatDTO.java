package com.mdabrow9.ticketbokkingapp.conventers;

import com.mdabrow9.ticketbokkingapp.api.v1.Model.SeatDTO;
import com.mdabrow9.ticketbokkingapp.domain.Seat;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class SeatToSeatDTO implements Converter <Seat, SeatDTO>
{
    @Override
    public SeatDTO convert(Seat seat)
    {
        if (seat == null) {
            return null;
        } else
        {
            SeatDTO seatDTO = new SeatDTO();
            seatDTO.setId(seat.getId());
            seatDTO.setSeatNumber(seat.getSeatNumber());
            seatDTO.setSeatRow(seat.getSeatRow());
            return seatDTO;
        }
    }
}
