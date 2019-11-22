package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.controllers.FeedbackController;
import com.hotel.entities.UserEntity;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.services.FeedbackService;
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
public class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    private MockMvc mockMvc;
    private Feedback testFeedback = new Feedback(
            new Hotel("Hotel", "---", "Country", "City", "Url", 10),
            new UserEntity("First name", "Second name", "Email", new Date(), 'M', "123"),
            new Date(), 10, "Some test");
    private Feedback[] testFeedbacks = new Feedback[] { testFeedback, testFeedback };

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController)
                .setUseSuffixPatternMatch(false)
                .build();

        when(feedbackService.getAll()).thenReturn(Arrays.asList(testFeedbacks));
        when(feedbackService.getById(0L)).thenReturn(testFeedback);
        when(feedbackService.create(testFeedback)).thenReturn(testFeedback);
        when(feedbackService.update(testFeedback)).thenReturn(testFeedback);
    }

    @Test
    public void getAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(FeedbackController.ROOT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testFeedbacks), result.getResponse().getContentAsString());
    }

    @Test
    public void getTestByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(FeedbackController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testFeedback), result.getResponse().getContentAsString());
    }

    @Test
    public void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(FeedbackController.ROOT)
                .content(mapper.writeValueAsString(testFeedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testFeedback), result.getResponse().getContentAsString());
    }

    @Test
    public void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(FeedbackController.ROOT)
                .content(mapper.writeValueAsString(testFeedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(mapper.writeValueAsString(testFeedback), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(FeedbackController.ROOT + "/0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
