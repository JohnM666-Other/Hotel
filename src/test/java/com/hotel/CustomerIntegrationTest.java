package com.hotel;

import com.hotel.controllers.CustomerController;
import com.hotel.entities.Customer;
import com.hotel.services.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
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
public class CustomerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void customerTest() {

        Customer customer = new Customer("Customer", "Second name", "Email", new Date(), 'F');

        {
            ResponseEntity<Customer> customerRes = rest.postForEntity("http://localhost:" + port + "/" + CustomerController.ROOT, customer, Customer.class);
            assertNotNull(customerRes.getBody());
            customer = customerRes.getBody();
        }

        {
            ResponseEntity<Customer> customerRes = rest.getForEntity("http://localhost:" + port + "/" + CustomerController.ROOT + "/" + customer.getId(), Customer.class);
            assertEquals(customerRes.getBody(), customer);
        }

        {
            rest.delete("http://localhost:" + port + "/" + CustomerController.ROOT + "/" + customer.getId());
            ResponseEntity<Customer> customerRes = rest.getForEntity("http://localhost:" + port + "/" + CustomerController.ROOT + "/" + customer.getId(), Customer.class);
            assertNull(customerRes.getBody().getId());
        }
    }
}
