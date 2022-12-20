package com.switchfully.eurderdb.domain.mapper;

import com.switchfully.eurderdb.domain.Customer;
import com.switchfully.eurderdb.domain.dto.CreateCustomerDto;
import com.switchfully.eurderdb.domain.dto.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {
    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhone()
        );
    }

    public Customer toCustomer(CreateCustomerDto createdCustomer) {
        return new Customer(
                createdCustomer.firstName(),
                createdCustomer.lastName(),
                createdCustomer.email(),
                createdCustomer.password(),
                createdCustomer.address(),
                createdCustomer.phone()
        );
    }

    public List<CustomerDto> toDto(List<Customer> customers) {
        return customers.stream().map(this::toDto).toList();
    }
}
