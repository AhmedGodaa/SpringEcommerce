package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.Cart;
import net.godaa.SpringEcommerce.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    CartRepo cartRepo;

    public Cart createCart(Cart cart) {
        return cartRepo.save(cart);

    }

    public void deleteCart(Long cartId) {
        cartRepo.deleteById(cartId);
    }
}
