package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.controllers.HotelController;
import com.hotel.entities.Hotel;
import com.hotel.services.HotelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class HotelControllerTest {

    @InjectMocks
    private HotelController hotelController;

    @Mock
    private HotelService hotelService;

    private MockMvc mockMvc;
    private Hotel testHotel = new Hotel("Hotel", "Description", "Country", "city", "url", 10);
    private Hotel[] testHotels = new Hotel[] {testHotel, testHotel};

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController)
                .setUseSuffixPatternMatch(false)
                .build();

        when(hotelService.getAll()).thenReturn(Arrays.asList(testHotels));
        when(hotelService.getById(0L)).thenReturn(testHotel);
        when(hotelService.create(testHotel)).thenReturn(testHotel);
        when(hotelService.update(testHotel)).thenReturn(testHotel);
    }

    @Test
    public void getAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(HotelController.ROOT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testHotels), result.getResponse().getContentAsString());
    }

    @Test
    public void getByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(HotelController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testHotel), result.getResponse().getContentAsString());
    }

    @Test
    public void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(HotelController.ROOT)
                .content(mapper.writeValueAsString(testHotel))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testHotel), result.getResponse().getContentAsString());
    }

    @Test
    public void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(HotelController.ROOT)
                .content(mapper.writeValueAsString(testHotel))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testHotel), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(HotelController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
