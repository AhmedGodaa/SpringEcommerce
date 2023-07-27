//package net.godaa.SpringEcommerce.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//import org.hibernate.Hibernate;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import javax.persistence.*;
//import javax.validation.constraints.DecimalMin;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
//import java.math.BigDecimal;
//import java.util.Objects;
//
//@Entity
//@Table(name = "order")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @Min(value = 0)
//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;
//
//
//    @NotNull
//    @DecimalMin(value = "0")
//    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
//    private BigDecimal totalPrice;
//
//
//    @ManyToOne(optional = false)
//    @NotNull
//    @JsonIgnoreProperties(value = { "productOrders" }, allowSetters = true)
//    private Product product;
//
//
//    @ManyToOne(optional = false)
//    @NotNull
//    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
//    private Cart cart;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Order order = (Order) o;
//        return id != null && Objects.equals(id, order.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}
