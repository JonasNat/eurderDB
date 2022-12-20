package com.switchfully.eurderdb.domain.dto;

import com.switchfully.eurderdb.domain.Address;

public record CustomerDto(String firstName,
                          String lastName,
                          String email,
                          Address address,
                          String phone) {
}
