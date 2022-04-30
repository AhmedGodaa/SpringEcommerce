package net.godaa.SpringEcommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.godaa.SpringEcommerce.Customer.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(mappedBy = "cart")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItem;
}
