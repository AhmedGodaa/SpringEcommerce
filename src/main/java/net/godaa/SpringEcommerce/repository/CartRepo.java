package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
