package net.godaa.SpringEcommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItem;
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPrice;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = {"user", "carts"}, allowSetters = true)
    private Customer customer;

//    @OneToMany(mappedBy = "cart")
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = {"cart"}, allowSetters = true)
//    private Set<Order> orders = new HashSet<>();

//
//    public void calculatePrice() {
//        if (null != this.orders) {
//            this.setTotalPrice(this.orders.stream().map(Order::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
//        }
//    }

//
//    public Cart addOrder(Order order) {
//        this.orders.add(order);
//        order.setCart(this);
//        calculatePrice();
//        return this;
//    }
//
//
//    public Cart removeOrder(Order productOrder) {
//        this.orders.remove(productOrder);
//        productOrder.setCart(null);
//        calculatePrice();
//        return this;
//    }
//
//
//    public void setOrders(Set<Order> productOrders) {
//        if (this.orders != null) {
//            this.orders.forEach(i -> i.setCart(null));
//        }
//        if (productOrders != null) {
//            productOrders.forEach(i -> i.setCart(this));
//        }
//        this.orders = productOrders;
//        calculatePrice();
//    }


}
