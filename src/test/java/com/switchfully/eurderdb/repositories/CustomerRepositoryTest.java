package com.switchfully.eurderdb.repositories;

import com.switchfully.eurderdb.domain.Address;
import com.switchfully.eurderdb.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@DataJpaTest
@Sql("/insertCustomer.sql")
class CustomerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String CUSTOMER = "customer";
    private final CustomerRepository repository;

    public CustomerRepositoryTest(CustomerRepository repository) {
        this.repository = repository;
    }

    @Test
    void count() {
        assertThat(repository.count()).isEqualTo(countRowsInTable(CUSTOMER));
    }

    @Test
    void findById() {
        assertThat(repository.findById(1L))
                .hasValueSatisfying(customer -> {
                    assertThat(customer.getFirstName()).isEqualTo("Jonas");
                    assertThat(customer.getAddress().getStreet()).isEqualTo("Teststreet");
                });
    }

    @Test
    void findByEmail() {
        assertThat(repository.findByEmail("jonas@eurder.com"))
                .hasValueSatisfying(customer -> {
                    assertThat(customer.getFirstName()).isEqualTo("Jonas");
                    assertThat(customer.getAddress().getStreet()).isEqualTo("Teststreet");
                });
    }


    @Test
    void findAll() {
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(CUSTOMER));
    }

    @Test
    void findAllSortedByLastName() {
        assertThat(repository.findAll(Sort.by("lastName")))
                .hasSize(countRowsInTable(CUSTOMER))
                .extracting(Customer::getLastName)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void findAllById() {
        assertThat(repository.findAllById(Set.of(1L, 2L)))
                .extracting(Customer::getId)
                .containsOnly(1L, 2L);
    }

    @Test
    void save() {
        Customer customer = new Customer(
                "Jan",
                "Paternoster",
                "jan@eurder.com",
                "password",
                new Address("Teststreet3", "100", "2000", "Antwerp"),
                "phone"
        );
        repository.saveAndFlush(customer);
        long id = customer.getId();
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(CUSTOMER, "id = " + id)).isOne();
    }

    @Test
    void deleteById() {

        repository.deleteById(2L);
        repository.flush();
        assertThat(countRowsInTableWhere(CUSTOMER, "id = " + 2)).isZero();
    }

    @Test
    void deleteByUnknownId() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class).isThrownBy(
                () -> repository.deleteById(-1L));
    }


}