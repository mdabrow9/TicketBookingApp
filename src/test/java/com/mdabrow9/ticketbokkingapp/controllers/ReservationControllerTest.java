package com.mdabrow9.ticketbokkingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.NewReservationRequest;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationResponseDTO;
import com.mdabrow9.ticketbokkingapp.api.v1.Model.ReservationTicket;
import com.mdabrow9.ticketbokkingapp.services.ReservationService;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTest
{

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    MockMvc mockMvc;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        //reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    /*@Test
    public void Test() throws Exception
    {   NewReservationRequest mockRequest = new NewReservationRequest();
        mockRequest.setScreeningId(1L);
        mockRequest.setSurname("Abc");
        mockRequest.setName("Abc");
        Long[] seatId = {1L};
        mockRequest.setSeatId(seatId);
        ReservationTicket[] rT = {new ReservationTicket()};
        rT[0].setName("abc");
        rT[0].setNumber(1);
        mockRequest.setReservationTickets(rT);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setTotalCost(BigDecimal.TEN);
        response.setExpirationTime(LocalDateTime.now());


        Mockito.when(reservationService.createReservation(any(),any(),any(),any(),any()))
                .thenReturn(response);

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.totalCost" ).value(response.getTotalCost()))
                .andExpect(jsonPath("$.expirationTime").value(response.getExpirationTime()));


        verify(reservationService, times(1)).createReservation(any(),any(),any(),any(),any());
    }*/


    @Test
    public void TestBadRequest() throws Exception
    {
        NewReservationRequest mockRequest = new NewReservationRequest();

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isBadRequest());
        mockRequest.setScreeningId(1L);

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isBadRequest());
        mockRequest.setSurname("Abc");
        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isBadRequest());
        mockRequest.setName("Abc");
        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isBadRequest());
        Long[] seatId = {1L};
        mockRequest.setSeatId(seatId);
        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isBadRequest());

        ReservationTicket[] rT = {new ReservationTicket()};
        rT[0].setName("abc");
        rT[0].setNumber(1);
        mockRequest.setReservationTickets(rT);

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest))).andExpect(status().isOk());
    }

    public  String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}