package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}