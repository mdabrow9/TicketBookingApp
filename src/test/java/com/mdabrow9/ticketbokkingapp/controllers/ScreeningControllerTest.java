package com.mdabrow9.ticketbokkingapp.controllers;

import com.mdabrow9.ticketbokkingapp.Exceptions.NotFoundException;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.AvailableScreeningDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ScreeningDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.SeatDTO;
import com.mdabrow9.ticketbokkingapp.domain.Seat;
import com.mdabrow9.ticketbokkingapp.services.ScreeningService;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class ScreeningControllerTest
{
    @Mock
    private  ScreeningService screeningService;


    private ScreeningController screeningController;

    MockMvc mockMvc;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        screeningController = new ScreeningController(screeningService);
        mockMvc = MockMvcBuilders.standaloneSetup(screeningController).build();
    }


    @Test
    public void TestBadRequest() throws Exception
    {
        mockMvc.perform(get("/screening")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/screening/abc")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/screening?fromDate=2021-01-01T11:00:00")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/screening?toDate=2021-01-01T11:00:00")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/screening?fromDate=&toDate=")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/screening?fromDate=123&toDate=abc")).andExpect(status().isBadRequest());

    }

    @Test
    public void TestGetAllScreenings() throws Exception
    {
        LocalDateTime time= LocalDateTime.of(2021,7,13,12,0,0);
        AvailableScreeningDTO screeningDTO = new AvailableScreeningDTO();
        screeningDTO.setId(1L);
        screeningDTO.setStartTime(time);
        Mockito.when(screeningService.getScreeningsInGivenTime(eq(time),eq(time.plusHours(2L)))).thenReturn(List.of(screeningDTO,screeningDTO));
        mockMvc.perform(get("/screening?fromDate=2021-07-13T12:00:00&toDate=2021-07-13T14:00:00").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(screeningService, times(1)).getScreeningsInGivenTime(eq(time),eq(time.plusHours(2L)));



    }


    @Test
    public void TestGetRoomInfoAndAvailableSeats() throws Exception
    {
        ScreeningDTO screeningDTO = new ScreeningDTO();

        List<SeatDTO> seats = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            seats.add(new SeatDTO());
        }

        screeningDTO.setRoomName("test room");
        screeningDTO.setSeats(seats);


        Mockito.when(screeningService.getRoomInfoAndAvailableSeats(any())).thenReturn(screeningDTO);
        mockMvc.perform(get("/screening/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName" ).value(screeningDTO.getRoomName()))
                .andExpect(jsonPath("$.seats",hasSize(10)));

        verify(screeningService, times(1)).getRoomInfoAndAvailableSeats(any());



    }

    @Test
    public void TestGetRoomInfoAndAvailableSeatsNotFound() throws Exception
    {
        Mockito.when(screeningService.getRoomInfoAndAvailableSeats(eq(-1L))).thenThrow(new NotFoundException());
        mockMvc.perform(get("/screening/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(screeningService, times(1)).getRoomInfoAndAvailableSeats(any());



    }

}