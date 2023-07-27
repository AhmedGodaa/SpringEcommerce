package net.godaa.SpringEcommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import net.godaa.SpringEcommerce.domain.enums.Gender;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jetbrains.annotations.NotNull;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;


    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;


    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"orders", "customerDetails"}, allowSetters = true)
    @ToString.Exclude
    private Set<Cart> carts = new HashSet<>();


    public void setCarts(Set<Cart> shoppingCarts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setCustomer(null));
        }
        if (shoppingCarts != null) {
            shoppingCarts.forEach(i -> i.setCustomer(this));
        }
        this.carts = shoppingCarts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", user=" + user +
                ", carts=" + carts +
                '}';
    }
}
