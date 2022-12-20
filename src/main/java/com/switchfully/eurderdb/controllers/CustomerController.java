package com.switchfully.eurderdb.controllers;

import com.switchfully.eurderdb.domain.dto.CreateCustomerDto;
import com.switchfully.eurderdb.domain.dto.CustomerDto;
import com.switchfully.eurderdb.security.SecurityService;
import com.switchfully.eurderdb.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto register(@RequestBody CreateCustomerDto customerToCreate) {
        return customerService.create(customerToCreate);
    }
}
