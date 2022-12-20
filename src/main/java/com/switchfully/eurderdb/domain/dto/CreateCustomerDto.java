package com.switchfully.eurderdb.domain.dto;

import com.switchfully.eurderdb.domain.Address;

public record CreateCustomerDto(String firstName,
                                String lastName,
                                String email,
                                String password,
                                Address address,
                                String phone) {
}
