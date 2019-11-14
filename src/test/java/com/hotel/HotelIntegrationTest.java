package com.hotel;

import com.hotel.controllers.HotelController;
import com.hotel.entities.Hotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void hotelTest() {

        Hotel hotel = new Hotel("Hotel", "---", "Country", "City", "Url", 10);

        {
            ResponseEntity<Hotel> hotelRes = rest.postForEntity("http://localhost:" + port + "/" + HotelController.ROOT, hotel, Hotel.class);
            assertNotNull(hotelRes.getBody());
            hotel = hotelRes.getBody();
        }

        {
            ResponseEntity<Hotel> hotelRes = rest.getForEntity("http://localhost:" + port + "/" + HotelController.ROOT + "/" + hotel.getId(), Hotel.class);
            assertEquals(hotelRes.getBody(), hotel);
        }

        {
            rest.delete("http://localhost:" + port + "/" + HotelController.ROOT + "/" + hotel.getId());
            ResponseEntity<Hotel> hotelRes = rest.getForEntity("http://localhost:" + port + "/" + HotelController.ROOT + "/" + hotel.getId(), Hotel.class);
            assertNull(hotelRes.getBody().getId());
        }
    }
}
