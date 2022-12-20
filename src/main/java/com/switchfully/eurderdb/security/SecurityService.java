package com.switchfully.eurderdb.security;

import com.switchfully.eurderdb.domain.Customer;
import com.switchfully.eurderdb.exception.CustomerNotFoundException;
import com.switchfully.eurderdb.exception.WrongPasswordException;
import com.switchfully.eurderdb.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
public class SecurityService {
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer validateAuthorization(String authorization) {
        EmailPassword usernamePassword = getUsernamePassword(authorization);
        Customer customer = customerRepository.findByEmail(usernamePassword.email()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if (!customer.doesPasswordMatch(usernamePassword.password())) {
            throw new WrongPasswordException("Wrong password");
        }
        return customer;
    }

    private EmailPassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new EmailPassword(username, password);
    }
}
