package com.mdabrow9.ticketbookingapp.controllers;

import com.mdabrow9.ticketbookingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbookingapp.api.v1.Model.ScreeningDTO;
import com.mdabrow9.ticketbookingapp.services.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/screening")
public class ScreeningController
{
    private final ScreeningService screeningService;
    @GetMapping("/{id}")
    public ScreeningDTO getRoomInfoAndAvailableSeats(@PathVariable Long id)
    {
        return screeningService.getRoomInfoAndAvailableSeats(id);
    }

    @GetMapping
    public List<AvailableScreeningDTO> getAllScreenings(@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                                        @RequestParam(required = true)@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate)
    {
        return screeningService.getScreeningsInGivenTime(fromDate,toDate);
    }

}
