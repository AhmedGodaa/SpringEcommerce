package net.godaa.SpringEcommerce.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.godaa.SpringEcommerce.Customer.Customer;

import javax.persistence.*;

@Entity
@Table(name = "billingAddress")
@Getter
@Setter
@NoArgsConstructor
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    @OneToOne(mappedBy = "billingAddress")
    private Customer customer;
}
