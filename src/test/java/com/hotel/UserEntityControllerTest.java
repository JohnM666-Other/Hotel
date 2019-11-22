package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.controllers.AuthController;
import com.hotel.entities.UserEntity;
import com.hotel.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserEntityControllerTest {

    @InjectMocks
    private AuthController customerController;

    @Mock
    private UserService customerService;

    private MockMvc mockMvc;
    private UserEntity testUserEntity = new UserEntity("First name", "Second name", "Email", new Date(), 'F', "123");
    private UserEntity[] testUserEntities = new UserEntity[] {
            testUserEntity, testUserEntity
    };

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setUseSuffixPatternMatch(false)
                .build();

        when(customerService.getAll()).thenReturn(Arrays.asList(testUserEntities));
        when(customerService.getById(0L)).thenReturn(testUserEntity);
        when(customerService.create(testUserEntity)).thenReturn(testUserEntity);
        when(customerService.update(testUserEntity)).thenReturn(testUserEntity);
    }

    @Test
    public void getAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(AuthController.ROOT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testUserEntities), result.getResponse().getContentAsString());
    }

    @Test
    public void getByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(AuthController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testUserEntity), result.getResponse().getContentAsString());
    }

    @Test
    public void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AuthController.ROOT)
                .content(mapper.writeValueAsString(testUserEntity))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testUserEntity), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(AuthController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(AuthController.ROOT)
                .content(mapper.writeValueAsString(testUserEntity))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testUserEntity), result.getResponse().getContentAsString());
    }
}
