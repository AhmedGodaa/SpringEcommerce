package net.godaa.SpringEcommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "cartItem")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantity;

    private double price;

    @OneToOne
    private Product product;

    @ManyToOne
    @JsonIgnore
    private Cart cart;
}
