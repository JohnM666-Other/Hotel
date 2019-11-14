package com.hotel;

import com.hotel.controllers.CustomerController;
import com.hotel.controllers.FeedbackController;
import com.hotel.controllers.HotelController;
import com.hotel.entities.Customer;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedbackIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void feedbackTest() {
        Hotel hotel = new Hotel("Hotel", "---", "Country", "City", "Url", 10);
        Customer customer = new Customer("First name", "Second name", "Email", new Date(), 'F');

        String prefix = "http://localhost:" + port + "/";
        String url = prefix + FeedbackController.ROOT;

        hotel = rest.postForEntity(prefix + HotelController.ROOT, hotel, Hotel.class).getBody();
        customer = rest.postForEntity(prefix + CustomerController.ROOT, customer, Customer.class).getBody();
        Feedback feedback = new Feedback(hotel, customer, new Date(), 10, "Text");

        {
            ResponseEntity<Feedback> feedbackRes = rest.postForEntity(url, feedback, Feedback.class);
            assertNotNull(feedbackRes.getBody().getId());
            feedback = feedbackRes.getBody();
        }

        {
            ResponseEntity<Feedback> feedbackRes = rest.getForEntity(url + "/" + feedback.getId(), Feedback.class);
            assertEquals(feedbackRes.getBody(), feedback);
        }

        {
            rest.delete(url + "/" + feedback.getId());
            ResponseEntity<Feedback> feedbackRes = rest.getForEntity(url + "/" + feedback.getId(), Feedback.class);
            assertNull(feedbackRes.getBody().getId());
        }

        rest.delete(prefix + HotelController.ROOT + "/" + hotel.getId());
        rest.delete(prefix + CustomerController.ROOT + "/" + customer.getId());
    }
}
