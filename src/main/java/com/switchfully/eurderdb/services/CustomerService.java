package com.switchfully.eurderdb.services;

import com.switchfully.eurderdb.domain.dto.CreateCustomerDto;
import com.switchfully.eurderdb.domain.dto.CustomerDto;
import com.switchfully.eurderdb.domain.mapper.CustomerMapper;
import com.switchfully.eurderdb.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    public CustomerDto create(CreateCustomerDto customerToCreate) {
        return customerMapper.toDto(customerRepository.save(customerMapper.toCustomer(customerToCreate)));
    }

}
