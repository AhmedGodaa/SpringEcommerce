package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
