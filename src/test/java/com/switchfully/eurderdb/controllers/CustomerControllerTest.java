package com.switchfully.eurderdb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.eurderdb.domain.Address;
import com.switchfully.eurderdb.domain.dto.CreateCustomerDto;
import com.switchfully.eurderdb.domain.mapper.CustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    private final MockMvc mvc;
    private final CustomerMapper customerMapper;

    CustomerControllerTest(MockMvc mvc, CustomerMapper customerMapper) {
        this.mvc = mvc;
        this.customerMapper = customerMapper;
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void register() throws Exception {
        CreateCustomerDto customerToCreate = new CreateCustomerDto(
                "Jan",
                "Paternoster",
                "jan@eurder.com",
                "password",
                new Address("Teststreet3", "100", "2000", "Antwerp"),
                "phone"
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result =
                this.mvc.perform(post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerToCreate))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.firstName").value("Jan"))
                        .andExpect(jsonPath("$.lastName").value("Paternoster"))
                        .andExpect(jsonPath("$.address.street").value("Teststreet3"))
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(customerMapper.toDto(customerMapper.toCustomer(customerToCreate))));
    }
}