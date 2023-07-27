//package net.godaa.SpringEcommerce.controller;
//
//import net.godaa.SpringEcommerce.domain.Cart;
//import net.godaa.SpringEcommerce.domain.CartItem;
//import net.godaa.SpringEcommerce.domain.Product;
//import net.godaa.SpringEcommerce.domain.User;
//import net.godaa.SpringEcommerce.security.UserDetailsImpl;
//import net.godaa.SpringEcommerce.service.CartItemService;
//import net.godaa.SpringEcommerce.service.CartService;
//import net.godaa.SpringEcommerce.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping
//public class CartController {
//    @Autowired
//    UserDetailsImpl userDetails;
//
//    @Autowired
//    ProductService productService;
//    @Autowired
//    CartService cartService;
//    @Autowired
//    CartItemService cartItemService;
//
//    @PostMapping
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void createCart() {
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = loggedInUser.getName();
//        User user = (User) userDetails.loadUserByUsername(username);
//        Cart cart = new Cart(user);
//        cartService.createCart(cart);
//    }
//
//
//    @PostMapping("/cart/{productId}")
//    public void addCartItem(@PathVariable(value = "productId") Long productId) {
////      get User
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = loggedInUser.getName();
//        User user = (User) userDetails.loadUserByUsername(username);
//
//        Cart cart = user.getCart();
//        List<CartItem> cartItems = cart.getCartItem();
//        Product product = productService.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("No product with this id " + productId));
////       If cart Item already exist duplicate the price and increase the quantity
//        for (int i = 0; i < cartItems.size(); i++) {
//            CartItem cartItem = cartItems.get(i);
//            if (product.getId() == (cartItem.getProduct().getId())) {
//                cartItem.setQuantity(cartItem.getQuantity() + 1);
//                cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
//                cartItemService.addCartItem(cartItem);
//                return;
//            }
//        }
//
//        CartItem cartItem = new CartItem();
//        cartItem.setQuantity(1);
//        cartItem.setProduct(product);
//        cartItem.setPrice(product.getPrice());
//        cartItem.setCart(cart);
//        cartItemService.addCartItem(cartItem);
//    }
//
//
//    @DeleteMapping("/cart/{cartItemId}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void removeCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
//        cartItemService.deleteCartItem(cartItemId);
//    }
//
//
//    @DeleteMapping("/cart/{cartId}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void deleteCart(@PathVariable(value = "cartId") Long cartId) {
//        cartService.deleteCart(cartId);
//
//    }
//}
