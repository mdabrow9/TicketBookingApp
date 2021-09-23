package com.mdabrow9.ticketbookingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdabrow9.ticketbookingapp.api.v1.Model.NewReservationRequest;
import com.mdabrow9.ticketbookingapp.api.v1.Model.ReservationTicket;
import com.mdabrow9.ticketbookingapp.services.ReservationService;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }


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