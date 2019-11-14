package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.controllers.CustomerController;
import com.hotel.entities.Customer;
import com.hotel.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
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
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;
    private Customer testCustomer = new Customer("First name", "Second name", "Email", new Date(), 'F');
    private Customer[] testCustomers = new Customer[] {
        testCustomer, testCustomer
    };

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setUseSuffixPatternMatch(false)
                .build();

        when(customerService.getAll()).thenReturn(Arrays.asList(testCustomers));
        when(customerService.getById(0L)).thenReturn(testCustomer);
        when(customerService.create(testCustomer)).thenReturn(testCustomer);
        when(customerService.update(testCustomer)).thenReturn(testCustomer);
    }

    @Test
    public void getAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.ROOT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testCustomers), result.getResponse().getContentAsString());
    }

    @Test
    public void getByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testCustomer), result.getResponse().getContentAsString());
    }

    @Test
    public void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CustomerController.ROOT)
                .content(mapper.writeValueAsString(testCustomer))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testCustomer), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CustomerController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CustomerController.ROOT)
                .content(mapper.writeValueAsString(testCustomer))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testCustomer), result.getResponse().getContentAsString());
    }
}
