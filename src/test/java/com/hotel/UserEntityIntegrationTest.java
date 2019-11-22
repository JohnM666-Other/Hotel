package com.hotel;

import com.hotel.controllers.AuthController;
import com.hotel.entities.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEntityIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void customerTest() {

        UserEntity userEntity = new UserEntity("UserEntity", "Second name", "Email", new Date(), 'F', "123");

        {
            ResponseEntity<UserEntity> customerRes = rest.postForEntity("http://localhost:" + port + "/" + AuthController.ROOT, userEntity, UserEntity.class);
            assertNotNull(customerRes.getBody());
            userEntity = customerRes.getBody();
        }

        {
            ResponseEntity<UserEntity> customerRes = rest.getForEntity("http://localhost:" + port + "/" + AuthController.ROOT + "/" + userEntity.getId(), UserEntity.class);
            assertEquals(customerRes.getBody(), userEntity);
        }

        {
            rest.delete("http://localhost:" + port + "/" + AuthController.ROOT + "/" + userEntity.getId());
            ResponseEntity<UserEntity> customerRes = rest.getForEntity("http://localhost:" + port + "/" + AuthController.ROOT + "/" + userEntity.getId(), UserEntity.class);
            assertNull(customerRes.getBody().getId());
        }
    }
}
