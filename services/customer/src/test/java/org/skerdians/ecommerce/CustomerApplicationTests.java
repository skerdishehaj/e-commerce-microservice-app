package org.skerdians.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.entity.Address;
import org.skerdians.ecommerce.customer.repository.CustomerRepository;
import org.skerdians.ecommerce.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc
class CustomerApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        CustomerRequest customerRequest = getCustomerRequest();
        String customerRequestJson = objectMapper.writeValueAsString(customerRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequestJson))
                .andExpect(status().isOk());
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerRequest customerRequest = getCustomerRequest();
        customerService.createCustomer(customerRequest);
        String customerRequestJson = objectMapper.writeValueAsString(customerRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequestJson))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldFindAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCheckCustomerExistsById() throws Exception {
        CustomerRequest customerRequest = getCustomerRequest();
        customerService.createCustomer(customerRequest);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/exists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString())));
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        CustomerRequest customerRequest = getCustomerRequest();
        customerService.createCustomer(customerRequest);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCustomerById() throws Exception {
        CustomerRequest customerRequest = getCustomerRequest();
        customerService.createCustomer(customerRequest);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    private CustomerRequest getCustomerRequest() {
        return new CustomerRequest("1", "John", "Doe", "johndoe@mail.com", new Address("123 Main St", "IL", "62701"));
    }
}
