package com.mdabrow9.ticketbokkingapp.controllers;

import com.mdabrow9.ticketbokkingapp.Exceptions.BadRequestException;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.NewReservationRequest;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbokkingapp.services.ReservationService;
import lombok.AllArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController
{
    private final ReservationService reservationService;

   @PostMapping
   public ReservationResponseDTO newReservation(@Validated @RequestBody NewReservationRequest requestBody, BindingResult result)
   {

       if (result.hasErrors()) {
           throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
       }
       return reservationService.createReservation(requestBody.getSeatId(), requestBody.getScreeningId(), requestBody.getName(), requestBody.getSurname(), requestBody.getReservationTickets());
   }


}
