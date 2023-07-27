package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.CartItem;
import net.godaa.SpringEcommerce.repository.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartItemService {
    @Autowired
    CartItemRepo cartItemRepo;

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepo.save(cartItem);

    }

    public void deleteCartItem(Long itemId) {
        cartItemRepo.deleteById(itemId);
    }

    public void deleteAllCartItems(Long cart) {

    }
}
