package com.mdabrow9.ticketbokkingapp.api.v1.Model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationResponseDTO
{
    private BigDecimal totalCost;
    private LocalDateTime expirationTime;
}
